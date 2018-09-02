package pl.jblew.doing.commands;

import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.control.TimesheetWriter;
import pl.jblew.doing.model.Config;
import pl.jblew.doing.model.Entry;
import pl.jblew.doing.model.Timesheet;
import pl.jblew.doing.util.DurationFormatter;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "summary",
        description = "Show project statistics",
        mixinStandardHelpOptions = true,
        aliases = { "summary" }
)
public class Summary implements Runnable {
    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    @CommandLine.Option(names = {"-f", "--filter"}, description = "Filter by tag or subproject")
    private String filter = "";

    @Override
    public void run() {
        long startMs = System.currentTimeMillis();
        try {
            Config c = ConfigLoader.loadConfig();
            TimesheetWriter writer = new TimesheetWriter(new File(c.selectedTimesheetFile));

            Timesheet ts = writer.readTimesheet();

            printPrety(ts);
        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        }
    }

    private void printPrety(Timesheet ts) {
        List<Entry> entries = filterEntries(ts.entries);

        Map<String, Duration> durationsOfTags = new HashMap<>();
        Map<String, Duration> durationsOfSubprojects = new HashMap<>();
        Map<String, Duration> durationsOfTasks = new HashMap<>();
        StringBuilder outBuilder = new StringBuilder();


        for (Entry e : entries) {
            Duration d = Duration.between(e.start, (e.stop.isEqual(LocalDateTime.MAX)? LocalDateTime.now() : e.stop));

            durationsOfSubprojects.put(e.subproject,
                    durationsOfSubprojects.containsKey(e.subproject) ? durationsOfSubprojects.get(e.subproject).plus(d) : d);
            durationsOfTasks.put(e.task,
                    durationsOfTasks.containsKey(e.task) ? durationsOfTasks.get(e.task).plus(d) : d);
            Arrays.stream(e.tags).forEach(tag -> durationsOfTags.put(tag,
                    durationsOfTags.containsKey(tag) ? durationsOfTags.get(tag).plus(d) : d));
        }

        outBuilder.append("\n");
        outBuilder.append("========== tasks ==========\n");
        durationsOfTasks.forEach((task, duration) -> {
            outBuilder.append(" - " + task + ": " + DurationFormatter.formatDuration(duration) + "\n");
        });

        outBuilder.append("\n");
        outBuilder.append("========== subprojects ==========\n");
        durationsOfSubprojects.forEach((subproject, duration) -> {
            outBuilder.append(" - " + subproject + ": " + DurationFormatter.formatDuration(duration) + "\n");
        });

        outBuilder.append("\n");
        outBuilder.append("========== tags ==========\n");
        durationsOfTags.forEach((tag, duration) -> {
            outBuilder.append(" #" + tag + ": " + DurationFormatter.formatDuration(duration) + "\n");
        });

        outBuilder.append("\n");
        System.out.println(outBuilder.toString());
    }

    private List<Entry> filterEntries(List<Entry> entries) {
        if (filter.isEmpty()) return entries;

        return entries.stream().filter(e ->
                e.subproject.equalsIgnoreCase(filter)
                        || Arrays.asList(e.tags).stream().filter(t -> t.equalsIgnoreCase(filter)).findAny().isPresent()).collect(Collectors.toList());
    }
}
