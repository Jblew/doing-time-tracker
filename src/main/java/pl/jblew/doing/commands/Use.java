package pl.jblew.doing.commands;

import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.model.Config;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(
        name = "use",
        description = "Select timesheet",
        mixinStandardHelpOptions = true,
        aliases = { "u", "us" }
)
public class Use implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Name of the timesheet")
    private String name = "";

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    @Override
    public void run() {
        try {
            Config c = ConfigLoader.loadConfig();

            if (!c.timesheets.containsKey(name)) {
                System.err.println("There is no such timesheet \"" + name + "\"");
                System.err.println("You can choose from: ");
                for(String k : c.timesheets.keySet()) {
                    System.err.println("  " + k + ": " + c.timesheets.get(k));
                }
                System.err.println();
                return;
            }

            File selectedPath = new File(c.timesheets.get(name));
            if (!selectedPath.exists() && !selectedPath.createNewFile()) {
                System.err.println("Selected path (" + selectedPath + ") does not exist and cannot be created");
                return;
            }

            c.selectedTimesheetFile = selectedPath.getAbsolutePath();

            ConfigLoader.saveConfig(c);
            System.out.println("Now using \"" + name + "\" timesheet at " + c.selectedTimesheetFile);

        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) e1.printStackTrace();
        }
    }
}
