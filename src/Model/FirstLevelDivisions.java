package Model;

/**
 * This class is for creating First Level Divisions. A First Level Division is a State or Province.
 * @author Nicholas Staley
 */
public class FirstLevelDivisions {
    /**
     * Holds the First Level Division id number.
     */
    private int divisionID;
    /**
     * Holds the First Level Division name.
     */
    private String divisionName;
    /**
     * Holds the Country id number.
     */
    private int countryID;

    /**
     * Blank constructor to initialize a blank First Level Division.
     */
    public FirstLevelDivisions() {
    }

    /**
     * Constructor to initialize a complete First Level Division.
     * @param divisionID The id number of the Division
     * @param divisionName The name of the Division
     * @param countryID The Country id of the Division
     */
    public FirstLevelDivisions(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * This method gets the Division id number.
     * @return  Returns an integer of the Division id number.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * This method sets the Division id number.
     * @param divisionID The id number of the division
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * This method gets the name of the Division.
     * @return Returns a string of the name of the Division.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * This method sets the name of the Division.
     * @param divisionName The name of the Division
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * This method gets the Country id number.
     * @return Returns an integer of the Country id number.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * This method sets the Country id number.
     * @param countryID The id number of the country
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
