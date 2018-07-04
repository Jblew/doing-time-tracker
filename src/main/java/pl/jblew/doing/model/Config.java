package pl.jblew.doing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.jblew.doing.StaticConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public String selectedTimesheetFile = StaticConfig.TIMESHEET_GENERAL_FILE;
    public Map<String, String> timesheets = new HashMap<>();

    public Config() {
    }

    public void populateConfig() {
        if(timesheets.isEmpty()) {
            timesheets.put("general", StaticConfig.TIMESHEET_GENERAL_FILE);
        }
    }
}
