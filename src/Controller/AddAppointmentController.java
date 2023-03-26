package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddAppointmentController extends ViewController {
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
    public ComboBox appointmentContactCombo;
    public RadioButton startAMRadio;
    public ToggleGroup startToggleGroup;
    public RadioButton startPMRadio;
    public RadioButton endAMRadio;
    public ToggleGroup endToggleGroup;
    public RadioButton endPMRadio;
    public TextField appointmentCustomerIDText;
    public TextField appointmentUserIDText;
    public Button appointmentSaveButton;
    public Button appointmentCancelButton;
    public AnchorPane mainPane;
    public ComboBox startHourCombo;
    public ComboBox startMinCombo;
    public ComboBox endHourCombo;
    public ComboBox endMinCombo;
    public DatePicker startDate;
    public DatePicker endDate;

    public void saveAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
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
}
