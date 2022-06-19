package com.fhbielefeld.wholetsthedogoutfrontend.login.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.ActivitySignupBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager.Addresses;
import com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager.GpsTracker;
import com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager.LocationToAddress;
import com.fhbielefeld.wholetsthedogoutfrontend.login.data.Result;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    double latitude;
    double longitude;

    EditText etFirstName, etLastName, etUserName, etEmail, etPassword, etRepeatPassword, etPicture;
    CheckBox cbDogWalker;
    DatePicker dpBirthday;
    TextView tvLocation;
    final int MIN_PASSWORD_LENGTH = 6;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        com.fhbielefeld.wholetsthedogoutfrontend.databinding.ActivitySignupBinding binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         * Create a custom ActionBar with custom title and back button
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.register_header));
            // To show back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewInitializations();
    }

    void viewInitializations() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etUserName = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etRepeatPassword = findViewById(R.id.et_repeat_password);
        dpBirthday = findViewById(R.id.et_birthday);
        etPicture = findViewById(R.id.et_picture);
        cbDogWalker = findViewById(R.id.et_dogWalker);
        tvLocation = findViewById(R.id.tv_location);
    }

    public void getLocation(View view) {
        gpsTracker = new GpsTracker(SignupActivity.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Toast.makeText(view.getContext(),String.valueOf(latitude) + " | " + String.valueOf(longitude),Toast.LENGTH_SHORT).show();
            tvLocation.setText(String.valueOf(latitude) + " | " + String.valueOf(longitude));
            tvLocation.setText(LocationToAddress.getAddress(view.getContext(), latitude, longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }

    }



    // Checking if the input in form is valid
    boolean validateInput() {
        if (etFirstName.getText().toString().equals("")) {
            etFirstName.setError("Bitte gib deinen Vornamen an");
            return false;
        }
        if (etLastName.getText().toString().equals("")) {
            etLastName.setError("Bitte gib deinen Nachnamen an");
            return false;
        }
        if (etUserName.getText().toString().equals("")) {
            etUserName.setError("Bitte gib einen Benutzernamen an");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Bitte gib deine Emailadresse an");
            return false;
        }
        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Bitte gib dein Passwort an");
            return false;
        }
        if (etRepeatPassword.getText().toString().equals("")) {
            etRepeatPassword.setError("Wiederhole dein Passwort");
            return false;
        }


        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Bitte gib eine valide Emailadresse an");
            return false;
        }

        // checking minimum password Length
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Das Passwort muss mindestens " + MIN_PASSWORD_LENGTH + "Zeichen enthalten");
            return false;
        }

        // Checking if repeat password is same
        if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            etRepeatPassword.setError("Passwörter stimmen nicht überein");
            return false;
        }
        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performSignUp (View v) {
        if (validateInput()) {




            // Input is valid, here send data to your server

            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String username = etUserName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String birthday = String.valueOf(dpBirthday.getYear()) + "-" + String.valueOf(dpBirthday.getMonth()) + "-" + String.valueOf(dpBirthday.getDayOfMonth());
            String picture = etPicture.getText().toString();
            Boolean dogWalker = cbDogWalker.isChecked();

            if (picture.equals("") || picture == null) {
                picture = "https://i.imgur.com/cqKSBdW.jpg";
            }

            Retrofit retrofit = APIClient.getClient();

            APIInterface apiInterface = retrofit.create(APIInterface.class);
            Call<GetUsersModel> call = apiInterface.createUser(firstName, lastName, username, password, birthday, email, picture, dogWalker, latitude, longitude);

                call.enqueue(new Callback<GetUsersModel>() {
                    @Override
                    public void onResponse(@NonNull Call<GetUsersModel> call, @NonNull Response<GetUsersModel> response) {
                        if (!response.isSuccessful()) {
                            if (response.code() == 400) {
                                Log.e("API_LOGIN", "400: " + response.message());
                                new Result.Error(new IOException("Error logging in"));
                            }
                        }

                        if (response.isSuccessful()) {
                            Toast.makeText(v.getContext(), "Erfolgreich registriert: ",Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(i);
                        }
                    }


                    public void onFailure(@NonNull Call<GetUsersModel> call, @NonNull Throwable t) {
                        Log.e("API_LOGIN.FAILURE",t.getMessage());
                        new Result.Error(new IOException("Error logging in"));
                    }
                });


        }
    }
}



