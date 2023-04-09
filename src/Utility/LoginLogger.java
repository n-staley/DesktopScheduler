package Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 * This class is used to log the login attempts made by users.
 */
public abstract class LoginLogger {

    /**
     * This method logs the login attempts made by users and saves them to the login_activity.txt file.
     * @param username The username used to log in
     * @param password The password used to log in
     * @param dateTime The zoned date time of the login attempt
     * @param success Whether the login was successful or not
     */
    public static void log(String username, String password, ZonedDateTime dateTime, boolean success) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("login_activity.txt", true))) {
            pw.println("Username: " + username + ", password: " + password + ", Date/Time: " + dateTime + ", Successful login: " + success);
            pw.flush();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

