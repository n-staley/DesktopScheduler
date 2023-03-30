package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    public static int loginQuery(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE (User_Name = ? AND Password = ?)";
        int result = 0;
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
           try (ResultSet rs = ps.executeQuery()) {

               rs.next();
               result = rs.getInt(1);
               return result;
           }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return result;

    }


}
