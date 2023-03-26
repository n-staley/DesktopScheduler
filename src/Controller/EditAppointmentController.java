package Controller;

import javafx.scene.control.*;

public class EditAppointmentController extends InputCheckController {
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
    public ComboBox appointmentStartMonthCombo;
    public ComboBox appointmentStartDayCombo;
    public ComboBox appointmentStartYearCombo;
    public ComboBox appointmentEndMonthCombo;
    public ComboBox appointmentEndDayCombo;
    public ComboBox appointmentEndYearCombo;
    public TextField appointmentStartTimeText;
    public RadioButton startAMRadio;
    public RadioButton startPMRadio;
    public TextField appointmentEndTimeText;
    public RadioButton endAMRadio;
    public RadioButton endPMRadio;
    public TextField appointmentCustomerIDText;
    public TextField appointmentUserIDText;
    public Button appointmentSaveButton;
    public Button appointmentCancelButton;
    public ToggleGroup startToggleGroup;
    public ToggleGroup endToggleGroup;
}
