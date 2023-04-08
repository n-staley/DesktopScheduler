package Controller;

import DAO.AppointmentDao;
import DAO.ContactsDao;
import DAO.CustomersDao;
import DAO.UserDao;
import Model.Appointment;
import Utility.LoginLogger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
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
            LoginLogger.log(username, password, loginDateTime, loginSuccess);


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorRB.getString("errorTitle"));
            alert.setHeaderText(errorRB.getString("errorHeader"));
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;

        }


        username = usernameTextBox.getText();
        password = passwordTextBox.getText();


        if (UserDao.loginQuery(username, password) == 0) {
            LoginLogger.log(username, password, loginDateTime, loginSuccess);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorRB.getString("errorTitle"));
            alert.setHeaderText(errorRB.getString("errorHeader"));
            alert.setContentText(errorRB.getString("error"));
            alert.showAndWait();

        } else {
            boolean successfulLogin = true;
            LoginLogger.log(username, password, loginDateTime, successfulLogin);
            AppointmentDao.populateAppointmentLists();
            CustomersDao.populateAllCustomersLists();
            UserDao.createUserList();
            UserDao.setUser(username);
            ContactsDao.setContactsList();

            try {
                switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            List<Appointment> appointmentsFifteenMin =  AppointmentDao.getAllAppointments().stream().filter(a -> a.getUsersID() == UserDao.getLoggedInUser().getUserID()).filter(a -> a.getStart().isAfter(loginDateTime)).filter(a -> a.getStart().isBefore(loginDateTime.plusMinutes(15))).toList();
            if (appointmentsFifteenMin.size() > 0) {
                String upcomingAppointments = "";
                for (Appointment appointment : appointmentsFifteenMin) {
                    upcomingAppointments = upcomingAppointments.concat(UserDao.getLoggedInUser().getUserName() + " has appointment number: " + appointment.getAppointmentID() + " at " + appointment.getFormattedStart() + ".\n");
                }
                Alert upcomingAppointment = new Alert(Alert.AlertType.INFORMATION);
                upcomingAppointment.setHeaderText("Appointments Next 15 Minutes");
                upcomingAppointment.setContentText(upcomingAppointments);
                upcomingAppointment.showAndWait();

            }
            else {
                Alert upcomingAppointment = new Alert(Alert.AlertType.INFORMATION);
                upcomingAppointment.setHeaderText("Appointments Next 15 Minutes");
                upcomingAppointment.setContentText("You do not have any appointments in the next fifteen minutes.");
                upcomingAppointment.showAndWait();
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
        ZoneId zoneId = ZoneId.systemDefault();
        zoneInformation.setText(zoneId.toString());
        ResourceBundle rb = ResourceBundle.getBundle("LoginLanguage", userLocale);
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        usernameLabel.setText(rb.getString("userName"));
        passwordLabel.setText(rb.getString("password"));
        loginLabel.setText(rb.getString("login"));


    }
}

