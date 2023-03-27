package Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

public abstract class LoginLogger {

    public static void log(String username, String password, ZonedDateTime dateTime, boolean success) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("login_activity.txt", true));
            pw.println("Username: " + username + ", password: " + password + ", Date/Time: " + dateTime + ", Successful login: " + success);
            pw.flush();
            pw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
