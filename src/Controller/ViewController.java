package Controller;

import Utility.InputErrorCheck;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * This class provides some common controller methods to the other controller classes.
 * @author Nichols Staley
 */
public abstract class ViewController {

    /**
     * This method switches scenes between the current one and the target scene.
     * @param actionEvent The action event that triggered the scene switch
     * @param path The path to the target scene
     * @param width The preferred width for target scene
     * @param height The preferred height for the target scene
     * @param stageTitle The title for the target scene
     * @throws IOException Can throw an IO exception if the file for the scene is not found.
     */
    public void switchScene(ActionEvent actionEvent, String path, int width, int height, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, width, height);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to exit the program, and to verify that the user want to exit before doing so.
     * @param mainPane The pane from the scene
     */
    public void exitProgram(Pane mainPane) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the program, do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * This method checks a user input string for a maximum length, or the use of disallowed characters.
     * @param maxLength The max length of the string
     * @param textFieldCheck The text field to be checked
     * @param inputField The user input the string is for
     * @return Returns an InputErrorCheck object with a boolean for if there was an error, and an error message if there was
     * an error.
     */
    public InputErrorCheck stringErrorCheck(int maxLength, TextField textFieldCheck, String inputField) {
        InputErrorCheck inputErrorCheck = new InputErrorCheck();

        String userInput = textFieldCheck.getText();
        if (userInput.length() > maxLength) {
            inputErrorCheck.setWasError(true);
            inputErrorCheck.concatErrorMessage("Max length for " + inputField +" string is: " + maxLength + ".\n");
        }
        for (int i = 0; i < userInput.length(); i++){
            if (userInput.charAt(i) == '\'') {
                inputErrorCheck.setWasError(true);
                inputErrorCheck.concatErrorMessage(inputField + " input can not include ', \", or *.\n");
                break;
            }
            if (userInput.charAt(i) == '\"') {
                inputErrorCheck.setWasError(true);
                inputErrorCheck.concatErrorMessage(inputField + " input can not include ', \", or *.\n");
                break;
            }
            if (userInput.charAt(i) == '*') {
                inputErrorCheck.setWasError(true);
                inputErrorCheck.concatErrorMessage(inputField + " input can not include ', \", or *.\n");
                break;
            }
        }
        return inputErrorCheck;
    }
}
