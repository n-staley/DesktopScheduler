package DAO;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersDao {

    private static ObservableList<Customers> customersList = FXCollections.observableArrayList();

    public static ObservableList<Customers> getCustomersList() {
        return customersList;
    }

    public static void setCustomersList(ObservableList<Customers> customersList) {
        CustomersDao.customersList = customersList;
    }

    public static void populateCustomersList() {
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country\n" +
                "FROM customers\n" +
                "INNER JOIN first_level_divisions\n" +
                "ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                "INNER JOIN countries\n" +
                "ON first_level_divisions.Country_ID = countries.Country_ID";
        customersList.clear();

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                Customers newCustomer = new Customers();
                newCustomer.setCustomerID(rs.getInt("Customer_ID"));
                newCustomer.setCustomerName(rs.getString("Customer_Name"));
                newCustomer.setAddress(rs.getString("Address"));
                newCustomer.setPostalCode(rs.getString("Postal_Code"));
                newCustomer.setPhoneNumber(rs.getString("Phone"));
                newCustomer.setDivisionID(rs.getInt("Division_ID"));
                newCustomer.setDivisionName(rs.getString("Division"));
                newCustomer.setCountryID(rs.getInt("Country_ID"));
                newCustomer.setCountryName(rs.getString("Country"));
                customersList.add(newCustomer);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
