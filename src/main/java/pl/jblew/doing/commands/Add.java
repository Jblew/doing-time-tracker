package pl.jblew.doing.commands;

import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.control.TimesheetWriter;
import pl.jblew.doing.model.Config;
import pl.jblew.doing.model.Timesheet;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(
        name = "add",
        description = "Add timesheet",
        mixinStandardHelpOptions = true,
        aliases = { "a", "ad" }
)
public class Add implements Runnable{
    @CommandLine.Parameters(index = "0", description = "Name of the timesheet")
    private String name = "";

    @CommandLine.Parameters(index = "1", description = "Path to timesheet yaml file")
    private File path = new File("");

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    @Override
    public void run() {
        try {
            if(!path.exists() && !path.createNewFile()) {
                System.err.println("Cannot create file " + path);
                return;
            }

            Config c = ConfigLoader.loadConfig();
            c.timesheets.put(name, path.getAbsolutePath());
            System.out.println("Added: " + path.getAbsolutePath() + " as " + name);
            ConfigLoader.saveConfig(c);
            System.out.println();

        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        }
    }
}
