package Utility;

/**
 * This class is used to check for multiple input errors at the same time and combine the error messages for each error found.
 * @author Nicholas Staley
 */
public class InputErrorCheck {
    /**
     * Holds boolean of whether there was an error or not.
     */
    private boolean wasError;
    /**
     * Holds the error message if there was an error found.
     */
    private String errorMessage;

    /**
     * Default constructor for the input error check class.
     */
    public InputErrorCheck(){
        wasError = false;
        errorMessage = "";
    }

    /**
     * Constructor for the input error check class
     * @param wasError boolean if there was error
     * @param errorMessage error message
     */
    public InputErrorCheck(boolean wasError, String errorMessage) {
        this.wasError = wasError;
        this.errorMessage = errorMessage;
    }

    /**
     * This method gets the boolean for if there was an error.
     * @return Returns a true boolean if there was an error and a false if there wasn't.
     */
    public boolean getWasError() {
        return wasError;
    }

    /**
     * This method sets the was error boolean.
     * @param wasError The boolean if there was or wasn't an error
     */
    public void setWasError(boolean wasError) {
        this.wasError = wasError;
    }

    /**
     * This method gets the error message.
     * @return Returns a string of the error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * This method sets the error message.
     * @param errorMessage The error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * This method adds the new error message onto the current error message.
     * @param errorString The error message to be added onto the current error message
     */
    public void concatErrorMessage(String errorString) {
        errorMessage = errorMessage.concat(errorString);
    }
}

