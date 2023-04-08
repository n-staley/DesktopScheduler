package Model;

/**
 * This class is for creating Countries.
 * @author Nicholas Staley
 */
public class Countries {
    /**
     * Holds the Country id number.
     */
    private int countryID;
    /**
     * Holds the Country name.
     */
    private String countryName;

    /**
     * Blank constructor to initialize a blank Country.
     */
    public Countries() {
    }

    /**
     * Country constructor to fully initialize a Country.
     * @param countryID The id of the country
     * @param countryName The name of the country
     */
    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * This method gets the Country id number.
     * @return Returns an integer of the country id number.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * This method sets the Country id number.
     * @param countryID The id number of the Country
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * This method gets the name of the Country.
     * @return Returns a string of the name of the country.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * This method sets the name of the country.
     * @param countryName The name of the country
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
