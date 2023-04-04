package DAO;

import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class CustomersDao {

    private static ObservableList<Customers> customersList = FXCollections.observableArrayList();
    private static ObservableList<String> customerNameList = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();
    private static ObservableList<Countries> countriesList = FXCollections.observableArrayList();
    private static ObservableList<String> countryNameList = FXCollections.observableArrayList();
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
        countriesList.clear();
        countryNameList.clear();
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                Countries newCountry = new Countries();
                newCountry.setCountryID(rs.getInt("Country_ID"));
                newCountry.setCountryName(rs.getString("Country"));
                countriesList.add(newCountry);
                countryNameList.add(rs.getString("Country"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void populateAllDivisionsList() {
        String sql = "SELECT * FROM first_level_divisions";
        divisionsList.clear();
        divisionCanadaList.clear();
        divisionUSAList.clear();
        divisionUKList.clear();
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                FirstLevelDivisions newDivision = new FirstLevelDivisions();
                newDivision.setDivisionID(rs.getInt("Division_ID"));
                newDivision.setDivisionName(rs.getString("Division"));
                newDivision.setCountryID(rs.getInt("Country_ID"));
                divisionsList.add(newDivision);
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

    public static int deleteCustomer(int customerID) {
        String sql = "DELETE client_schedule.customers, client_schedule.appointments \n" +
                "FROM client_schedule.customers \n" +
                "INNER JOIN client_schedule.appointments ON client_schedule.customers.Customer_ID = client_schedule.appointments.Customer_ID\n" +
                "WHERE client_schedule.customers.Customer_ID = ?";
        int wasDeleted = 0;

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            wasDeleted = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (wasDeleted == 0) {
            String noAppointments = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
            try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(noAppointments)) {
                ps.setInt(1, customerID);
                wasDeleted = ps.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wasDeleted;
    }

    public static int addCustomer(String name, String address, String postalCode, String phone, Instant create, String createBy, Instant update, String updateBy, int divisionID) {
        String sql = "INSERT INTO client_schedule.customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int wasAdded = 0;

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.from(create));
            ps.setString(6, createBy);
            ps.setTimestamp(7, Timestamp.from(update));
            ps.setString(8, updateBy);
            ps.setInt(9, divisionID);

            wasAdded = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return wasAdded;
    }

    public static int updateCustomer(String name, String address, String postalCode, String phone, Instant update, String updateBy, int divisionID, int customerID) {
        String sql = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        int wasUpdated = 0;
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.from(update));
            ps.setString(6, updateBy);
            ps.setInt(7, divisionID);
            ps.setInt(8, customerID);

            wasUpdated = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return wasUpdated;
    }

    public static ObservableList<String> getCustomerIDList() {
        return customerNameList;
    }

    public static ObservableList<String> getCountryNameList() {
        return countryNameList;
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

    public static ObservableList<FirstLevelDivisions> getDivisionsList() {
        return divisionsList;
    }

    public static void setDivisionsList(ObservableList<FirstLevelDivisions> divisionsList) {
        CustomersDao.divisionsList = divisionsList;
    }

    public static ObservableList<Countries> getCountriesList() {
        return countriesList;
    }

    public static void setCountriesList(ObservableList<Countries> countriesList) {
        CustomersDao.countriesList = countriesList;
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

    public  static int getDivisionID(String divisionName) {
        Optional<FirstLevelDivisions> optionalDivision = divisionsList.stream().filter(d -> d.getDivisionName().equals(divisionName)).findFirst();
        if (optionalDivision.isPresent()) {
            return optionalDivision.get().getDivisionID();
        }
        else return -1;
    }

    public static String getDivisionName(int divisionID) {
        Optional<FirstLevelDivisions> optionalDivision = divisionsList.stream().filter(d -> d.getDivisionID() == divisionID).findFirst();
        if (optionalDivision.isPresent()) {
            return optionalDivision.get().getDivisionName();
        }
        else return "error";
    }

    public static String getCountryName(int countryID) {
        Optional<Countries> optionalCountries = countriesList.stream().filter(c -> c.getCountryID() == countryID).findFirst();
        if (optionalCountries.isPresent()) {
            return optionalCountries.get().getCountryName();
        }
        else return "error";
    }
}
