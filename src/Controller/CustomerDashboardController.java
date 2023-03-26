package Controller;

import Model.Customers;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomerDashboardController {
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
}
