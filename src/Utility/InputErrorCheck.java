package Utility;

public class InputErrorCheck {
    private boolean wasError;
    private String errorMessage;


    public InputErrorCheck(){
        wasError = false;
        errorMessage = "";
    }
    public InputErrorCheck(boolean wasError, String errorMessage) {
        this.wasError = wasError;
        this.errorMessage = errorMessage;
    }

    public boolean getWasError() {
        return wasError;
    }

    public void setWasError(boolean wasError) {
        this.wasError = wasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void concatErrorMessage(String errorString) {
        errorMessage = errorMessage.concat(errorString);
    }
}
