package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AppointmentDashboardController extends ViewController{
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
    public AnchorPane mainPane;

    public void toAddAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AddAppointmentForm.fxml", 550, 850, "Add Appointments");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toEditAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/EditAppointmentForm.fxml", 550, 850, "Edit Appointments");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toViewCustomers(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toViewReports(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/ReportsForm.fxml", 1200, 600, "Reports DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exit(ActionEvent actionEvent) {
        exitProgram(mainPane);
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }
}
