package Controller;

import DAO.AppointmentDao;
import DAO.CustomersDao;
import Model.Customers;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerDashboardController extends ViewController implements Initializable {
    public TableView<Customers> customersTableView;
    public TableColumn<Customers, Integer> customerIDColumn;
    public TableColumn<Customers, String> nameColumn;
    public TableColumn<Customers, String> phoneNumberColumn;
    public TableColumn<Customers, String> addressColumn;
    public TableColumn<Customers, String> postalCodeColumn;
    public TableColumn<Customers, String> stateOrProvinceColumn;
    public TableColumn<Customers, String> countryColumn;
    public Label customersTableViewLabel;
    public Button addCustomerButton;
    public Button editCustomerButton;
    public Button viewAppointmentsButton;
    public Button viewReportsButton;
    public Button exitButton;
    public Button deleteCustomerButton;
    public AnchorPane mainPane;

    public void toAddCustomer(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AddCustomerForm.fxml", 550, 650, "Add Customer");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toEditCustomer(ActionEvent actionEvent) {
        if (customersTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Customer");
            alert.setContentText("Must select a customer from the table to edit it.");
            alert.showAndWait();
            return;
        }

        EditCustomerController.setCustomerToEdit(customersTableView.getSelectionModel().getSelectedItem());
        try {
            switchScene(actionEvent, "/view/EditCustomerForm.fxml", 550, 650, "Edit Customer");
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

    public void deleteCustomer(ActionEvent actionEvent) {
        int wasDeleted = 0;
        if (customersTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Customer");
            alert.setContentText("Must select a customer from the table to delete it.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the customer and all of their appointments, do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            wasDeleted = CustomersDao.deleteCustomer(customersTableView.getSelectionModel().getSelectedItem().getCustomerID());
        }
        if (wasDeleted > 0) {
            CustomersDao.populateCustomersList();
            AppointmentDao.populateAppointmentLists();
            customersTableView.setItems(CustomersDao.getCustomersList());

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customersTableView.setItems(DAO.CustomersDao.getCustomersList());
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        stateOrProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
    }
}
