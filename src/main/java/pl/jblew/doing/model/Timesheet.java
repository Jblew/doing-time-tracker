package pl.jblew.doing.model;

import pl.jblew.doing.StaticConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Timesheet {
    public List<Entry> entries = new ArrayList<>();

    public void startTask(Entry e) throws TimesheetException {
        if (entries.size() > 0 && entries.get(entries.size() - 1).stop.equals(LocalDateTime.MAX)) {
            throw new TimesheetException("Cannot start new task: last task was not finished");
        }
        else {
            entries.add(e);
        }
    }

    public void stopTask() throws TimesheetException {
        if (entries.isEmpty()) {
            throw new TimesheetException("Cannot stop. There is are no tasks");
        }
        else if (!entries.get(entries.size() - 1).stop.equals(LocalDateTime.MAX)) {
            throw new TimesheetException("Cannot stop. Last entry already stopped");
        }
        else {
            Entry lastEntry = entries.get(entries.size() - 1);
            lastEntry.stop = LocalDateTime.now();
            lastEntry.duration = Duration.between(lastEntry.start, lastEntry.stop);
        }
    }
}
