package Controller;

import Model.Appointment;
import Utility.DateTimeAdjustment;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    public void saveAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelEditAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setAppointmentToEdit(Appointment appointmentToEdit) {
        EditAppointmentController.appointmentToEdit = appointmentToEdit;
    }

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
        customerIDCombo.setItems(DAO.CustomersDao.getCustomerIDList());
        customerIDCombo.setValue(DAO.CustomersDao.getCustomerName(appointmentToEdit.getCustID()));
        userIDCombo.setItems(DAO.UserDao.getUsersNameList());
        userIDCombo.setValue(DAO.UserDao.getUserName(appointmentToEdit.getUsersID()));
        startDateInput.setValue(appointmentToEdit.getStart().toLocalDate());
        endDateInput.setValue(appointmentToEdit.getEnd().toLocalDate());

    }
}
