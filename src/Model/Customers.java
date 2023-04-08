package Model;

/**
 * This class is for creating customers.
 * @author Nicholas Staley
 */
public class Customers {
    /**
     * Holds the customer id number.
     */
    private int customerID;
    /**
     * Holds the customer's name.
     */
    private String customerName;
    /**
     * Holds the customer's address.
     */
    private String address;
    /**
     * Holds the customer's postal code.
     */
    private String postalCode;
    /**
     * Holds the customer's phone number.
     */
    private String phoneNumber;
    /**
     * Holds the customer's First Level Division id number.
     */
    private int divisionID;
    /**
     * Holds the customer's First Level Division name.
     */
    private String divisionName;
    /**
     * Holds the customer's Country id number.
     */
    private int countryID;
    /**
     * Holds the customer's Country name.
     */
    private String countryName;

    /**
     * Blank constructor to initialize a blank customer.
     */
    public Customers() {
    }

    /**
     * Constructor to initialize a complete customer.
     * @param customerID The id number of the customer
     * @param customerName The name of the customer
     * @param address The address of the customer
     * @param postalCode The postal code of the customer
     * @param phoneNumber The phone number of the customer
     * @param divisionID The First Level Division id of the customer
     * @param divisionName The First Level Division name of the customer
     * @param countryID The Country id of the customer
     * @param countryName The Country name of the customer
     */
    public Customers(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID, String divisionName, int countryID, String countryName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * This method gets the customer's id number.
     * @return Returns an integer id number for the customer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * This method sets the customer's id number.
     * @param customerID The id number of the customer
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * This method gets the customer's name.
     * @return Returns a string of the customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method sets the customer's name.
     * @param customerName The name of the customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * This method gets the customer's address.
     * @return Returns a string of the customer's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method sets the customer's address.
     * @param address The address of the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method gets the postal code of the customer.
     * @return Returns a string of the postal code of the customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * This method sets the customer's postal code.
     * @param postalCode The postal code of the customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * This method gets the customer's phone number.
     * @return Returns a string of the customer's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method sets the customer's phone number.
     * @param phoneNumber The phone number of the customer
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method gets the customer's First Level Division id number.
     * @return Returns an integer of the customer's First Level Division id number.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * This method sets the customer's First Level Division id number.
     * @param divisionID The First Level Division id number of the customer
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * This method gets the customer's First Level Division name.
     * @return Returns a string of the customer's First Level Division name.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * This method sets the customer's First Level Division name.
     * @param divisionName The First Level Division name of the customer
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * This method gets the customer's Country id number.
     * @return Returns an integer of the customer's Country id number.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * This method sets the customer's Country id number.
     * @param countryID The Country id number of the customer
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * This method gets the customer's Country name.
     * @return Returns a string of the customer's Country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * This method sets the customer's Country name.
     * @param countryName The Country name of the customer
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
