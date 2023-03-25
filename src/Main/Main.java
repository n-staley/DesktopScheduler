package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is the main class that is used to launch the appointment desktop application.
 *
 * @author Nicholas Staley
 */

public class Main extends Application {
    /**
     * Mehtod to start the graphical application and load the login screen.
     * @param primaryStage stage used to run the program
     * @throws Exception can throw IOException if the file is not found or fails to load.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**
     * Method main method that starts the application.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
