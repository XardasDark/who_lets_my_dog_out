package com.fhbielefeld.wholetsthedogoutfrontend.login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@SuppressWarnings("FieldMayBeFinal")
public class LoggedInUser {

    private String displayName;

    public LoggedInUser(String displayName) {
        this.displayName = displayName;
    }
    @SuppressWarnings({"UnusedDeclaration"})

    public String getDisplayName() {
        return displayName;
    }
}