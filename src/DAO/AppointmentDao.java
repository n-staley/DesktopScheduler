package DAO;

import Model.Appointment;
import Utility.DateTimeAddressAdjustment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class AppointmentDao {
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

    public static void populateAppointmentLists(ZonedDateTime startTime) {
        String sql = "SELECT * FROM appointments";
        allAppointments.clear();
        weekAppointments.clear();
        monthAppointments.clear();

        try (PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Appointment newAppointment = new Appointment();
                    newAppointment.setAppointmentID(rs.getInt("Appointment_ID"));
                    newAppointment.setTitle(rs.getString("Title"));
                    newAppointment.setDescription(rs.getString("Description"));
                    newAppointment.setLocation(rs.getString("Location"));
                    newAppointment.setType(rs.getString("Type"));
                    newAppointment.setStart(DateTimeAddressAdjustment.changeToCurrentTimezone(rs.getTimestamp("Start").toInstant()));
                    newAppointment.setEnd(DateTimeAddressAdjustment.changeToCurrentTimezone(rs.getTimestamp("End").toInstant()));
                    newAppointment.setCustID(rs.getInt("Customer_ID"));
                    newAppointment.setUsersID(rs.getInt("User_ID"));
                    newAppointment.setContactsID(rs.getInt("Contact_ID"));
                    newAppointment.setFormattedStart(DateTimeAddressAdjustment.formatTime(newAppointment.getStart()));
                    newAppointment.setFormattedEnd(DateTimeAddressAdjustment.formatTime(newAppointment.getEnd()));
                    allAppointments.add(newAppointment);
            }

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        weekAppointments = (ObservableList<Appointment>) allAppointments.stream().filter(a -> a.getStart().isAfter(startTime)).filter(a -> a.getStart().isBefore(startTime.plusWeeks(1))).collect(Collectors.toCollection(FXCollections::observableArrayList));
        monthAppointments = (ObservableList<Appointment>) allAppointments.stream().filter(a -> a.getStart().isAfter(startTime)).filter(a -> a.getStart().isBefore(startTime.plusMonths(1))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    public static void addNewAppointment() {

    }

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        AppointmentDao.allAppointments = allAppointments;
    }

    public static ObservableList<Appointment> getWeekAppointments() {
        return weekAppointments;
    }

    public static void setWeekAppointments(ObservableList<Appointment> weekAppointments) {
        AppointmentDao.weekAppointments = weekAppointments;
    }

    public static ObservableList<Appointment> getMonthAppointments() {
        return monthAppointments;
    }

    public static void setMonthAppointments(ObservableList<Appointment> monthAppointments) {
        AppointmentDao.monthAppointments = monthAppointments;
    }
}
