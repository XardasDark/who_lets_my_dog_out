package com.fhbielefeld.wholetsthedogoutfrontend.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUsersByDistanceModel {

    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("username")
    @Expose
    private String username;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}