package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
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
        if (true) {
            Locale errorLocale = Locale.getDefault();
            ResourceBundle errorRB = ResourceBundle.getBundle("LoginLanguage", errorLocale);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorRB.getString("errorTitle"));
            alert.setHeaderText(errorRB.getString("errorHeader"));
            alert.setContentText(errorRB.getString("error"));
            alert.showAndWait();

        } else {
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

