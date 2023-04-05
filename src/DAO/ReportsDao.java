package DAO;

import Model.Appointment;
import Utility.DateTimeAdjustment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReportsDao {
    public static final ObservableList<String> MonthList = FXCollections.observableList(List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
    private static ObservableList<Appointment> appointmentsByContactList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsByCustomersList = FXCollections.observableArrayList();


    public static int appointmentTypePerMonth(String type, String month) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE Type = ? AND MONTH(Start) = ?";
        int count = 0;
        int monthInt = switch (month) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            case "December" -> 12;
            default -> 0;
        };

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setInt(2, monthInt);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                count = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    public static void appointmentsByContact(int contactID) {
        String sql = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
        appointmentsByContactList.clear();
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setInt(1, contactID);
            try (ResultSet rs = ps.executeQuery()) {
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
                    appointmentsByContactList.add(newAppointment);

                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void appointmentsByCustomer(int customerID) {
        String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ?";
        appointmentsByCustomersList.clear();
        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
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
                    appointmentsByCustomersList.add(newAppointment);

                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ObservableList<Appointment> getAppointmentsByContactList() {
        return appointmentsByContactList;
    }

    public static ObservableList<Appointment> getAppointmentsByCustomersList() {
        return appointmentsByCustomersList;
    }
}
