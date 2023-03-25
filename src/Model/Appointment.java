package Model;
/**
 * This class is for creating appointments.
 * @author Nicholas Staley
 */

import java.time.ZonedDateTime;

public class Appointment {
    /**
     * Holds the appointmentID.
     */
    private int appointmentID;
    /**
     * Holds the title of the appointment.
     */
    private String title;
    /**
     * Holds the description of the appointment.
     */
    private String description;
    /**
     * Holds the location of the appointment.
     */
    private String location;
    /**
     * Holds the type of the appointment.
     */
    private String type;
    /**
     * Holds the start date and time of the appointment.
     */
    private ZonedDateTime start;
    /**
     * Holds the end date and time of the appointment.
     */
    private ZonedDateTime end;
    /**
     * Holds the ID of the customer who the appointment is with.
     */
    private int custID;
    /**
     * Holds the ID of the user the appointment is with.
     */
    private int usersID;
    /**
     * Holds the ID of the contact for the appointment.
     */
    private int contactsID;


    /**
     * The constructor that initializes appointments.
     * @param appointmentID ID of the appointment
     * @param title Title of the appointment
     * @param description Description of the appointment
     * @param location Location of the appointment
     * @param type type of the appointment
     * @param start start date and time of the appointment
     * @param end end date and time of the appointment
     * @param custID ID for the customer appointment is with
     * @param usersID ID for the user the appointment is with
     * @param contactsID ID for the contact of the appointment
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int custID, int usersID, int contactsID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custID = custID;
        this.usersID = usersID;
        this.contactsID = contactsID;
    }

    /**
     * This method gets the appointment ID.
     * @return Returns the appointment id of the appointment.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * This method sets the appointment ID.
     * @param appointmentID The appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * This method gets the title of the appointment.
     * @return Returns the title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public int getUsersID() {
        return usersID;
    }

    public void setUsersID(int usersID) {
        this.usersID = usersID;
    }

    public int getContactsID() {
        return contactsID;
    }

    public void setContactsID(int contactsID) {
        this.contactsID = contactsID;
    }
}
