package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public static ObservableList<User> usersList = FXCollections.observableArrayList();


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

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User newUser = new User();
                newUser.setUserID(rs.getInt("User_ID"));
                newUser.setUserName(rs.getString("User_Name"));
                usersList.add(newUser);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
