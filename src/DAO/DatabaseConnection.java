package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is used to create a connection to the database, and to close the connection when done.
 * @author Nicholas Staley
 */
public abstract class DatabaseConnection {
    /**
     * Holds the string protocol for the jdbc url.
     */
    private static final String protocol = "jdbc";
    /**
     * Holds the string vendor sequence for the jdbc url.
     */
    private static final String vendor = ":mysql:";
    /**
     * Holds the string location part of the jdbc url.
     */
    private static final String location = "//localhost/";
    /**
     * Holds the database name portion of the jdbc url.
     */
    private static final String databaseName = "client_schedule";
    /**
     * Hold the jdbc url for making the connection.
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    /**
     * Holds the name of the jdbc driver used in making the connection.
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    /**
     * Holds the username used for making the connection.
     */
    private static final String userName = "sqlUser";
    /**
     * Holds the password used for making the connection.
     */
    private static final String password = "Passw0rd!";
    /**
     * Holds the created connection used to execute queries to the database.
     */
    public static Connection connection;

    /**
     * This method creates a connection to the database being used.
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
        }
        catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method closes the connection to the database being used.
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
