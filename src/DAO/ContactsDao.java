package DAO;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * This class is responsible for doing jdbc queries to the Contacts table in the database. Also, it holds observable
 * lists for the contacts and their names.
 * @author Nicholas Staley
 */
public class ContactsDao {
    /**
     * Holds the observable list of contacts.
     */
    private static ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
    /**
     * Holds the observable list of contact names.
     */
    private static ObservableList<String> contactsNameList = FXCollections.observableArrayList();

    /**
     * This method sets both the contact name list and the contacts list.
     */
    public static void  setContactsList() {
        String sql = "SELECT * FROM contacts";
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            contactsNameList.clear();
            contactsList.clear();
            while (rs.next()) {
                Contacts newContact = new Contacts();
                newContact.setContactID(rs.getInt("Contact_ID"));
                newContact.setContactName(rs.getString("Contact_Name"));
                newContact.setEmail(rs.getString("Email"));
                contactsList.add(newContact);
                contactsNameList.add(newContact.getContactName());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets the observable list of contacts.
     * @return Returns an observable list of contacts.
     */
    public static ObservableList<Contacts> getContactsList() {
        return contactsList;
    }

    /**
     * This method gets the observable list of contact names.
     * @return Returns an observable list of strings of contact names.
     */
    public static ObservableList<String> getContactsNameList() {
        return contactsNameList;
    }

    /**
     * This method gets the contact id number given a contact name. Uses a lambda expression for the predicate of the
     * filter method to check the contact names for equality.
     * @param contactName The name of the contact
     * @return Returns an integer of the id number of the contact if it is found, or negative one if it is not.
     */
    public static int getContactIDNumber (String contactName) {
        Optional<Contacts> optionalContact = contactsList.stream().filter(c -> c.getContactName().equals(contactName)).findFirst();
        if (optionalContact.isPresent()) {
            return optionalContact.get().getContactID();
        }
        else {
            return -1;
        }
    }

    /**
     * This method gets the contact name given a contact id number. Uses a lambda expression for the predicate of the
     * filter method to check the contact id numbers for equality.
     * @param contactID The id number of the contact
     * @return Returns a string of the contact name if found, or returns null if not.
     */
    public static String getContactName (int contactID) {
        Optional<Contacts> optionalContact = contactsList.stream().filter(c -> c.getContactID() == contactID).findFirst();
        if (optionalContact.isPresent()) {
            return optionalContact.get().getContactName();
        }
        else {
            return "null";
        }
    }
}
