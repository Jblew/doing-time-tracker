package pl.jblew.doing.util;

import java.time.Duration;

public class DurationFormatter {
    public static String formatDuration(Duration d) {
        if(d.toHours() > 1) {
            return d.toHours() + "h " + d.toMinutesPart() + "m ";
        }
        else {
            return " " + d.toMinutes() + "m " + d.toSecondsPart() + "s";
        }
    }
}
