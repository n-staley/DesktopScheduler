package Model;

/**
 * This class is for creating contacts.
 * @author Nicholas Staley
 */
public class Contacts {
    /**
     * Holds the contact id number.
     */
    private int contactID;
    /**
     * Holds the contact's name.
     */
    private String contactName;
    /**
     * Holds the contact's email address.
     */
    private String email;

    /**
     * Blank constructor that initializes a blank contact.
     */
    public Contacts() {
    }

    /**
     * Full constructor that initializes contacts.
     * @param contactID The contact's id number
     * @param contactName The contact's name
     * @param email the contact's email address
     */
    public Contacts(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * This method gets the contact's id number.
     * @return Returns an integer of the id number.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * This method sets the contact's id number.
     * @param contactID The id number of the contact
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * This method gets the contact's name.
     * @return Returns a string of the contact's name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method sets the contact's name.
     * @param contactName The name of the contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * This method gets the contact's email address.
     * @return Returns a string of the contact's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method sets the contact's email address.
     * @param email The email address of the contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
