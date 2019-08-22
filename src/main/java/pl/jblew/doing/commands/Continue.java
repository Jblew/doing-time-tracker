package pl.jblew.doing.commands;

import com.google.common.collect.Lists;
import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.control.TimesheetWriter;
import pl.jblew.doing.model.Config;
import pl.jblew.doing.model.Entry;
import pl.jblew.doing.model.Timesheet;
import pl.jblew.doing.model.TimesheetException;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(
        name = "continue",
        description = "Continue last task (duplicate & start)",
        mixinStandardHelpOptions = true,
        aliases = { "c", "co", "cont" }

)
public class Continue implements Runnable {
    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    @CommandLine.Parameters(index = "*", arity="0..1", description = "Start of hash of the task you want to continue")
    private String hash = "";

    public Continue() {

    }

    @Override
    public void run() {
        try {
            Config c = ConfigLoader.loadConfig();
            TimesheetWriter writer = new TimesheetWriter(new File(c.selectedTimesheetFile));

            Timesheet ts = writer.readTimesheet();

            if (hash.trim().isEmpty()) {
                Entry e = ts.continueLastTask();
                System.out.println("Continue doing: " + e.task);
                System.out.println("  " + e.getDescriptionLine());
                System.out.println();
            }
            else {
                Entry selectedEntry = Lists.reverse(ts.entries).stream()
                        .filter(e -> e.getHumanFriendlyHash().startsWith(hash.trim()))
                        .findFirst().orElseThrow(() -> new TimesheetException("Task starting with this hash was not found."));
                Entry started = ts.startTask(selectedEntry.duplicate());
                System.out.println("Continue doing: " + started.task);
                System.out.println("  " + started.getDescriptionLine());
                System.out.println();
            }

            writer.writeTimesheet(ts);

        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        } catch (TimesheetException e1) {
            System.err.println("TimesheetException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        }

    }
}
