package pl.jblew.doing.control;

import pl.jblew.doing.StaticConfig;
import pl.jblew.doing.model.Timesheet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class TimesheetWriter {
    private final File timesheetFile;

    public TimesheetWriter(File timesheetFile) {
        this.timesheetFile = timesheetFile;
    }

    public Timesheet readTimesheet() throws IOException {
        if (!timesheetFile.exists() || Files.readAllBytes(timesheetFile.toPath()).length == 0) return new Timesheet();
        return StaticConfig.OBJECT_MAPPER.readValue(timesheetFile, Timesheet.class);
    }

    public void writeTimesheet(Timesheet t) throws IOException {
        if (!timesheetFile.exists()) timesheetFile.createNewFile();

        String outStr = StaticConfig.TIMESHEET_HEADER+"\n";
        outStr += StaticConfig.OBJECT_MAPPER.writeValueAsString(t);
        Files.write(timesheetFile.toPath(), Arrays.asList(outStr), StandardCharsets.UTF_8);
    }
}
