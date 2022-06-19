package com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager;

/**
 * A data class representing a user address.
 */
public class Addresses {
    private String locality = "";
    private String thoroughfare = "";
    private String thoroughfare_number = "";
    private String country = "";
    private String country_code = "";
    private String zipcode = "";

    public Addresses(String locality, String thoroughfare, String thoroughfare_number, String country, String country_code, String zipcode) {
        this.locality = locality;
        this.thoroughfare = thoroughfare;
        this.thoroughfare_number = thoroughfare_number;
        this.country = country;
        this.country_code = country_code;
        this.zipcode = zipcode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getThoroughfare() {
        return thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        this.thoroughfare = thoroughfare;
    }

    public String getThoroughfare_number() {
        return thoroughfare_number;
    }

    public void setThoroughfare_number(String thoroughfare_number) {
        this.thoroughfare_number = thoroughfare_number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
