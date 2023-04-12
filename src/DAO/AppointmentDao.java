package DAO;

import Model.Appointment;
import Utility.DateTimeAdjustment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for the jdbc queries to the appointments table in the database. Also, it holds the observable
 * lists for the appointments and types of appointments.
 */
public class AppointmentDao {
    /**
     * Holds the observable list of all appointments in the database.
     */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    /**
     * Holds the observable list of all appointments for the current week.
     */
    private static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    /**
     * Holds the observable list for all appointments for the current month.
     */
    private static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
    /**
     * Holds an observable list of strings of all the types of appointments.
     */
    private static ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();

    /**
     * This method populates all the observable appointment lists. Uses lambda functions in the predicates of the
     * filter methods to filter the appointments based off of the month and or week of the appointment.
     */
    public static void populateAppointmentLists() {
        String sql = "SELECT * FROM appointments";
        allAppointments.clear();
        weekAppointments.clear();
        monthAppointments.clear();
        appointmentTypeList.clear();
        Set<String> typeSet = new HashSet<>();

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Appointment newAppointment = new Appointment();
                    newAppointment.setAppointmentID(rs.getInt("Appointment_ID"));
                    newAppointment.setTitle(rs.getString("Title"));
                    if (rs.wasNull()) newAppointment.setTitle("null");
                    newAppointment.setDescription(rs.getString("Description"));
                    if (rs.wasNull()) newAppointment.setDescription("null");
                    newAppointment.setLocation(rs.getString("Location"));
                    if (rs.wasNull()) newAppointment.setLocation("null");
                    newAppointment.setType(rs.getString("Type"));
                    newAppointment.setStart(DateTimeAdjustment.changeToCurrentTimezone(rs.getTimestamp("Start").toInstant()));
                    newAppointment.setEnd(DateTimeAdjustment.changeToCurrentTimezone(rs.getTimestamp("End").toInstant()));
                    newAppointment.setCustID(rs.getInt("Customer_ID"));
                    newAppointment.setUsersID(rs.getInt("User_ID"));
                    newAppointment.setContactsID(rs.getInt("Contact_ID"));
                    newAppointment.setFormattedStart(DateTimeAdjustment.formatTime(newAppointment.getStart()));
                    newAppointment.setFormattedEnd(DateTimeAdjustment.formatTime(newAppointment.getEnd()));
                    typeSet.add(newAppointment.getType());
                    allAppointments.add(newAppointment);


            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        monthAppointments = allAppointments.stream().filter(a -> a.getStart().getMonth().equals(ZonedDateTime.now().getMonth())).collect(Collectors.toCollection(FXCollections::observableArrayList));
        weekAppointments = allAppointments.stream().filter(a -> a.getStart().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == ZonedDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)).collect(Collectors.toCollection(FXCollections::observableArrayList));
        appointmentTypeList.addAll(typeSet);
    }

    /**
     * This method adds a new appointment to the appointments table in the database.
     * @param title The title of the appointment
     * @param description The description of the appointment
     * @param location The location of the appointment
     * @param type The type of the appointment
     * @param start The instant of the start time of the appointment
     * @param end The instant of the end time of the appointment
     * @param create The current instant
     * @param createBy The logged-in user's username
     * @param lastUpdate The current instant
     * @param lastUpdateBy The logged-in user's username
     * @param customerID The id number of the customer for the appointment
     * @param userID The id number of the user for the appointment
     * @param contactID The id number of the contact for the appointment
     * @return Returns an integer of zero if the appointment wasn't added, and returns one if the appointment was added.
     */
    public static int addNewAppointment(String title, String description, String location, String type, Instant start, Instant end, Instant create, String createBy, Instant lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsInserted = 0;

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            if (title.equals("")) {
                ps.setNull(1,Types.NULL);
            }
            else {
                ps.setString(1, title);
            }
            if (description.equals("")) {
                ps.setNull(2, Types.NULL);
            }
            else {
                ps.setString(2,description);
            }
            if (location.equals("")) {
                ps.setNull(3, Types.NULL);
            }
            else {
                ps.setString(3, location);
            }
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.from(start));
            ps.setTimestamp(6, Timestamp.from(end));
            ps.setTimestamp(7, Timestamp.from(create));
            ps.setString(8, createBy);
            ps.setTimestamp(9, Timestamp.from(lastUpdate));
            ps.setString(10, lastUpdateBy);
            ps.setInt(11, customerID);
            ps.setInt(12, userID);
            ps.setInt(13, contactID);

            rowsInserted = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return rowsInserted;
    }

    /**
     * This method edits an appointment in the appointments table of the database.
     * @param title The title of the appointment
     * @param description The description of the appointment
     * @param location The location of the appointment
     * @param type The type of the appointment
     * @param start The instant of the start time of the appointment
     * @param end The instant of the end time of the appointment
     * @param lastUpdate The current instant
     * @param lastUpdateBy The logged-in user's username
     * @param customerID The customer id number for the appointment
     * @param userID The user id number for the appointment
     * @param contactID The contact id number for the appointment
     * @param appointmentID The appointment id number
     * @return Returns an integer of zero if the appointment was not updated, and greater than zero if it was updated.
     */
    public static int editAppointment(String title, String description, String location, String type, Instant start, Instant end, Instant lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID, int appointmentID) {
        String sql = "UPDATE appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, end = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        int rowsUpdated = 0;
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            if (title.equals("")) {
                ps.setNull(1,Types.NULL);
            }
            else {
                ps.setString(1, title);
            }
            if (description.equals("")) {
                ps.setNull(2, Types.NULL);
            }
            else {
                ps.setString(2,description);
            }
            if (location.equals("")) {
                ps.setNull(3, Types.NULL);
            }
            else {
                ps.setString(3, location);
            }
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.from(start));
            ps.setTimestamp(6, Timestamp.from(end));
            ps.setTimestamp(7, Timestamp.from(lastUpdate));
            ps.setString(8, lastUpdateBy);
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
            ps.setInt(12, appointmentID);

            rowsUpdated = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return rowsUpdated;
    }

    /**
     * This method deletes an appointment from the database.
     * @param appointmentID The id number of the appointment
     * @return Returns zero if the appointment wasn't deleted, and greater than zero if it was deleted.
     */
    public static int deleteAppointmentID(int appointmentID) {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        int wasDeleted = 0;

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setInt(1, appointmentID);

            wasDeleted = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return wasDeleted;
    }

    /**
     * This method deletes appointments that have the same customer id number.
     * @param customerID The id number of the customer
     * @return Returns zero if nothing was deleted or greater than zero if there was a deletion.
     */
    public static int deleteAppointmentCustomerID(int customerID) {
        String sql = "DELETE FROM client_schedule.appointments WHERE Customer_ID = ?";
        int wasDeleted = 0;

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);

            wasDeleted = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return wasDeleted;
    }

    /**
     * This method gets the observable list of all appointments.
     * @return Returns an observable list of appointments of all the appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * This method gets the observable list of appointments for the current week.
     * @return Returns an observable list of appointments for the current week.
     */
    public static ObservableList<Appointment> getWeekAppointments() {
        return weekAppointments;
    }

    /**
     * This method gets the observable list of appointments for the current month.
     * @return Returns an observable list of appointments for the current month.
     */
    public static ObservableList<Appointment> getMonthAppointments() {
        return monthAppointments;
    }

    /**
     * This method gets the observable list of all appointment types.
     * @return Returns an observable list of strings of all they appointment types.
     */
    public static ObservableList<String> getAppointmentTypeList() {
        return appointmentTypeList;
    }
}
