package utilities.exceptions;

public class SystemException extends Exception {
    private String message;
    private int errorCode;

    public SystemException(int errorCode, String message) {
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
