package Controller;


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

public class AddAppointmentController extends ViewController implements Initializable {
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
    public Label appointmentEndTimeLabel;
    public Label appointmentCustomerIDLabel;
    public Label appointmentUserIDLabel;
    public TextField appointmentTitleText;
    public TextField appointmentDescriptionText;
    public TextField appointmentLocationText;
    public TextField appointmentTypeText;
    public ComboBox<String> appointmentContactCombo;
    public RadioButton startAMRadio;
    public ToggleGroup startToggleGroup;
    public RadioButton startPMRadio;
    public RadioButton endAMRadio;
    public ToggleGroup endToggleGroup;
    public RadioButton endPMRadio;
    public Button appointmentSaveButton;
    public Button appointmentCancelButton;
    public AnchorPane mainPane;
    public ComboBox<String> startHourCombo;
    public ComboBox<String> startMinCombo;
    public ComboBox<String> endHourCombo;
    public ComboBox<String> endMinCombo;
    public DatePicker startDateInput;
    public ComboBox<String> userIDCombo;
    public ComboBox<String> customerIDCombo;
    public DatePicker endDateInput;
    public Label appointmentEndDateLabel;

    public void saveAppointment(ActionEvent actionEvent) {
        String errorMessage = "";
        boolean wasError = false;
        InputErrorCheck errorCheck;
        int appointmentAdded;
        String title;
        String description;
        String location;
        String type;
        Instant start;
        Instant end;
        Instant create;
        String createBy;
        Instant lastUpdate;
        String lastUpdateBy;
        int customerID;
        int userID;
        int contactID;
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
        if (appointmentTypeText.getText().isEmpty()) {
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

        title = (appointmentTitleText.getText().isEmpty()) ? "" : appointmentTitleText.getText();
        description = (appointmentDescriptionText.getText().isEmpty()) ? "" : appointmentDescriptionText.getText();
        location = (appointmentLocationText.getText().isEmpty()) ? "" : appointmentLocationText.getText();
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
        create = Instant.now();
        createBy = DAO.UserDao.getLoggedInUser().getUserName();
        lastUpdate = create;
        lastUpdateBy = DAO.UserDao.getLoggedInUser().getUserName();
        customerID = DAO.CustomersDao.getCustomerIDNumber(customerIDCombo.getValue());
        userID = DAO.UserDao.getUserID(userIDCombo.getValue());
        contactID = DAO.ContactsDao.getContactIDNumber(appointmentContactCombo.getValue());

        if (endDateTime.isBefore(startDateTime)) {
            wasError = true;
            errorMessage = errorMessage.concat("Start Date and Time must be before End Date and Time.\n");
        }


        if (wasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Appointment Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;
        }

        appointmentAdded = DAO.AppointmentDao.addNewAppointment(title, description, location, type, start, end, create, createBy, lastUpdate, lastUpdateBy, customerID, userID, contactID);

        if (appointmentAdded == 1) {
            DAO.AppointmentDao.populateAppointmentLists();
            try {
                switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Appointment Error");
            alert.setContentText("There was an error adding the new appointment.\nPlease Contact IT Department.");
            alert.showAndWait();
        }
    }

    public void cancelAddAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startHourCombo.setItems(DateTimeAdjustment.hoursCombo);
        startMinCombo.setItems(DateTimeAdjustment.minutesCombo);
        endHourCombo.setItems(DateTimeAdjustment.hoursCombo);
        endMinCombo.setItems(DateTimeAdjustment.minutesCombo);
        appointmentContactCombo.setItems(DAO.ContactsDao.getContactsNameList());
        customerIDCombo.setItems(DAO.CustomersDao.getCustomerIDList());
        userIDCombo.setItems(DAO.UserDao.getUsersNameList());

    }
}
