/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jblew.doing.commands;

import picocli.CommandLine;
import pl.jblew.doing.control.ConfigLoader;
import pl.jblew.doing.model.Config;

import java.io.IOException;

@CommandLine.Command(
        name = "list",
        description = "List available timesheet",
        mixinStandardHelpOptions = true,
        aliases = {"l", "li", "lis"}
)
public class List implements Runnable {

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    @Override
    public void run() {
        try {
            Config c = ConfigLoader.loadConfig();

            System.out.println("Available timesheets: ");
            for (String k : c.timesheets.keySet()) {
                System.out.println("  " + k + ": " + c.timesheets.get(k));
            }
            System.out.println();
        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) {
                e1.printStackTrace();
            }
        }
    }
}
