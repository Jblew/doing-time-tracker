package pl.jblew.doing.commands;

import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.control.TimesheetWriter;
import pl.jblew.doing.model.Config;
import pl.jblew.doing.model.Entry;
import pl.jblew.doing.model.Timesheet;
import pl.jblew.doing.model.TimesheetException;
import pl.jblew.doing.util.DurationFormatter;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

@CommandLine.Command(
        name = "stats",
        description = "Show project statistics",
        mixinStandardHelpOptions = true
)
public class Stats implements Runnable {
    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    @Override
    public void run() {
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
        Duration completeDuration = Duration.ZERO;

        System.out.println("Tasks: ");
        for (Entry e : ts.entries) {
            Duration d = Duration.between(e.start, (e.stop.isEqual(LocalDateTime.MAX)? LocalDateTime.now() : e.stop));
            completeDuration = completeDuration.plus(d);

            System.out.print("  (" + DurationFormatter.formatDuration(d) + ") ");
            System.out.println(e.task);
            System.out.print("      " + e.subproject + " ");
            System.out.print(Arrays.stream(e.tags).reduce("", (t1, t2) -> t1 + " " + "#" + t2));
            if (e.stop.isEqual(LocalDateTime.MAX)) System.out.print(" --acive");
            System.out.println();

        }
        System.out.println("------------");
        System.out.println("  Duration: " + DurationFormatter.formatDuration(completeDuration));
        System.out.println();
    }
}
