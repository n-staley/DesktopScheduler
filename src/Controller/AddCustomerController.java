package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddCustomerController extends ViewController {
    public Label customerLabel;
    public Label customerIDLabel;
    public Label customerNameLabel;
    public Label customerAddressLabel;
    public Label customerPostalCodeLabel;
    public Label customerPhoneLabel;
    public TextField customerIDText;
    public TextField customerNameText;
    public TextField customerPhoneText;
    public TextField customerAddressText;
    public TextField customerPostalCodeText;
    public Label customerFirstDivisionLabel;
    public ComboBox customerFirstDivisionCombo;
    public Label customerCountryLabel;
    public ComboBox customerCountryCombo;
    public Button saveButton;
    public Button cancelButton;
    public AnchorPane mainPane;

    public void saveCustomer(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelAddCustomer(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
