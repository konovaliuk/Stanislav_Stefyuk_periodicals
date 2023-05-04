package manager;

import java.util.ResourceBundle;

public class PageManager {
    private static PageManager instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "page";
    public static final String MAIN = "MAIN";
    public static final String SIGNUP = "SIGNUP";
    public static final String LOGIN = "LOGIN";
    public static final String CART = "CART";
    public static final String LIBRARY = "LIBRARY";


    public static PageManager getInstance() {
        if (instance == null) {
            instance = new PageManager();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }
    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
