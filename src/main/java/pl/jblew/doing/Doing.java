package pl.jblew.doing;

import picocli.CommandLine;
import pl.jblew.doing.commands.*;


@CommandLine.Command(
    description = "Monitors time in yaml format",
    name = "doing", mixinStandardHelpOptions = true, version = "0.1",
    subcommands = {
        Start.class,
        Stop.class,
        Stats.class,
        Add.class,
        Use.class,
        Continue.class,
        Summary.class,
        Edit.class,
        Timesheets.class,
    }
)
public class Doing implements Runnable {
    @Override
    public void run() {
        System.out.println("Please type command or \"doing help\".");
    }
        
    public static void main(String[] args) {
        CommandLine.run(new Doing(), System.out, args);
    }
}
