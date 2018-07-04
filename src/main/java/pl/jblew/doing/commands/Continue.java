package pl.jblew.doing.commands;

import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.control.TimesheetWriter;
import pl.jblew.doing.model.Config;
import pl.jblew.doing.model.Entry;
import pl.jblew.doing.model.Timesheet;
import pl.jblew.doing.model.TimesheetException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@CommandLine.Command(
        name = "continue",
        description = "Continue last task (duplicate & start)",
        mixinStandardHelpOptions = true,
        aliases = { "c", "co", "cont" }

)
public class Continue implements Runnable {
    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    public Continue() {

    }

    @Override
    public void run() {
        try {
            Config c = ConfigLoader.loadConfig();
            TimesheetWriter writer = new TimesheetWriter(new File(c.selectedTimesheetFile));

            Timesheet ts = writer.readTimesheet();
            Entry e = ts.continueLastTask();

            writer.writeTimesheet(ts);

            System.out.println("Continuing doing " + e.task + " (" + e.subproject + ") " + Arrays.stream(e.tags).reduce("", (t1, t2) -> t1 + " " + "#" + t2));
        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        } catch (TimesheetException e1) {
            System.err.println("TimesheetException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        }

    }
}
