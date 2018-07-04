package pl.jblew.doing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;

public class StaticConfig {
    public static String CONFIG_FILE = System.getProperty("user.home") + File.separator + ".doing.yml";
    public static String TIMESHEET_GENERAL_FILE = System.getProperty("user.home") + File.separator + ".doing-general-timesheet.yml";
    public static String TIMESHEET_HEADER = "" +
            "###################\n" +
            "# Doing timesheet #\n" +
            "###################\n" +
            "\n" +
            "# This time sheet was created using Doing time tracker:\n" +
            "#   https://github.com/Jblew/doing-time-tracker\n" +
            "# For convenience it uses a yaml document format.\n" +
            "\n";
    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper(
                new YAMLFactory()
                    .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                    .enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE)
                    .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
            )
            .registerModule(new JavaTimeModule());
}
