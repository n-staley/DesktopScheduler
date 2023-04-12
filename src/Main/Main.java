package Main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This is the main class that is used to launch the appointment desktop application.
 *
 * @author Nicholas Staley
 * @version 1.0
 */

public class Main extends Application {
    /**
     * Method to start the graphical application and load the login screen.
     * Used a lambda expression for the action event on setOnCloseRequest to be able to consume the event and add a conformation for exiting the program.
     * @param primaryStage stage used to run the program
     * @throws Exception can throw IOException if the file is not found or fails to load.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale userLocale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("LoginLanguage", userLocale);

        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        primaryStage.setTitle(rb.getString("login"));
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the program, do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                primaryStage.close();
            }
        });
        primaryStage.setScene(new Scene(root, 440, 410));
        primaryStage.show();
    }

    /**
     * Method main method that starts the application.
     * JavaDocs located nstaleySW2\JavaDocs\index.html
     * @param args arguments
     */
    public static void main(String[] args) {
        DAO.DatabaseConnection.openConnection();


        launch(args);

        DAO.DatabaseConnection.closeConnection();


    }
}
