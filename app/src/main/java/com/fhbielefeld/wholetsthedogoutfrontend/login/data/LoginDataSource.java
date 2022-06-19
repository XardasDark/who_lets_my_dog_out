package com.fhbielefeld.wholetsthedogoutfrontend.login.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fhbielefeld.wholetsthedogoutfrontend.MainActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.LoginUserModel;
import com.fhbielefeld.wholetsthedogoutfrontend.login.data.model.LoggedInUser;
import com.fhbielefeld.wholetsthedogoutfrontend.login.ui.LoginActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.sharedpreferences.UserDataToSP;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    @SuppressWarnings({"UnusedDeclaration"})
    public Result<LoggedInUser> login(String username, String password) {

        Retrofit retrofit = APIClient.getClient();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<LoginUserModel> call = apiInterface.loginUser(username, password);

        LoginActivity loginActivity = new LoginActivity();

        try {
            // TODO: Implement Auth Token
            // TODO: Secure Login
            // TODO: More Error Handling

            call.enqueue(new Callback<LoginUserModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginUserModel> call, @NonNull Response<LoginUserModel> response) {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400) {
                            Log.e("API_LOGIN", "400: " + response.message());
                            new Result.Error(new IOException("Error logging in"));
                        }
                        return;
                    }

                    if (response.isSuccessful()) {
                        //LoginUserModel token = response.body();
                        //Log.d("API_LOGIN","Bearer " + token.getToken());

                        LoggedInUser user =
                                new LoggedInUser(
                                        "username");


                        Log.e("API_LOGIN", "Ende Response");
                        return;
                    }
                }


                public void onFailure(@NonNull Call<LoginUserModel> call, @NonNull Throwable t) {
                    Log.e("API_LOGIN.FAILURE",t.getMessage());
                    new Result.Error(new IOException("Error logging in"));
                }
            });



/*            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Max Mustermann");
            return new Result.Success<>(user);*/
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }


        LoggedInUser user =
                new LoggedInUser(username);
        return new Result.Success<>(user);

    }

    public void logout() {
        // TODO: revoke authentication
    }
}