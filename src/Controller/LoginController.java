package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController extends ViewController implements Initializable {


    public Label usernameLabel;
    public Label passwordLabel;
    public PasswordField passwordTextBox;
    public TextField usernameTextBox;
    public Label loginLabel;
    public Label zoneInformation;
    public Button loginButton;
    public Button exitButton;
    public AnchorPane mainPane;

    public void login (ActionEvent actionEvent) {
        Locale errorLocale = Locale.getDefault();
        ResourceBundle errorRB = ResourceBundle.getBundle("LoginLanguage", errorLocale);
        ZonedDateTime loginDateTime = ZonedDateTime.now();
        boolean loginSuccess = false;
        String username;
        String password;
        boolean errorCheck = false;
        String errorMessage = "";


        if (usernameTextBox.getText().isEmpty()) {
            errorCheck = true;
            errorMessage = errorMessage.concat(errorRB.getString("missingUserName"));
        }
        if (passwordTextBox.getText().isEmpty()) {
            errorCheck = true;
            errorMessage = errorMessage.concat(errorRB.getString("missingPassword"));
        }
        username = usernameTextBox.getText();
        password = passwordTextBox.getText();
        for (int i = 0; i < username.length(); i++){
            if (username.charAt(i) == '\'') {
                errorCheck = true;
                errorMessage = errorMessage.concat(errorRB.getString("usernameInvalidChar"));
                break;
            }
            if (username.charAt(i) == '\"') {
                errorCheck = true;
                errorMessage = errorMessage.concat(errorRB.getString("usernameInvalidChar"));
                break;
            }
            if (username.charAt(i) == '*') {
                errorCheck = true;
                errorMessage = errorMessage.concat(errorRB.getString("usernameInvalidChar"));
                break;
            }
        }
        for (int i = 0; i < password.length(); i++){
            if (password.charAt(i) == '\'') {
                errorCheck = true;
                errorMessage = errorMessage.concat(errorRB.getString("passwordInvalidChar"));
                break;
            }
            if (password.charAt(i) == '\"') {
                errorCheck = true;
                errorMessage = errorMessage.concat(errorRB.getString("passwordInvalidChar"));
                break;
            }
            if (password.charAt(i) == '*') {
                errorCheck = true;
                errorMessage = errorMessage.concat(errorRB.getString("passwordInvalidChar"));
                break;
            }
        }
        if (errorCheck) {
            username = usernameTextBox.getText();
            password = passwordTextBox.getText();
            Utility.LoginLogger.log(username, password, loginDateTime, loginSuccess);


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorRB.getString("errorTitle"));
            alert.setHeaderText(errorRB.getString("errorHeader"));
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;

        }


        username = usernameTextBox.getText();
        password = passwordTextBox.getText();


        if (DAO.UserDao.loginQuery(username, password) == 0) {
            Utility.LoginLogger.log(username, password, loginDateTime, loginSuccess);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorRB.getString("errorTitle"));
            alert.setHeaderText(errorRB.getString("errorHeader"));
            alert.setContentText(errorRB.getString("error"));
            alert.showAndWait();

        } else {
            boolean successfulLogin = true;
            Utility.LoginLogger.log(username, password, loginDateTime, successfulLogin);
            ZonedDateTime currentTime = ZonedDateTime.now();
            DAO.AppointmentDao.populateAppointmentLists(currentTime);
            DAO.CustomersDao.populateCustomersList();
            try {
                switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void exit(ActionEvent actionEvent) {
        Locale userLocale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("LoginLanguage", userLocale);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("exitConfirmation"));
        alert.setTitle(rb.getString("confirmation"));
        alert.setHeaderText(rb.getString("confirmation"));
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }

        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale userLocale = Locale.getDefault();
        zoneInformation.setText(userLocale.getDisplayCountry());
        ResourceBundle rb = ResourceBundle.getBundle("LoginLanguage", userLocale);
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        usernameLabel.setText(rb.getString("userName"));
        passwordLabel.setText(rb.getString("password"));
        loginLabel.setText(rb.getString("login"));


    }
}

