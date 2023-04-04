package DAO;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ContactsDao {
    private static ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
    private static ObservableList<String> contactsNameList = FXCollections.observableArrayList();

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

    public static ObservableList<Contacts> getContactsList() {
        return contactsList;
    }

    public static ObservableList<String> getContactsNameList() {
        return contactsNameList;
    }

    public static int getContactIDNumber (String contactName) {
        Optional<Contacts> optionalContact = contactsList.stream().filter(c -> c.getContactName().equals(contactName)).findFirst();
        if (optionalContact.isPresent()) {
            return optionalContact.get().getContactID();
        }
        else {
            return -1;
        }
    }
    public static String getContactName (int contactID) {
        Optional<Contacts> optionalContact = contactsList.stream().filter(c -> c.getContactID() == contactID).findFirst();
        if (optionalContact.isPresent()) {
            return optionalContact.get().getContactName();
        }
        else {
            return null;
        }
    }
}
