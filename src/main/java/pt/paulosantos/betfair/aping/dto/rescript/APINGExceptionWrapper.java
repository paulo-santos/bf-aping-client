package pt.paulosantos.betfair.aping.dto.rescript;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author paulosantos
 */
public class APINGExceptionWrapper {
    @JsonProperty("faultcode")
    private String faultCode;
    @JsonProperty("faultstring")
    private String faultString;
    @JsonProperty("detail")
    private ErrorDetail errorDetail;

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
}
