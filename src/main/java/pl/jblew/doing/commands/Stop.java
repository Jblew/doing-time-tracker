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

@CommandLine.Command(
        name = "stop",
        description = "Stop task",
        mixinStandardHelpOptions = true
)
public class Stop implements Runnable {
    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    public Stop() {
    }

    @Override
    public void run() {
        try {
            Config c = ConfigLoader.loadConfig();
            TimesheetWriter writer = new TimesheetWriter(new File(c.selectedTimesheetFile));

            Timesheet ts = writer.readTimesheet();
            ts.stopTask();

            writer.writeTimesheet(ts);

            System.out.println("Stop doing last task.");
        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        } catch (TimesheetException e1) {
            System.err.println("TimesheetException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        }

    }
}
