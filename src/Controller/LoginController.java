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

/**
 * This class controls the login form, giving it its functionality.
 * @author Nicholas Staley
 */
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

    /**
     * This method attempts to log in the user based off of the username and password entered. Also, checks to make sure
     * a username and password were entered with no disallowed characters. Then, logs in every login attempt noting
     * if the attempt was successful or not. Finally, if login was successful it sends user to the appointments' dashboard
     * and checks if the user has any appointments in the next fifteen minutes.
     * @param actionEvent Login button clicked
     */
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
                switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment Dashboard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            List<Appointment> appointmentsFifteenMin =  AppointmentDao.getAllAppointments().stream().filter(a -> a.getUsersID() == UserDao.getLoggedInUser().getUserID()).filter(a -> a.getStart().isAfter(loginDateTime)).filter(a -> a.getStart().isBefore(loginDateTime.plusMinutes(15))).toList();
            if (appointmentsFifteenMin.size() > 0) {
                String upcomingAppointments = "";
                for (Appointment appointment : appointmentsFifteenMin) {
                    upcomingAppointments = upcomingAppointments.concat(UserDao.getLoggedInUser().getUserName() + errorRB.getString("fifteenPartOne") + appointment.getAppointmentID() + errorRB.getString("fifteenPartTwo") + appointment.getFormattedStart() + ".\n");
                }
                Alert upcomingAppointment = new Alert(Alert.AlertType.INFORMATION);
                upcomingAppointment.setTitle(errorRB.getString("fifteenTitle"));
                upcomingAppointment.setHeaderText(errorRB.getString("fifteenHeader"));
                upcomingAppointment.setContentText(upcomingAppointments);
                upcomingAppointment.showAndWait();

            }
            else {
                Alert upcomingAppointment = new Alert(Alert.AlertType.INFORMATION);
                upcomingAppointment.setTitle(errorRB.getString("fifteenTitle"));
                upcomingAppointment.setHeaderText(errorRB.getString("fifteenHeader"));
                upcomingAppointment.setContentText(errorRB.getString("noFifteen"));
                upcomingAppointment.showAndWait();
            }
        }
    }

    /**
     * This method is used to exit the program.
     * @param actionEvent Exit button clicked
     */
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

    /**
     * This method is used to initialize the login screen. It sets the zone id to the system default zone id of the user,
     * and sets all text to either English or French based off of localization setting of the users' system.
      * @param url The url
     * @param resourceBundle The resource bundle
     */
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

