
package com.fhbielefeld.wholetsthedogoutfrontend.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllChatsModel {

    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("username")
    @Expose
    private String username;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}