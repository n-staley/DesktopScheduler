package Controller;

import DAO.CustomersDao;
import DAO.UserDao;
import Utility.InputErrorCheck;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

/**
 * This class controls the add customer form, and provides functionality.
 * @author Nicholas Staley
 */
public class AddCustomerController extends ViewController implements Initializable {
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

    /**
     * This method attempts to add the customer in the customer table in the database. All fields are checked
     * to make sure they are filled in, no disallowed characters are used, and strings are the proper length.
     * If the add is successful, the user is returned to the customer dashboard.
     * @param actionEvent Save button clicked
     */
    public void saveCustomer(ActionEvent actionEvent) {
        int wasAdded;
        InputErrorCheck errorCheck;
        boolean wasError = false;
        String errorMessage = "";
        String name;
        String address;
        String postalCode;
        String phone;
        Instant create = Instant.now();
        String createBy = UserDao.getLoggedInUser().getUserName();
        Instant update = create;
        String updateBy = UserDao.getLoggedInUser().getUserName();
        int divisionID;

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

        wasAdded = CustomersDao.addCustomer(name, address, postalCode, phone, create, createBy, update, updateBy, divisionID);
        if (wasAdded > 0) {
            CustomersDao.populateCustomersList();
            try {
                switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers Dashboard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method cancels adding a new customer, and returns the user to the customer dashboard.
     * @param actionEvent Cancel button clicked
     */
    public void cancelAddCustomer(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers Dashboard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method initializes the add customer form populating the countries combo box with a list of available countries.
     * @param url The url
     * @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCountryCombo.setItems(CustomersDao.getCountryNameList());
    }

    /**
     * This method changes the list of first level division options in the state/province combo box based on country selection.
     * @param actionEvent Selection made in the country combo box
     */
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
