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
        name = "start",
        description = "Start task",
        mixinStandardHelpOptions = true
)
public class Start implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Name of the task")
    private String task = "";

    @CommandLine.Option(names = {"-s", "--subproject"}, description = "Name of the subproject")
    private String subproject = "(main)";

    @CommandLine.Option(names = {"-t", "--tag"}, description = "Add tag (can specify multiple)")
    private String [] tags = {};

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    public Start() {

    }

    @Override
    public void run() {
        Entry e = new Entry();
        e.start = LocalDateTime.now();
        e.stop = LocalDateTime.MAX;
        e.subproject = subproject;
        e.tags = tags;
        e.task = task;

        try {
            Config c = ConfigLoader.loadConfig();
            TimesheetWriter writer = new TimesheetWriter(new File(c.selectedTimesheetFile));

            Timesheet ts = writer.readTimesheet();
            ts.startTask(e);

            writer.writeTimesheet(ts);

            System.out.println("Start doing " + task + " (" + subproject + ") " + Arrays.stream(e.tags).reduce("", (t1, t2) -> t1 + " " + "#" + t2));
        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        } catch (TimesheetException e1) {
            System.err.println("TimesheetException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        }

    }
}
