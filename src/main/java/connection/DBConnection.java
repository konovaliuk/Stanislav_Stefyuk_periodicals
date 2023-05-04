package connection;

import manager.ConfigManager;
import manager.PageManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

public class DBConnection {

    private static BasicDataSource dataSource = new BasicDataSource();

    static{

    }

    public DBConnection() throws FileNotFoundException {
    }

    public static Connection getConnection() throws SQLException, IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
        String host = ConfigManager.getInstance().getProperty(ConfigManager.HOST);
        String port = ConfigManager.getInstance().getProperty(ConfigManager.PORT);
        String table = ConfigManager.getInstance().getProperty(ConfigManager.TABLE);
        String user = ConfigManager.getInstance().getProperty(ConfigManager.USER);
        String password = ConfigManager.getInstance().getProperty(ConfigManager.PASSWORD);
        dataSource.setUrl("jdbc:mysql://"+host+":"+port+"/"+table);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(10);
        dataSource.setMinIdle(5);
        //dataSource.setDriverClassName("org.mysql.jdbc.Driver");
        return dataSource.getConnection();
    }

}
