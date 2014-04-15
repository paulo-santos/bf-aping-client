package pt.paulosantos.betfair.aping.exceptions;


/**
 * @author Paulo
 */
public class APINGException extends Exception {

    private String errorDetails;
    private ErrorCode errorCode;
    private String requestUUID;

    public APINGException() {
        super();
    }

    public APINGException(String errorDetails, ErrorCode errorCode, String requestUUID) {
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.requestUUID = requestUUID;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getRequestUUID() {
        return requestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        this.requestUUID = requestUUID;
    }

    @Override
    public String toString() {
        return "ErrorCode: " + errorCode + " ErrorDetails: " + errorDetails + " RequestUUID: " + requestUUID;
    }

}
