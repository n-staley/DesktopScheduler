package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController extends ViewController {


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
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exit(ActionEvent actionEvent) {
        exitProgram(mainPane);
        }
    }

