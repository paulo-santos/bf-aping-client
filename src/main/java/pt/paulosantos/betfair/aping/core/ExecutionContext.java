package pt.paulosantos.betfair.aping.core;

/**
 * @author Paulo
 */
public class ExecutionContext {
    private String applicationKey;
    private String sessionToken;

    public ExecutionContext(String applicationKey, String sessionToken){
        this.applicationKey = applicationKey;
        this.sessionToken = sessionToken;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
