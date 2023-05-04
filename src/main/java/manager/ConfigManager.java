package manager;

import java.util.ResourceBundle;

public class ConfigManager {
    private static ConfigManager instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "config";
    public static final String HOST = "server.host";
    public static final String PORT = "server.port";
    public static final String TABLE = "database.table";
    public static final String USER = "database.user";
    public static final String PASSWORD = "database.password";


    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }
    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
