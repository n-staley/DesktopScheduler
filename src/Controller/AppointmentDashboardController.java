package Controller;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentDashboardController extends ViewController implements Initializable {
    public TableView<Appointment> appointmentsTableView;
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descriptionColumn;
    public TableColumn<Appointment, String> locationColumn;
    public TableColumn<Appointment, Integer> contactColumn;
    public TableColumn<Appointment, String>typeColumn;
    public TableColumn<Appointment, String> startDateTimeColumn;
    public TableColumn<Appointment, String> endDateTimeColumn;
    public TableColumn<Appointment, Integer> customerIDColumn;
    public TableColumn<Appointment, Integer> userIDColumn;
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
    public RadioButton viewAllRadio;

    public void toAddAppointment(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AddAppointmentForm.fxml", 550, 850, "Add Appointments");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toEditAppointment(ActionEvent actionEvent) {
        if (appointmentsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Appointment");
            alert.setContentText("Must select an appointment from the table to edit it.");
            alert.showAndWait();
            return;
        }

        EditAppointmentController.setAppointmentToEdit(appointmentsTableView.getSelectionModel().getSelectedItem());
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
        int wasDeleted = 0;
        if (appointmentsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Appointment");
            alert.setContentText("Must select an appointment from the table to delete it.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the appointment, do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            wasDeleted = DAO.AppointmentDao.deleteAppointmentID(appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID());
        }
        if (wasDeleted > 0) {
            DAO.AppointmentDao.populateAppointmentLists();
            if (appointmentWeekViewRadio.isSelected()) {
                appointmentsTableView.setItems(DAO.AppointmentDao.getWeekAppointments());
            }
            if (appointmentMonthViewRadio.isSelected()) {
                appointmentsTableView.setItems(DAO.AppointmentDao.getMonthAppointments());
            }
            if (viewAllRadio.isSelected()) {
                appointmentsTableView.setItems(DAO.AppointmentDao.getAllAppointments());
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTableView.setItems(DAO.AppointmentDao.getWeekAppointments());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("custID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("usersID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactsID"));
    }

    public void viewWeekSelected(ActionEvent actionEvent) {
        appointmentsTableView.setItems(DAO.AppointmentDao.getWeekAppointments());
    }

    public void viewMonthSelected(ActionEvent actionEvent) {
        appointmentsTableView.setItems(DAO.AppointmentDao.getMonthAppointments());
    }

    public void viewAllSelected(ActionEvent actionEvent) {
        appointmentsTableView.setItems(DAO.AppointmentDao.getAllAppointments());
    }
}
