package com.fhbielefeld.wholetsthedogoutfrontend.profilscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.DogModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentProfilBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager.GpsTracker;
import com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager.LocationToAddress;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfilFragment extends Fragment {

    private FragmentProfilBinding binding;

    ImageView ivProfilPicture;
    TextView tvForename, tvSurname, tvHeader, tvEmail, tvAddress, tvDogs, tvBirthday;
    CheckBox cbIsWalker;
    AlertDialog adForname, adSurname, adEmail, adAddress, adDogs, adBirthday;
    EditText etForname, etSurname, etEmail, etAddress, etDogs, etBirthday;

    String firstName = "Leer";
    String lastName = "Leer";
    String email = "Leer";
    String birthday = "Leer";
    String picture = "Leer";
    String address = "Leer";
    Boolean dogWalker = false;

    double latitude;
    double longitude;

    private GpsTracker gpsTracker;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfilViewModel searchViewModel =
                new ViewModelProvider(this).get(ProfilViewModel.class);

        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState){
        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
        String spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);

        viewInitializations(view);

        adForname = new AlertDialog.Builder(getContext()).create();
        adSurname = new AlertDialog.Builder(getContext()).create();
        adEmail = new AlertDialog.Builder(getContext()).create();
        adAddress = new AlertDialog.Builder(getContext()).create();
        adDogs = new AlertDialog.Builder(getContext()).create();
        adBirthday = new AlertDialog.Builder(getContext()).create();

        etForname = new EditText(getContext());
        etSurname = new EditText(getContext());
        etEmail = new EditText(getContext());
        etAddress = new EditText(getContext());
        etDogs = new EditText(getContext());
        etBirthday = new EditText(getContext());

        adForname.setTitle("Vorname editieren");
        adForname.setView(etForname);
        adForname.setButton(DialogInterface.BUTTON_POSITIVE,"Speichern", (dialog, which) -> tvForename.setText(etForname.getText()));

        adSurname.setTitle("Nachname editieren");
        adSurname.setView(etSurname);
        adSurname.setButton(DialogInterface.BUTTON_POSITIVE,"Speichern", (dialog, which) -> tvSurname.setText(etSurname.getText()));

        adEmail.setTitle("Email editieren");
        adEmail.setView(etEmail);
        adEmail.setButton(DialogInterface.BUTTON_POSITIVE,"Speichern", (dialog, which) -> tvEmail.setText(etEmail.getText()));

        adAddress.setTitle("Addresse editieren");
        adAddress.setView(etAddress);
        adAddress.setButton(DialogInterface.BUTTON_POSITIVE,"Speichern", (dialog, which) -> tvAddress.setText(etAddress.getText()));

        adDogs.setTitle("Hunde editieren");
        adDogs.setView(etDogs);
        adDogs.setButton(DialogInterface.BUTTON_POSITIVE,"Speichern", (dialog, which) -> tvDogs.setText(etDogs.getText()));

        adBirthday.setTitle("Geburtsdatum editieren");
        adBirthday.setView(etBirthday);
        adBirthday.setButton(DialogInterface.BUTTON_POSITIVE,"Speichern", (dialog, which) -> tvBirthday.setText(etBirthday.getText()));

        tvForename.setOnClickListener(v -> {
            etForname.setText(tvForename.getText());
            adForname.show();
        });

        tvSurname.setOnClickListener(v -> {
            etSurname.setText(tvSurname.getText());
            adSurname.show();
        });

        tvEmail.setOnClickListener(v -> {
            etEmail.setText(tvEmail.getText());
            adEmail.show();
        });

        tvAddress.setOnClickListener(v -> {
            etAddress.setText(tvAddress.getText());
            adAddress.show();
        });

        tvDogs.setOnClickListener(v -> {
            etDogs.setText(tvDogs.getText());
            adDogs.show();
        });

        tvBirthday.setOnClickListener(v -> {
            etBirthday.setText(tvBirthday.getText());
            adBirthday.show();
        });


        Retrofit retrofit = APIClient.getClient();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<List<GetUsersModel>> call = apiInterface.getUser(spUser);
        call.enqueue(new Callback<List<GetUsersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetUsersModel>> call, @NonNull Response<List<GetUsersModel>> response) {
                Toast toast = null;
                if(!response.isSuccessful()){
                    if(response.code() == 400){
                        Log.e("API",response.message());
                        toast = Toast.makeText(view.getContext(),"Code "+response.code(),Toast.LENGTH_LONG);
                    }
                    toast.show();
                    return;
                }
                List<GetUsersModel> users = response.body();
                toast = Toast.makeText(view.getContext(), users.get(0).getFirstname(),Toast.LENGTH_LONG);
                toast.show();
                new DownloadImageTask((ImageView) view.findViewById(R.id.profilAvatar)).execute(users.get(0).getPicture());

                String firstname = users.get(0).getFirstname();
                String lastname = users.get(0).getLastname ();
                String username = spUser;
                String email = users.get(0).getEmail();
                String birthday = users.get(0).getBirthdate();
                Boolean isWalker = users.get(0).getDogWalker();
                latitude = users.get(0).getLatitude();
                longitude =  users.get(0).getLongitude();

                List<DogModel> dogs = users.get(0).getDogs();

                String dogsname="";
                for (DogModel dog: dogs){
                    dogsname = dogsname + dog.getName() + "," ;
                }

                getLocation(view);

                tvForename.setText(String.valueOf(firstname));
                tvSurname.setText(String.valueOf(lastname));
                tvEmail.setText(String.valueOf(email));
                tvBirthday.setText(String.valueOf(birthday));
                cbIsWalker.setChecked(isWalker);
                tvHeader.setText("Das Profil von " + String.valueOf(username));
                tvDogs.setText(dogsname);
            }

            @Override
            public void onFailure(@NonNull Call<List<GetUsersModel>> call, @NonNull Throwable t) {
                Log.e("API",t.getMessage());
            }
        });


        ImageView submitButton = (ImageView) view.findViewById(R.id.profil_edit_image);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //picture = "https://aisvip-a.akamaihd.net/themenarchiv/henry-cavill-bilder/460x0/henry-cavill-t2597.jpg";
                // TODO: dogWalker wird nicht beim PUT übernommen
                dogWalker = cbIsWalker.isChecked();

                if (etForname.getText().toString().equals("") || etForname.getText().toString() == null) {
                    firstName = tvForename.getText().toString();
                } else {
                    firstName = etForname.getText().toString();
                }
                if (etSurname.getText().toString().equals("") || etSurname.getText().toString() == null) {
                    lastName = tvSurname.getText().toString();
                } else {
                    lastName = etSurname.getText().toString();
                }
                if (etEmail.getText().toString().equals("") || etEmail.getText().toString() == null) {
                    email = tvEmail.getText().toString();
                } else {
                    email = etEmail.getText().toString();
                }
                if (etBirthday.getText().toString().equals("") || etBirthday.getText().toString() == null) {
                    birthday = tvBirthday.getText().toString();
                } else {
                    birthday = etBirthday.getText().toString();
                }
                if (etAddress.getText().toString().equals("") || etAddress.getText().toString() == null) {
                    address = tvAddress.getText().toString();
                } else {
                    address = etAddress.getText().toString();
                }

                getAddress(view, address);

                Call<GetUsersModel> call = apiInterface.changeUser(spUser, firstName, lastName, birthday, email, dogWalker, latitude, longitude);
                call.enqueue(new Callback<GetUsersModel>() {
                    @Override
                    public void onResponse(Call<GetUsersModel> call, Response<GetUsersModel> response) {
                        Toast toast = Toast.makeText(view.getContext(),"Änderungen gespeichert!",Toast.LENGTH_LONG);
                        toast.show();
                    }

                    @Override
                    public void onFailure(Call<GetUsersModel> call, Throwable t) {
                        Toast toast = Toast.makeText(view.getContext(),"Änderungen konnten nicht gespeichert werden!",Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

            }
        });

    }

    void viewInitializations(View view) {
        tvForename = view.findViewById(R.id.profilForename);
        tvSurname = view.findViewById(R.id.profilSurname);
        tvHeader = view.findViewById(R.id.profilHeader);
        tvEmail = view.findViewById(R.id.profilEmailResponse);
        ivProfilPicture = view.findViewById(R.id.profilAvatar);
        tvDogs = view.findViewById(R.id.profilDogsResponse);
        cbIsWalker = view.findViewById(R.id.profilIsDogwalkerResponse);
        tvAddress = view.findViewById(R.id.profilAddressResponse);
        tvBirthday = view.findViewById(R.id.profilBirthdayResponse);
    }

    public void getLocation(View view) {
        gpsTracker = new GpsTracker(view.getContext());
        if(gpsTracker.canGetLocation()){
            tvAddress.setText(LocationToAddress.getAddress(view.getContext(), latitude, longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    public void getAddress(View view, String address) {
        gpsTracker = new GpsTracker(view.getContext());
        String foo = gpsTracker.getLocationFromAddress(view.getContext(), address).toString();
        Pattern pattern = Pattern.compile("[0-9].+[0-9]");
        Matcher matcher = pattern.matcher(foo);

        if (matcher.find( )) {
            String[] parts = matcher.group(0).split(",");
            String part1 = parts[0];
            String part2 = parts[1];
            latitude = Double.parseDouble(part1);
            longitude = Double.parseDouble(part2);
        }else {
            Log.e("Address", "No Match");
        }

        Log.e("Address", foo);
    }

    public void toSettings(FragmentActivity activity, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( fragment, null );
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    //Load image
    // TODO: Error Handling - Bild kann nicht geladen werden / Leer / What ever
    // TODO: Bild tmp Speichern
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            InputStream in = null;
            try {
                in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}