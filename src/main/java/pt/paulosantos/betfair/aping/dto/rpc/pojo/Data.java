package pt.paulosantos.betfair.aping.dto.rpc.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.paulosantos.betfair.aping.exceptions.APINGException;

/**
 * Created by Paulo. 19-04-2014.
 */
public class Data {

    @JsonProperty("exceptionname")
    private String exceptionName;

    @JsonProperty("APINGException")
    private APINGException APINGException;

    public APINGException getAPINGException() {
        return APINGException;
    }

    public void setAPINGException(APINGException aPINGException) {
        APINGException = aPINGException;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }
}
