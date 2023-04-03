package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {
    private static ObservableList<User> usersList = FXCollections.observableArrayList();
    private static ObservableList<String> usersNameList = FXCollections.observableArrayList();
    private static User loggedInUser = null;


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

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static ObservableList<User> getUsersList() {
        return usersList;
    }

    public static void setUsersList(ObservableList<User> usersList) {
        UserDao.usersList = usersList;
    }

    public static ObservableList<String> getUsersNameList() {
        return usersNameList;
    }

    public static int getUserID(String userName) {
        Optional<User> optionalUser = usersList.stream().filter(u -> u.getUserName().equals(userName)).findFirst();
        if (optionalUser.isPresent()) {
            return optionalUser.get().getUserID();
        }
        else return -1;
    }
}
