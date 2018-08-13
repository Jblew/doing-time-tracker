package pl.jblew.doing.commands;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.control.TimesheetWriter;
import pl.jblew.doing.model.Config;
import pl.jblew.doing.model.Entry;
import pl.jblew.doing.model.Timesheet;
import pl.jblew.doing.model.TimesheetException;
import pl.jblew.doing.util.DurationFormatter;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "stats",
        description = "Show project statistics",
        mixinStandardHelpOptions = true,
        aliases = { "s", "st", "stat" }
)
public class Stats implements Runnable {
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

        Duration completeDuration = Duration.ZERO;
        Duration dailyDuration = Duration.ZERO;
        Map<String, Duration> durationsOfTasks = new HashMap<>();

        LocalDate date = LocalDate.MIN;

        StringBuilder outBuilder = new StringBuilder();

        outBuilder.append("###############################################################################\n");
        outBuilder.append("################################# \033[1mDOING STATS\033[0m #################################\n");
        outBuilder.append("###############################################################################\n");
        for (Entry e : entries) {
            if(!e.start.toLocalDate().isEqual(date)) {
                outBuilder.append("                                                  daily work: " + DurationFormatter.formatDuration(dailyDuration)+"\n");
                date = e.start.toLocalDate();
                dailyDuration = Duration.ZERO;
                outBuilder.append("\n");
                outBuilder.append("------------------------- \033[1m" + date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        + "\033[0m (" + date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ") -------------------------\n");
                outBuilder.append("\n");
            }

            Duration d = Duration.between(e.start, (e.stop.isEqual(LocalDateTime.MAX)? LocalDateTime.now() : e.stop));
            completeDuration = completeDuration.plus(d);
            dailyDuration = dailyDuration.plus(d);
            durationsOfTasks.put(e.task,
                    durationsOfTasks.containsKey(e.task) ? durationsOfTasks.get(e.task).plus(d) : d);


            outBuilder.append("  " + e.getDescriptionLine() + "("
                    + DurationFormatter.formatDuration(durationsOfTasks.get(e.task)) + " total)\n");
            outBuilder.append("   \033[1m" + e.task+"\033[0m \n");
            outBuilder.append("\n");

        }
        outBuilder.append("                                                  daily work: " + DurationFormatter.formatDuration(dailyDuration) + "\n");
        outBuilder.append("\n");
        outBuilder.append("----------------------- ~~~ --------- ~~~ -----------------------\n");
        outBuilder.append("  Total work: " + DurationFormatter.formatDuration(completeDuration)+"\n");
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
