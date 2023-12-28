package utilities.exceptions;

public class ApplicationException extends Exception {
    private String message;
    private int errorCode;

    public ApplicationException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }
}
