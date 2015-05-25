package pt.paulosantos.betfair.aping.dto.rescript;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.paulosantos.betfair.aping.exceptions.APINGException;

/**
 * @author paulosantos.
 */
public class ErrorDetail {
    @JsonProperty("exceptionname")
    private String exceptionName;
    @JsonProperty("APINGException")
    private APINGException APINGException;

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public pt.paulosantos.betfair.aping.exceptions.APINGException getAPINGException() {
        return APINGException;
    }

    public void setAPINGException(pt.paulosantos.betfair.aping.exceptions.APINGException APINGException) {
        this.APINGException = APINGException;
    }
}
