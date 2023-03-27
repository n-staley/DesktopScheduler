package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Locale;
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
        Boolean loginSuccess = false;
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
            Boolean successfulLogin = true;
            Utility.LoginLogger.log(username, password, loginDateTime, successfulLogin);
            try {
                switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void exit(ActionEvent actionEvent) {
        exitProgram(mainPane);
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

