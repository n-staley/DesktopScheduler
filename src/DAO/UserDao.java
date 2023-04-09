package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * This class is responsible for the jdbc queries to the users table. Also, it maintains a list of users from the database
 * and maintains which user is currently logged into the scheduling system.
 * @author Nicholas Staley
 */
public class UserDao {
    /**
     * Holds an observable list of all users stored in the users table of the database.
     */
    private static ObservableList<User> usersList = FXCollections.observableArrayList();
    /**
     * Holds an observable list of the usernames of all users in the database.
     */
    private static ObservableList<String> usersNameList = FXCollections.observableArrayList();
    /**
     * Holds the user that is currently logged into the scheduling application.
     */
    private static User loggedInUser = null;

    /**
     * This method checks the database to see if the entered username and password are a match to a user in the users table
     * of the database.
     * @param username The username entered by the user
     * @param password The password entered by the user
     * @return Returns an int of 0 if the username and password don't match, and 1 if the username and password matches a user.
     */
    public static int loginQuery(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE (User_Name = ? AND Password = ?)";
        int result = 0;
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
           try (ResultSet rs = ps.executeQuery()) {
               rs.next();
               result = rs.getInt(1);
           }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return result;
    }

    /**
     * This method creates both the observable lists of users and the observable list of usernames.
     */
    public static void createUserList() {
        String sql = "SELECT * FROM users";
        usersList.clear();
        usersNameList.clear();

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User newUser = new User();
                newUser.setUserID(rs.getInt("User_ID"));
                newUser.setUserName(rs.getString("User_Name"));
                usersList.add(newUser);
                usersNameList.add(newUser.getUserName());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the logged-in user.
     * @param username username of user that logged in successfully
     */
    public static void setUser(String username) {
        Optional<User> optionalUser;
        optionalUser = usersList.stream().filter(u -> u.getUserName().equals(username)).findFirst();
        if (optionalUser.isPresent()) {
            loggedInUser = optionalUser.get();
        }
        else {
            System.out.println("user not found");
            loggedInUser = new User(-1, "unknown");
        }
    }

    /**
     * This method gets the logged-in user.
     * @return Returns a User object of the logged-in user.
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * This method gets the observable list of users.
     * @return Returns the observable list of users.
     */
    public static ObservableList<User> getUsersList() {
        return usersList;
    }

    /**
     * This method gets the observable list of usernames.
     * @return Returns an observable list of string usernames.
     */
    public static ObservableList<String> getUsersNameList() {
        return usersNameList;
    }

    /**
     * This method gets the id of the user whose username was provided.
     * @param userName The username used to get the id
     * @return Returns an integer of the user id number.
     */
    public static int getUserID(String userName) {
        Optional<User> optionalUser = usersList.stream().filter(u -> u.getUserName().equals(userName)).findFirst();
        if (optionalUser.isPresent()) {
            return optionalUser.get().getUserID();
        }
        else return -1;
    }

    /**
     * This method gets the username of the user whose user id was provided.
     * @param userID The user's id number
     * @return Returns a string of the user's username.
     */
    public static String getUserName(int userID) {
        Optional<User> optionalUser = usersList.stream().filter(u -> u.getUserID() == userID).findFirst();
        if (optionalUser.isPresent()) {
            return optionalUser.get().getUserName();
        }
        else return null;
    }
}
