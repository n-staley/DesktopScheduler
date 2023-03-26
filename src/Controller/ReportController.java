package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ReportController extends ViewController {
    public TabPane reportHolder;
    public Tab reportOneTab;
    public TableView reportOneTableView;
    public TableColumn reportOneAppointmentType;
    public TableColumn reportOneNumberOfAppointments;
    public Tab reportTwoTab;
    public TableView reportTwoTableView;
    public TableColumn r2Contact;
    public TableColumn r2Title;
    public TableColumn r2AppointmentID;
    public TableColumn r2Type;
    public TableColumn r2Description;
    public TableColumn r2StartDateTime;
    public TableColumn r2EndDateTime;
    public TableColumn r2CustomerID;
    public Tab report3Tab;
    public TableView r3TableView;
    public Button exitButton;
    public Button viewCustomersButton;
    public Button viewAppointmentsButton;
    public AnchorPane mainPane;

    public void exit(ActionEvent actionEvent) {
        exitProgram(mainPane);
    }

    public void toViewCustomers(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toViewAppointments(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
