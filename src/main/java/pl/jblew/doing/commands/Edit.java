/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        name = "edit",
        description = "Edit timesheet",
        mixinStandardHelpOptions = true,
        aliases = {"edi", "ed", "e"}
)
public class Edit implements Runnable {

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    public Edit() {

    }

    @Override
    public void run() {
        try {
            Config c = ConfigLoader.loadConfig();
            editTimesheet(c.selectedTimesheetFile);
        } catch (IOException e1) {
            System.err.println("IOException: " + e1.getMessage());
            if (verbose) {
                e1.printStackTrace();
            }
        } catch (InterruptedException e1) {
        }
    }

    private void editTimesheet(String tsPath) throws IOException, InterruptedException {
        File tsFile = new File(tsPath);
        if (tsFile.exists() && tsFile.canRead()) {
            openVimInTerminal(tsFile);

        } else {
            System.err.println("Timesheet file must exist and be readable");
        }
    }

    private void openVimInTerminal(File f) throws IOException, InterruptedException {
        System.out.println("Entering VIM");
        ProcessBuilder processBuilder = new ProcessBuilder("vim", f.getAbsolutePath());
        processBuilder.inheritIO();

        Process p = processBuilder.start();
        p.waitFor();
        System.out.println("Done");
    }
}
