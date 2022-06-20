package com.fhbielefeld.wholetsthedogoutfrontend.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fhbielefeld.wholetsthedogoutfrontend.login.ui.LoginActivity;

public class UserDataToSP {

    public void saveToSharedPreferences(String username, @NonNull Context context) {
        SharedPreferences sharedPreferences =  context.getSharedPreferences("WLMDO", context.MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putString("username", username);

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();
    }

    public void getSharedPreferences(@NonNull Context context) {

// Retrieving the value using its keys the file name
// must be same in both saving and retrieving the data
        SharedPreferences sh = context.getSharedPreferences("WLMDO", context.MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String s1 = sh.getString("name", "");
        //int a = sh.getInt("age", 0);

// We can then use the data
        //name.setText(s1);
        //age.setText(String.valueOf(a));

    }

}
