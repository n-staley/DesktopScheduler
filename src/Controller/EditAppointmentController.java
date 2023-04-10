package Controller;

import DAO.AppointmentDao;
import Model.Appointment;
import Utility.DateTimeAdjustment;
import Utility.InputErrorCheck;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * This class controls the edit appointments scene, and provides functionality.
 * @author Nicholas Staley
 */
public class EditAppointmentController extends ViewController implements Initializable {
    private static Appointment appointmentToEdit = null;
    public Label appointmentLabel;
    public Label appointmentIDLabel;
    public TextField appointmentIDText;
    public Label appointmentTitleLabel;
    public Label appointmentDescriptionLabel;
    public Label appointmentLocationLabel;
    public Label appointmentContactLabel;
    public Label appointmentTypeLabel;
    public Label appointmentStartDateLabel;
    public Label appointmentStartTimeLabel;
    public Label appointmentEndDateLabel;
    public Label appointmentEndTimeLabel;
    public Label appointmentCustomerIDLabel;
    public Label appointmentUserIDLabel;
    public TextField appointmentTitleText;
    public TextField appointmentDescriptionText;
    public TextField appointmentLocationText;
    public TextField appointmentTypeText;
    public ComboBox<String> appointmentContactCombo;
    public RadioButton startAMRadio;
    public RadioButton startPMRadio;
    public RadioButton endAMRadio;
    public RadioButton endPMRadio;
    public Button appointmentSaveButton;
    public Button appointmentCancelButton;
    public ToggleGroup startToggleGroup;
    public ToggleGroup endToggleGroup;
    public AnchorPane mainPane;
    public ComboBox<String> startHourCombo;
    public ComboBox<String> startMinCombo;
    public ComboBox<String> endHourCombo;
    public ComboBox<String> endMinCombo;
    public DatePicker startDateInput;
    public DatePicker endDateInput;
    public ComboBox<String> customerIDCombo;
    public ComboBox<String> userIDCombo;

    /**
     * This method is used to save an edited appointment to the database. Checks are done to make sure all fields
     * are properly filled in. Also, checks for appointment overlap and business hours. If the appointment is successfully
     * updated, the user is returned to the appointment dashboard.
     * @param actionEvent Save button clicked
     */
    public void saveAppointment(ActionEvent actionEvent) {
        String errorMessage = "";
        boolean wasError = false;
        InputErrorCheck errorCheck;
        int appointmentUpdated;
        String title;
        String description;
        String location;
        String type;
        Instant start;
        Instant end;
        Instant lastUpdate;
        String lastUpdateBy;
        int customerID;
        int userID;
        int contactID;
        int appointmentID = appointmentToEdit.getAppointmentID();
        ZonedDateTime startDateTime;
        ZonedDateTime endDateTime;
        String startDateTimeString;
        String endDateTimeString;
        String startAmPm;
        String endAmPm;


        errorCheck = stringErrorCheck(50, appointmentTitleText, "Title");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        errorCheck = stringErrorCheck(50, appointmentDescriptionText, "Description");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        errorCheck = stringErrorCheck(50, appointmentLocationText, "Location");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        errorCheck = stringErrorCheck(50, appointmentTypeText, "Type");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        if (appointmentContactCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select a Contact ID from the combo box.\n");
        }
        if (startDateInput.getValue() == null) {
            wasError = true;
            errorMessage =  errorMessage.concat("Must select a start date from the date picker.\n");
        }
        if (endDateInput.getValue() == null) {
            wasError = true;
            errorMessage =  errorMessage.concat("Must select a end date from the date picker.\n");
        }
        if (startHourCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select a start hour from the combo box.\n");
        }
        if (startMinCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select a start minute from the combo box.\n");
        }
        if (endHourCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select an end hour from the combo box.\n");
        }
        if (endMinCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select an end minute from the combo box.\n");
        }
        if (customerIDCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select a Customer ID from the combo box.\n");
        }
        if (userIDCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select a User ID from the combo box.\n");
        }
        if (appointmentTypeText.getText().isEmpty() || appointmentTypeText.getText().equals("null")) {
            wasError = true;
            errorMessage = errorMessage.concat("Must enter an appointment type.\n");
        }

        if (wasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Appointment Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;
        }

        title = (appointmentTitleText.getText().isEmpty() || appointmentTitleText.getText().equals("null")) ? "" : appointmentTitleText.getText();
        description = (appointmentDescriptionText.getText().isEmpty() || appointmentDescriptionText.getText().equals("null")) ? "" : appointmentDescriptionText.getText();
        location = (appointmentLocationText.getText().isEmpty() || appointmentLocationText.getText().equals("null")) ? "" : appointmentLocationText.getText();
        type = appointmentTypeText.getText();


        if(startAMRadio.isSelected()) {
            startAmPm = "AM";
        }
        else {
            startAmPm = "PM";
        }
        if(endAMRadio.isSelected()) {
            endAmPm = "AM";
        }
        else {
            endAmPm = "PM";
        }
        startDateTimeString = startDateInput.getValue().toString() + " " + startHourCombo.getValue() + ":" + startMinCombo.getValue() + " " + startAmPm;
        endDateTimeString = endDateInput.getValue().toString() + " " + endHourCombo.getValue() + ":" + endMinCombo.getValue() + " " + endAmPm;
        startDateTime = Utility.DateTimeAdjustment.convertStringToZoned(startDateTimeString);
        endDateTime = Utility.DateTimeAdjustment.convertStringToZoned(endDateTimeString);
        start = startDateTime.toInstant();
        end = endDateTime.toInstant();
        lastUpdate = Instant.now();
        lastUpdateBy = DAO.UserDao.getLoggedInUser().getUserName();
        customerID = DAO.CustomersDao.getCustomerIDNumber(customerIDCombo.getValue());
        userID = DAO.UserDao.getUserID(userIDCombo.getValue());
        contactID = DAO.ContactsDao.getContactIDNumber(appointmentContactCombo.getValue());


        if (endDateTime.isBefore(startDateTime)) {
            wasError = true;
            errorMessage = errorMessage.concat("Start Date and Time must be before End Date and Time.\n");
        }
        errorCheck = Utility.DateTimeAdjustment.checkBusinessHours(startDateTime, endDateTime);
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        for (Appointment appointment : AppointmentDao.getAllAppointments()) {
            if (appointmentToEdit.getAppointmentID() != appointment.getAppointmentID()) {
                if (startDateTime.equals(appointment.getStart()) || endDateTime.equals(appointment.getEnd()) ||
                        (startDateTime.isAfter(appointment.getStart()) && startDateTime.isBefore(appointment.getEnd())) ||
                        (endDateTime.isAfter(appointment.getStart()) && endDateTime.isBefore(appointment.getEnd())) ||
                        (appointment.getStart().isAfter(startDateTime) && appointment.getStart().isBefore(endDateTime)) ||
                        (appointment.getEnd().isAfter(startDateTime) && appointment.getEnd().isBefore(endDateTime))) {
                    wasError = true;
                    errorMessage = errorMessage.concat("This appointment overlaps with another appointment please reschedule.\n");
                    break;
                }
            }
        }

        if (wasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Appointment Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;
        }

        appointmentUpdated = DAO.AppointmentDao.editAppointment(title, description, location, type, start, end, lastUpdate, lastUpdateBy, customerID, userID, contactID, appointmentID);

        if (appointmentUpdated > 0) {
            DAO.AppointmentDao.populateAppointmentLists();
            try {
                switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment Dashboard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (title.equals(appointmentToEdit.getTitle()) && description.equals(appointmentToEdit.getDescription()) && type.equals(appointmentToEdit.getType())
                    && location.equals(appointmentToEdit.getLocation()) && start.equals(appointmentToEdit.getStart().toInstant()) && end.equals(appointmentToEdit.getEnd().toInstant())
                    && customerID == appointmentToEdit.getCustID() && userID == appointmentToEdit.getUsersID() && contactID == appointmentToEdit.getContactsID()) {
                Alert alertWasUpdated = new Alert(Alert.AlertType.INFORMATION);
                alertWasUpdated.setHeaderText("Appointment Updated");
                alertWasUpdated.setContentText("Appointment updated with no changes.");
                alertWasUpdated.showAndWait();
            }
            else {
                Alert alertWasUpdated = new Alert(Alert.AlertType.INFORMATION);
                alertWasUpdated.setHeaderText("Appointment Updated");
                alertWasUpdated.setContentText("The appointment was updated.");
                alertWasUpdated.showAndWait();
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Appointment Error");
            alert.setContentText("There was an error adding the new appointment.\nPlease Contact IT Department.");
            alert.showAndWait();
        }
    }

    /**
     * This method cancels updating an appointment and returns the user to the appointment dashboard.
     * @param actionEvent Cancel button clicked
     */
    public void cancelEditAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment Dashboard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method sets the appointment that the user wants to edit.
     * @param appointmentToEdit The appointment to be edited
     */
    public static void setAppointmentToEdit(Appointment appointmentToEdit) {
        EditAppointmentController.appointmentToEdit = appointmentToEdit;
    }

    /**
     * This method initializes the edit appointment scene populating the text fields, date pickers, radio buttons , and combo boxes with
     * the details of the appointment that is being edited.
     * @param url The url
     * @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDText.setText(String.valueOf(appointmentToEdit.getAppointmentID()));
        appointmentTitleText.setText(appointmentToEdit.getTitle());
        appointmentDescriptionText.setText(appointmentToEdit.getDescription());
        appointmentLocationText.setText(appointmentToEdit.getLocation());
        appointmentTypeText.setText(appointmentToEdit.getType());
        startHourCombo.setItems(DateTimeAdjustment.hoursCombo);
        startHourCombo.setValue(Utility.DateTimeAdjustment.getHour(appointmentToEdit.getStart()));
        startMinCombo.setItems(DateTimeAdjustment.minutesCombo);
        if (Utility.DateTimeAdjustment.amCheck(appointmentToEdit.getStart())) {
            startAMRadio.setSelected(true);
        }
        else {
            startPMRadio.setSelected(true);
        }
        startMinCombo.setValue(Utility.DateTimeAdjustment.getMinute(appointmentToEdit.getStart()));
        endHourCombo.setItems(DateTimeAdjustment.hoursCombo);
        endHourCombo.setValue(Utility.DateTimeAdjustment.getHour(appointmentToEdit.getEnd()));
        endMinCombo.setItems(DateTimeAdjustment.minutesCombo);
        endMinCombo.setValue(Utility.DateTimeAdjustment.getMinute(appointmentToEdit.getEnd()));
        if (Utility.DateTimeAdjustment.amCheck(appointmentToEdit.getEnd())) {
            endAMRadio.setSelected(true);
        }
        else {
            endPMRadio.setSelected(true);
        }
        appointmentContactCombo.setItems(DAO.ContactsDao.getContactsNameList());
        appointmentContactCombo.setValue(DAO.ContactsDao.getContactName(appointmentToEdit.getContactsID()));
        customerIDCombo.setItems(DAO.CustomersDao.getCustomerNameList());
        customerIDCombo.setValue(DAO.CustomersDao.getCustomerName(appointmentToEdit.getCustID()));
        userIDCombo.setItems(DAO.UserDao.getUsersNameList());
        userIDCombo.setValue(DAO.UserDao.getUserName(appointmentToEdit.getUsersID()));
        startDateInput.setValue(appointmentToEdit.getStart().toLocalDate());
        endDateInput.setValue(appointmentToEdit.getEnd().toLocalDate());

    }
}
