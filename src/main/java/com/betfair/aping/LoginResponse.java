package com.betfair.aping;

/**
 * Created by Paulo. 20-04-2014.
 */
public class LoginResponse {

    private String sessionToken;
    private LoginStatus loginStatus;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }
}
