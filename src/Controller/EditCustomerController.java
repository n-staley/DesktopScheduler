package Controller;

import DAO.CustomersDao;
import DAO.UserDao;
import Model.Customers;
import Utility.InputErrorCheck;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class EditCustomerController extends ViewController implements Initializable {
    private static Customers customerToEdit;
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
    public ComboBox<String> customerFirstDivisionCombo;
    public Label customerCountryLabel;
    public ComboBox<String> customerCountryCombo;
    public Button saveButton;
    public Button cancelButton;
    public AnchorPane mainPane;

    public void cancelEditCustomer(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveEditCustomer(ActionEvent actionEvent) {
        int wasEdited;
        InputErrorCheck errorCheck;
        boolean wasError = false;
        String errorMessage = "";
        String name;
        String address;
        String postalCode;
        String phone;
        Instant update = Instant.now();
        String updateBy = UserDao.getLoggedInUser().getUserName();
        int divisionID;
        int customerID = customerToEdit.getCustomerID();

        errorCheck = stringErrorCheck(50, customerNameText, "name");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        if (customerNameText.getText().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must enter a customer name.\n");
        }
        errorCheck = stringErrorCheck(100, customerAddressText, "address");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        if (customerAddressText.getText().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must enter a customer address.\n");
        }
        errorCheck = stringErrorCheck(50, customerPostalCodeText, "postal code");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        if (customerPostalCodeText.getText().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must enter a postal code.\n");
        }
        errorCheck = stringErrorCheck(50, customerPhoneText, "phone number");
        if (errorCheck.getWasError()) {
            wasError = true;
            errorMessage = errorMessage.concat(errorCheck.getErrorMessage());
        }
        if (customerPhoneText.getText().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must enter a phone number.\n");
        }
        if (customerCountryCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select a Country from the combo box.\n");
        }
        if (customerFirstDivisionCombo.getSelectionModel().isEmpty()) {
            wasError = true;
            errorMessage = errorMessage.concat("Must select a State/Province from the combo box.\n");
        }

        if (wasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Add Customer Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;
        }

        name = customerNameText.getText();
        address = customerAddressText.getText();
        postalCode = customerPostalCodeText.getText();
        phone = customerPhoneText.getText();
        divisionID = CustomersDao.getDivisionID(customerFirstDivisionCombo.getValue());

        wasEdited = CustomersDao.updateCustomer(name, address, postalCode, phone, update, updateBy, divisionID, customerID);
        if (wasEdited > 0) {
            CustomersDao.populateCustomersList();
            try {
                switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers DashBoard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Customers getCustomerToEdit() {
        return customerToEdit;
    }

    public static void setCustomerToEdit(Customers customerToEdit) {
        EditCustomerController.customerToEdit = customerToEdit;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDText.setText(String.valueOf(customerToEdit.getCustomerID()));
        customerNameText.setText(customerToEdit.getCustomerName());
        customerAddressText.setText(customerToEdit.getAddress());
        customerPostalCodeText.setText(customerToEdit.getPostalCode());
        customerPhoneText.setText(customerToEdit.getPhoneNumber());
        customerCountryCombo.setItems(CustomersDao.getCountryNameList());
        customerCountryCombo.setValue(CustomersDao.getCountryName(customerToEdit.getCountryID()));
        if (customerCountryCombo.getValue().equals("U.S")) {
            customerFirstDivisionCombo.setItems(CustomersDao.getDivisionUSAList());
        }
        if (customerCountryCombo.getValue().equals("UK")) {
            customerFirstDivisionCombo.setItems(CustomersDao.getDivisionUKList());
        }
        if (customerCountryCombo.getValue().equals("Canada")) {
            customerFirstDivisionCombo.setItems(CustomersDao.getDivisionCanadaList());
        }
        customerFirstDivisionCombo.setValue(CustomersDao.getDivisionName(customerToEdit.getDivisionID()));
    }

    public void countrySelected(ActionEvent actionEvent) {
        if (customerCountryCombo.getValue().equals("U.S")) {
            customerFirstDivisionCombo.setItems(CustomersDao.getDivisionUSAList());
        }
        if (customerCountryCombo.getValue().equals("UK")) {
            customerFirstDivisionCombo.setItems(CustomersDao.getDivisionUKList());
        }
        if (customerCountryCombo.getValue().equals("Canada")) {
            customerFirstDivisionCombo.setItems(CustomersDao.getDivisionCanadaList());
        }
    }
}
