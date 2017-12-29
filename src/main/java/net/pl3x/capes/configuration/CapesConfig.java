package net.pl3x.capes.configuration;

import net.pl3x.capes.Capes;

import java.io.File;
import java.io.IOException;

public class CapesConfig extends ConfigLoader {
    public static final CapesConfig INSTANCE = new CapesConfig();
    private static final String FILE_NAME = "capes.json";

    public Data data;

    public void init() {
        reload();
    }

    private void reload() {
        try {
            data = loadConfig(new Data(), Data.class, new File(Capes.configDir, FILE_NAME));
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
    }

    public class Data {
        public final boolean dropOnDeath = false;
    }
}
