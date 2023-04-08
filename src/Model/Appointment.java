package Model;

import java.time.ZonedDateTime;
/**
 * This class is for creating appointments.
 * @author Nicholas Staley
 */
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
     * Holds the formatted start date and time.
     */
    private String formattedStart;
    /**
     * Holds the formatted end date and time.
     */
    private String formattedEnd;

    /**
     * Blank constructor that initializes a blank appointment.
     */
    public Appointment() {
    }

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
    public Appointment(int appointmentID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int custID, int usersID, int contactsID, String formattedStart, String formattedEnd) {
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
        this.formattedStart = formattedStart;
        this.formattedEnd = formattedEnd;
    }

    /**
     * This method gets the appointment id number.
     * @return Returns an integer of the appointment id for the appointment.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * This method sets the appointment id number.
     * @param appointmentID The appointment id number
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * This method gets the title of the appointment.
     * @return Returns a string of the title for the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     *This method sets the appointment title.
     * @param title The appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method returns the appointment's description.
     * @return Returns a string of the description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method sets the appointment's description.
     * @param description The description of the appointment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method gets the location of the appointment.
     * @return Returns a string of the location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method set the location of the appointment.
     * @param location The location of the appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method gets the type of the appointment.
     * @return Returns a string of the type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * This method sets the type of appointment.
     * @param type The type of appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method gets the start date and time of the appointment.
     * @return Returns a zoned date time for the start of the appointment.
     */
    public ZonedDateTime getStart() {
        return start;
    }

    /**
     * This method sets the start date time of the appointment
     * @param start The zoned date time of the start of the appointment
     */
    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    /**
     * This method gets the end date time of the appointment.
     * @return Returns a zoned date time for the end of the appointment.
     */
    public ZonedDateTime getEnd() {
        return end;
    }

    /**
     * This method set the end date time of the appointment.
     * @param end The zoned date time for the end of the appointment
     */
    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    /**
     * This method get the customer's id number for the appointment.
     * @return Returns an integer of the customer's id for the appointment.
     */
    public int getCustID() {
        return custID;
    }

    /**
     * This method sets the customer's id number for the appointment.
     * @param custID The customer's id number for the appointment
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * This method gets the user id number for the appointment.
     * @return Returns an integer of the user id number for the appointment.
     */
    public int getUsersID() {
        return usersID;
    }

    /**
     * This method sets the user id number for the appointment.
     * @param usersID The user id number for the appointment
     */
    public void setUsersID(int usersID) {
        this.usersID = usersID;
    }

    /**
     * This method gets the contact's id number for the appointment.
     * @return Returns an integer of the contact's id number.
     */
    public int getContactsID() {
        return contactsID;
    }

    /**
     * This method sets the contact's id number for the appointment.
     * @param contactsID The contact's id number
     */
    public void setContactsID(int contactsID) {
        this.contactsID = contactsID;
    }

    /**
     * This method gets the formatted start time for the appointment.
     * @return Returns a string of the formatted start time.
     */
    public String getFormattedStart() {
        return formattedStart;
    }

    /**
     * This method sets the formatted start time for the appointment.
     * @param formattedStart The formatted start time for the appointment
     */
    public void setFormattedStart(String formattedStart) {
        this.formattedStart = formattedStart;
    }

    /**
     * This method gets the formatted end time for the appointment.
     * @return Returns a string of the formatted end time.
     */
    public String getFormattedEnd() {
        return formattedEnd;
    }

    /**
     * This method sets the formatted end time for the appointment.
     * @param formattedEnd The formatted end time for the appointment
     */
    public void setFormattedEnd(String formattedEnd) {
        this.formattedEnd = formattedEnd;
    }
}
