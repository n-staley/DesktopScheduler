package DAO;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomersDao {

    private static ObservableList<Customers> customersList = FXCollections.observableArrayList();
    private static ObservableList<String> customerNameList = FXCollections.observableArrayList();
    private static ObservableList<String> countryList = FXCollections.observableArrayList();
    private static ObservableList<String> divisionCanadaList = FXCollections.observableArrayList();
    private static ObservableList<String> divisionUSAList = FXCollections.observableArrayList();
    private static ObservableList<String> divisionUKList = FXCollections.observableArrayList();

    public static ObservableList<Customers> getCustomersList() {
        return customersList;
    }

    public static void populateCustomersList() {
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country\n" +
                "FROM customers\n" +
                "INNER JOIN first_level_divisions\n" +
                "ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                "INNER JOIN countries\n" +
                "ON first_level_divisions.Country_ID = countries.Country_ID";
        customersList.clear();
        customerNameList.clear();

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
                customerNameList.add(newCustomer.getCustomerName());
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void populateCountryList() {
        String sql = "SELECT * FROM countries";
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                countryList.add(rs.getString("Country"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void populateAllDivisionsList() {
        String sql = "SELECT * FROM first_level_divisions";
        divisionCanadaList.clear();
        divisionUSAList.clear();
        divisionUKList.clear();
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                if (rs.getInt("Country_ID") == 1) {
                    divisionUSAList.add(rs.getString("Division"));
                }
                if (rs.getInt("Country_ID") == 2) {
                    divisionUKList.add(rs.getString("Division"));
                }
                if (rs.getInt("Country_ID") == 3) {
                    divisionCanadaList.add(rs.getString("Division"));
                }

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void populateAllCustomersLists() {
        populateCustomersList();
        populateCountryList();
        populateAllDivisionsList();
    }

    public static ObservableList<String> getCustomerIDList() {
        return customerNameList;
    }

    public static ObservableList<String> getCountryList() {
        return countryList;
    }

    public static ObservableList<String> getDivisionCanadaList() {
        return divisionCanadaList;
    }

    public static ObservableList<String> getDivisionUSAList() {
        return divisionUSAList;
    }

    public static ObservableList<String> getDivisionUKList() {
        return divisionUKList;
    }

    public static int getCustomerIDNumber(String customerName) {
        Optional<Customers> optionalCustomer = customersList.stream().filter(c -> c.getCustomerName().equals(customerName)).findFirst();
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get().getCustomerID();
        }
        else {
            return -1;
        }
    }

    public static String getCustomerName(int customerID) {
        Optional<Customers> optionalCustomer = customersList.stream().filter(c -> c.getCustomerID() == customerID).findFirst();
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get().getCustomerName();
        }
        else {
            return null;
        }
    }
}
