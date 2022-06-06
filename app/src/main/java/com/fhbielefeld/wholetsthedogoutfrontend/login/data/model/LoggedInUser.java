package com.fhbielefeld.wholetsthedogoutfrontend.login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@SuppressWarnings("FieldMayBeFinal")
public class LoggedInUser {

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }
    @SuppressWarnings({"UnusedDeclaration"})
    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}