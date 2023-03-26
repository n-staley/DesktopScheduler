package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public abstract class ViewController {

    public void switchScene(ActionEvent actionEvent, String path, int width, int height, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, width, height);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    public void exitProgram(Pane mainPane) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the program, do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
    }
}
