package DAO;

import Model.Appointment;
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

/**
 * This class is responsible for doing jdbc queries to the customer table, the first level division table, and the countries table.
 * Also, it maintains observable lists for customers, divisions, and countries.
 * @author Nicholas Staley
 */
public class CustomersDao {
    /**
     * Holds the observable list of customers.
     */
    private static ObservableList<Customers> customersList = FXCollections.observableArrayList();
    /**
     * Holds the observable list of customer names.
     */
    private static ObservableList<String> customerNameList = FXCollections.observableArrayList();
    /**
     * Holds the observable list of first level divisions.
     */
    private static ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();
    /**
     * Holds the observable list of countries.
     */
    private static ObservableList<Countries> countriesList = FXCollections.observableArrayList();
    /**
     * Holds the observable list of country names.
     */
    private static ObservableList<String> countryNameList = FXCollections.observableArrayList();
    /**
     * Holds the observable list of Canada first level division names.
     */
    private static ObservableList<String> divisionCanadaList = FXCollections.observableArrayList();
    /**
     * HOlds the observable list of the USA first level division names.
     */
    private static ObservableList<String> divisionUSAList = FXCollections.observableArrayList();
    /**
     * Holds the observable list of th UK first level division names.
     */
    private static ObservableList<String> divisionUKList = FXCollections.observableArrayList();

    /**
     * This method gets the observable list of customers.
     * @return Returns an observable list of customers.
     */
    public static ObservableList<Customers> getCustomersList() {
        return customersList;
    }

    /**
     * This method sets the observable list of customers and customer names.
     */
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

    /**
     * This method sets the observable list of countries and country names.
     */
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

    /**
     * This method sets the four observable lists of first level divisions.
     */
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

    /**
     * This method populates the first level division lists, the country lists and the customer lists.
     */
    public static void populateAllCustomersLists() {
        populateCustomersList();
        populateCountryList();
        populateAllDivisionsList();
    }

    /**
     * This method deletes a customer from the database along with all the appointments that the customer had, if they
     * had any.
     * @param customerID The customer id for customer to be deleted.
     * @return Returns an integer of 0 if the customer was deleted, or greater than zero if the customer wasn't deleted.
     */
    public static int deleteCustomer(int customerID) {
        /*String sql = "DELETE client_schedule.customers, client_schedule.appointments \n" +
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
        */
        AppointmentDao.deleteAppointmentCustomerID(customerID);
        int wasDeleted = 0;
        String noAppointments = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(noAppointments)) {
            ps.setInt(1, customerID);
            wasDeleted = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return wasDeleted;
    }

    /**
     * This method adds a new customer to the customer table in the database.
     * @param name The name of the customer
     * @param address The address of the customer
     * @param postalCode The postal code of the customer
     * @param phone The phone number of the customer
     * @param create The current time instant
     * @param createBy The user that is currently logged in
     * @param update The current time instant
     * @param updateBy The username of the user that is currently logged in
     * @param divisionID The first level division id number
     * @return Returns an integer of zero if the customer wasn't added, or returns a one if the customer was added.
     */
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

    /**
     * This method updates a customer in the database.
     * @param name The name of the customer
     * @param address The address of the customer
     * @param postalCode The postal code of the customer
     * @param phone The phone number of the customer
     * @param update The current time instant
     * @param updateBy The username of the user that is currently logged in
     * @param divisionID The first level division id number
     * @param customerID The customer id number
     * @return Returns an integer of zero if the customer was not updated, or greater than zero if it was updated.
     */
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

    /**
     * This method gets the observable list of country names.
     * @return Returns an observable list of strings of country names.
     */
    public static ObservableList<String> getCountryNameList() {
        return countryNameList;
    }

    /**
     * This method get the observable list of Canada first level divisions.
     * @return Returns an observable list of strings of the first level division names in Canada.
     */
    public static ObservableList<String> getDivisionCanadaList() {
        return divisionCanadaList;
    }

    /**
     * This method gets the observable list of the USA  first level divisions.
     * @return Returns an observable list of strings of the first level division names in the USA.
     */
    public static ObservableList<String> getDivisionUSAList() {
        return divisionUSAList;
    }

    /**
     * This method gets the observable list of the UK first level divisions.
     * @return Returns an observable list of strings of the first level division names in the UK.
     */
    public static ObservableList<String> getDivisionUKList() {
        return divisionUKList;
    }

    /**
     * This method gets the observable list of all first level divisions.
     * @return Returns an observable list of first level divisions.
     */
    public static ObservableList<FirstLevelDivisions> getDivisionsList() {
        return divisionsList;
    }

    /**
     * This method gets the observable list of all countries.
     * @return Returns an observable list of countries.
     */
    public static ObservableList<Countries> getCountriesList() {
        return countriesList;
    }

    /**
     * This method gets the customer id number of the provided customer name. Uses a lambda expression for the predicate
     * of the filter method to check if the customer names are equal.
     * @param customerName The name of the customer
     * @return Returns an integer of the customer id number or negative one if there isn't one.
     */
    public static int getCustomerIDNumber(String customerName) {
        Optional<Customers> optionalCustomer = customersList.stream().filter(c -> c.getCustomerName().equals(customerName)).findFirst();
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get().getCustomerID();
        }
        else {
            return -1;
        }
    }

    /**
     * This method gets the name of the customer of the provided customer id number. Uses a lambda expression for the
     * predicate of the filter method to check customer ids for equality.
     * @param customerID The id number of the customer
     * @return Returns a string of the customer name, or null if there isn't a matching customer name.
     */
    public static String getCustomerName(int customerID) {
        Optional<Customers> optionalCustomer = customersList.stream().filter(c -> c.getCustomerID() == customerID).findFirst();
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get().getCustomerName();
        }
        else {
            return "null";
        }
    }

    /**
     * This method gets the first level division id number given a division name. Uses a lambda expression for the predicate
     * in the filter to check the division name for equality.
     * @param divisionName The first level division name
     * @return Returns an integer of the first level division id number.
     */
    public  static int getDivisionID(String divisionName) {
        Optional<FirstLevelDivisions> optionalDivision = divisionsList.stream().filter(d -> d.getDivisionName().equals(divisionName)).findFirst();
        if (optionalDivision.isPresent()) {
            return optionalDivision.get().getDivisionID();
        }
        else return -1;
    }

    /**
     * This method gets the first level division name given a first level division id number. Uses a lambda expression
     * for the predicate of the filter method to check the first level division id numbers for equality.
     * @param divisionID The first level division id number
     * @return Returns a string of the name of the first level division if found, or returns error if not found.
     */
    public static String getDivisionName(int divisionID) {
        Optional<FirstLevelDivisions> optionalDivision = divisionsList.stream().filter(d -> d.getDivisionID() == divisionID).findFirst();
        if (optionalDivision.isPresent()) {
            return optionalDivision.get().getDivisionName();
        }
        else return "error";
    }

    /**
     * This method gets the country name given the country id number. Uses a lambda expression for the predicate of the
     * filter method to check the country ids for equality.
     * @param countryID The country id number
     * @return Returns a string of the country name if a match is found, or returns error if it is not.
     */
    public static String getCountryName(int countryID) {
        Optional<Countries> optionalCountries = countriesList.stream().filter(c -> c.getCountryID() == countryID).findFirst();
        if (optionalCountries.isPresent()) {
            return optionalCountries.get().getCountryName();
        }
        else return "error";
    }

    /**
     * This method gets the observable list of customer names.
     * @return Returns an observable list of strings of the customer names.
     */
    public static ObservableList<String> getCustomerNameList() {
        return customerNameList;
    }
}
