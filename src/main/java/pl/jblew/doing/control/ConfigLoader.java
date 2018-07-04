package pl.jblew.doing.control;

import com.fasterxml.jackson.annotation.JsonCreator;
import pl.jblew.doing.StaticConfig;
import pl.jblew.doing.model.Config;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    public static Config loadConfig() throws IOException {
        Config c = new Config();

        File configFile = new File(StaticConfig.CONFIG_FILE);
        if (configFile.exists()) {
            c = StaticConfig.OBJECT_MAPPER.readValue(new File(StaticConfig.CONFIG_FILE), Config.class);
        }
        else {
            System.out.println("Creating new config file in "+StaticConfig.CONFIG_FILE);
            ConfigLoader.saveConfig(c);
        }

        c.populateConfig();

        return c;
    }

    public static void saveConfig(Config c) throws IOException {
        File configFile = new File(StaticConfig.CONFIG_FILE);
        if(!configFile.exists()) {
            configFile.createNewFile();
        }
        StaticConfig.OBJECT_MAPPER.writeValue(configFile, c);
    }
}
