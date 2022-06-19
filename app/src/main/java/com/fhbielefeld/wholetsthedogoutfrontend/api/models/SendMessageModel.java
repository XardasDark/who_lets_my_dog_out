package com.fhbielefeld.wholetsthedogoutfrontend.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMessageModel {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("usernameTarget")
    @Expose
    private String usernameTarget;
    @SerializedName("message")
    @Expose
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameTarget() {
        return usernameTarget;
    }

    public void setUsernameTarget(String usernameTarget) {
        this.usernameTarget = usernameTarget;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
