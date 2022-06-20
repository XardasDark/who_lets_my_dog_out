package com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A class to get the address of the users
 */
public class LocationToAddress {

    /**
     * Gets a context, latitude and longitude and reverse geocoding them to a real address
     * Checks if a address was found and concatenate the address fragments to a big String
     * @param ctx
     * @param latitude
     * @param longitude
     * @return
     */
    @NonNull
    public static StringBuilder getAddress(Context ctx, double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        List<String> resultList = new ArrayList<String>();
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);

                String locality = address.getLocality();
                String thoroughfare = address.getThoroughfare();
                String thoroughfare_number = address.getSubThoroughfare();
                String country = address.getCountryName();
                String country_code = address.getCountryCode();
                String zipcode = address.getPostalCode();
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
        return result;
    }

}

