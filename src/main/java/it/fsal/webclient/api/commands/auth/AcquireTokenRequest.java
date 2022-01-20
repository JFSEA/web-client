package it.fsal.webclient.api.commands.auth;

import it.fsal.webclient.api.IRequest;

public class AcquireTokenRequest implements IRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
