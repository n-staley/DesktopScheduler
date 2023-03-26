package Controller;

import javafx.scene.control.*;

public class AppointmentDashboardController {
    public TableView appointmentsTableView;
    public TableColumn appointmentIDColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startDateTimeColumn;
    public TableColumn endDateTimeColumn;
    public TableColumn customerIDColumn;
    public TableColumn userIDColumn;
    public Label appointmentsLabel;
    public Button addAppointmentButton;
    public Button editAppointmentButton;
    public Button deleteAppointmentButton;
    public Button viewCustomersButton;
    public Button viewReportsButton;
    public Button exitButton;
    public RadioButton appointmentWeekViewRadio;
    public ToggleGroup appointmentViewToggle;
    public RadioButton appointmentMonthViewRadio;
}
