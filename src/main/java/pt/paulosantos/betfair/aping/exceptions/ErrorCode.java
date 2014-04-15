package pt.paulosantos.betfair.aping.exceptions;

/**
 * @author Paulo
 */
public enum ErrorCode {
    /**
     * The operation requested too much data
     */
    TOO_MUCH_DATA,
    /**
     * Invalid input data
     */
    INVALID_INPUT_DATA,
    /**
     * The session token passed is invalid or expired
     */
    INVALID_SESSION_INFORMATION,
    /**
     * An application key is required for this operation
     */
    NO_APP_KEY,
    /**
     * A session token is required for this operation
     */
    NO_SESSION,
    /**
     * An unexpected internal error occurred that prevented successful request processing.
     */
    UNEXPECTED_ERROR,
    /**
     * The application key passed is invalid
     */
    INVALID_APP_KEY,
    /**
     * There are too many pending requests
     */
    TOO_MANY_REQUESTS,
    /**
     * The service is currently too busy to service this request
     */
    SERVICE_BUSY,
    /**
     * Internal call to downstream service timed out
     */
    TIMEOUT_ERROR
}
