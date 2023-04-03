package DAO;

import Model.Appointment;
import Utility.DateTimeAdjustment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.stream.Collectors;

public class AppointmentDao {
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

    public static void populateAppointmentLists() {
        String sql = "SELECT * FROM appointments";
        allAppointments.clear();
        weekAppointments.clear();
        monthAppointments.clear();

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
                    if (rs.wasNull()) newAppointment.setType("null");
                    newAppointment.setStart(DateTimeAdjustment.changeToCurrentTimezone(rs.getTimestamp("Start").toInstant()));
                    newAppointment.setEnd(DateTimeAdjustment.changeToCurrentTimezone(rs.getTimestamp("End").toInstant()));
                    newAppointment.setCustID(rs.getInt("Customer_ID"));
                    newAppointment.setUsersID(rs.getInt("User_ID"));
                    newAppointment.setContactsID(rs.getInt("Contact_ID"));
                    newAppointment.setFormattedStart(DateTimeAdjustment.formatTime(newAppointment.getStart()));
                    newAppointment.setFormattedEnd(DateTimeAdjustment.formatTime(newAppointment.getEnd()));
                    allAppointments.add(newAppointment);


            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        monthAppointments = (ObservableList<Appointment>) allAppointments.stream().filter(a -> a.getStart().getMonth().equals(ZonedDateTime.now().getMonth())).collect(Collectors.toCollection(FXCollections::observableArrayList));
        weekAppointments = (ObservableList<Appointment>) allAppointments.stream().filter(a -> a.getStart().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == ZonedDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


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
