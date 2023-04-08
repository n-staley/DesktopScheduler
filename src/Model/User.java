package Model;

/**
 * This class is for creating users.
 * @author Nicholas Staley
 */
public class User {
    /**
     * Holds the user id number.
     */
    private int userID;
    /**
     * Holds the username.
     */
    private String userName;

    /**
     * Constructor to create a default user.
     */
    public User() {
        userID = -1;
        userName = "";
    }

    /**
     * Constructor to create a complete user.
     * @param userID The id of the user
     * @param userName The username
     */
    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    /**
     * This method gets the user id number.
     * @return Returns an integer of the user id number.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * This method sets the user id number.
     * @param userID The id number of the user.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * This method gets the username.
     * @return Returns a string of the username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method sets the username.
     * @param userName the username of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
