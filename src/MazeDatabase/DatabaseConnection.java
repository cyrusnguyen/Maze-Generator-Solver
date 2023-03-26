package MazeDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @Author Michael Fox
 */
public class DatabaseConnection {

    /**
     * Creates an instance of the database connection
     */
    private static Connection dataConnect = null;


    /**
     * Constructor to initialize the connection.
     */
    public DatabaseConnection() {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");
            dataConnect = DriverManager.getConnection(url + '/' + schema, username, password);
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                System.err.println("Throwable: " + t);
                Throwable cause;
                while ((cause = t.getCause()) != null) {
                    System.err.println("Cause: " + cause);
                    break;
                }
            }
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**  Used to receive an instance of the data connection to the database
     * @return dataConnect
     */
    public static Connection getInstance() {
        if (dataConnect == null) {
            new DatabaseConnection();
        }
        return dataConnect;
    }
}
