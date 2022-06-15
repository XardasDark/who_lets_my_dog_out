package com.fhbielefeld.wholetsthedogoutfrontend.login.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etUserName, etEmail, etPassword, etRepeatPassword, etBirthday, etPicture;
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);


        com.fhbielefeld.wholetsthedogoutfrontend.databinding.ActivitySignupBinding binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         * Create a custom ActionBar with custom titke and back button
         */
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
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
        etBirthday = findViewById(R.id.et_birthday);
        etPicture = findViewById(R.id.et_picture);


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
        if (etBirthday.getText().toString().equals("")) {
            etBirthday.setError("Bitte gib deinen Geburstag an");
            return false;
        }
        if (etPicture.getText().toString().equals("")) {
            etPicture.setError("Hyperlink mit png/jpeg Endung");
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
            String repeatPassword = etRepeatPassword.getText().toString();
            String birthday = etBirthday.getText().toString();
            String picture = etPicture.getText().toString();

            Toast.makeText(this,"Erfolgreich registriert",Toast.LENGTH_SHORT).show();
            // Here you can call you API

        }
    }

}