package codes.reason.wool.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

public class Config {

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            File file = new File("config.properties");
            if (!file.exists()) {
                // Save the default config.properties file
                InputStream stream = Config.class.getResourceAsStream("/config.properties");
                if (stream == null) {
                    throw new IllegalStateException("Could not read config.properties");
                }
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(stream.readAllBytes());
                outputStream.close();
                System.out.println("Please configure the application in the config.properties file.");
                System.exit(0);
            }
            PROPERTIES.load(new FileInputStream("config.properties"));
        } catch (Exception e) {
            System.err.println("Could not read config.properties");
            System.exit(0);
        }
    }

    private static String get(String key) {
        if (System.getenv().containsKey(key)) {
            return System.getenv(key);
        }
        return PROPERTIES.getProperty(key);
    }

    public static String getString(String key) {
        return get(key);
    }

    public static UUID getUUID(String key) {
        String value = get(key);
        if (value == null) {
            return null;
        }
        return UUID.fromString(value);
    }

    public static int getInteger(String key) {
        String value = get(key);
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public static boolean getBoolean(String key) {
        String value = get(key);
        return value != null && value.equals("true");
    }

}
