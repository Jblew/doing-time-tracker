package pl.jblew.doing.commands;

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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

@CommandLine.Command(
        name = "stats",
        description = "Show project statistics",
        mixinStandardHelpOptions = true,
        aliases = { "s", "st", "stat" }
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

        LocalDate date = LocalDate.MIN;

        System.out.println("###############################################################################");
        System.out.println("################################# \033[1mDOING STATS\033[0m #################################");
        System.out.println("###############################################################################");
        for (Entry e : ts.entries) {
            if(!e.start.toLocalDate().isEqual(date)) {
                date = e.start.toLocalDate();
                System.out.println();
                System.out.println("-------------------------- \033[1m" + date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        + "\033[0m (" + date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ") --------------------------");
                System.out.println();
            }

            Duration d = Duration.between(e.start, (e.stop.isEqual(LocalDateTime.MAX)? LocalDateTime.now() : e.stop));
            completeDuration = completeDuration.plus(d);
            System.out.print("  ");
            System.out.print(e.start.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            System.out.print(" (" + DurationFormatter.formatDuration(d) + ")   ");
            System.out.print("[" + e.subproject + "]   ");
            System.out.print(Arrays.stream(e.tags).reduce("", (t1, t2) -> t1 + " " + "#" + t2));
            if (e.stop.isEqual(LocalDateTime.MAX)) System.out.print(" --acive");
            System.out.println();
            System.out.println("   \033[1m" + e.task+"\033[0m");
            System.out.println();

        }
        System.out.println();
        System.out.println("----------------------- ~~~ --------- ~~~ -----------------------");
        System.out.println("  Duration: " + DurationFormatter.formatDuration(completeDuration));
        System.out.println();
    }
}
