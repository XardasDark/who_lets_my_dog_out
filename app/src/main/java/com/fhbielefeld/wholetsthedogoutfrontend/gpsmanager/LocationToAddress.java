package com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A class to get the address of the users
 */
public class LocationToAddress {

    Addresses addresseses;
    String locality;
    String thoroughfare;
    String thoroughfare_number;
    String country;
    String country_code;
    String zipcode;


    public LocationToAddress(Context context, double latitude, double longitude) {
        //this.mContext = context;
        getAddress(context, latitude, longitude);
    }



    /**
     * Gets a context, latitude and longitude and reverse geocoding them to a real address
     * Checks if a address was found and
     * @param ctx
     * @param latitude
     * @param longitude
     * @return
     */
    public Addresses getAddress(Context ctx, double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        List<String> resultList = new ArrayList<String>();
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);

                locality = address.getLocality();
                thoroughfare = address.getThoroughfare();
                thoroughfare_number = address.getSubThoroughfare();
                country = address.getCountryName();
                country_code = address.getCountryCode();
                zipcode = address.getPostalCode();
                Log.e("Foo", "Locality in LocationToAddress: " + locality);
                //double lat =address.getLatitude();
                //double lon= address.getLongitude();
                result.append(thoroughfare + " " + thoroughfare_number + "\n" + zipcode + " " + locality + "\n" + country + " " + country_code + " ");
                if (result.toString().trim().length() > 0){
                    resultList.add(locality);
                    resultList.add(thoroughfare);
                    resultList.add(thoroughfare_number);
                    resultList.add(country);
                    resultList.add(country_code);
                    resultList.add(zipcode);
                    Log.d("WLMDO.GPS.ADDRESS", "Location could be determined");


                }
            } else {
                result.append("Standort konnte nicht ermittelt werden!");
                Log.d("WLMDO.GPS.ADDRESS", "Location could not be determined");
            }
        } catch (IOException e) {
            Log.e("WLMDO.GPS.ADDRESS", e.getMessage());
        }
        return addresseses;
    }

    public String getLocality(){
        if(addresseses != null){
            locality = addresseses.getLocality();
        }
        return locality;
    }

}

