package com.fhbielefeld.wholetsthedogoutfrontend.api.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUsersModel {

    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("lastname")
    @Expose
    public String lastname;
    @SerializedName("birthdate")
    @Expose
    public String username;
    @SerializedName("username")
    @Expose
    public String password;
    @SerializedName("password")
    @Expose
    public String birthdate;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("picture")
    @Expose
    public String picture;
    @SerializedName("dogWalker")
    @Expose
    public Boolean dogWalker;
    @SerializedName("latitude")
    @Expose
    public Number latitude;
    @SerializedName("longitude")
    @Expose
    public Number longitude;
    @SerializedName("dogs")
    @Expose
    public List<DogModel> dogModels = null;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getDogWalker() {
        return dogWalker;
    }

    public void setDogWalker(Boolean dogWalker) {
        this.dogWalker = dogWalker;
    }

    public Number getLatitude() {
        return latitude;
    }

    public void setLatitude(Number latitude) {
        this.latitude = latitude;
    }

    public Number getLongitude() {
        return longitude;
    }

    public void setLongitude(Number longitude) {
        this.longitude = longitude;
    }

    public List<DogModel> getDogs() {
        return dogModels;
    }

    public void setDogs(List<DogModel> dogModels) {
        this.dogModels = dogModels;
    }

}