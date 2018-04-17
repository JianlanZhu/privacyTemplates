package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing the input we receive from the frontend when a request is generated from the form.
 */
public class LoginInput {

    private String username;
    private String password;

    public LoginInput() {
        // Jackson deserialization
    }

    public String getUsername() {
        return username;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}

