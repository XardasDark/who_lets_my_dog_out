package com.fhbielefeld.wholetsthedogoutfrontend.profilscreen;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentProfilBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager.GpsTracker;
import com.fhbielefeld.wholetsthedogoutfrontend.gpsmanager.LocationToAddress;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfilFragment extends Fragment {

    private FragmentProfilBinding binding;

    ImageView ivProfilPicture;
    TextView tvForename, tvSurname, tvHeader, tvEmail, tvAddress, tvDogs, tvBirthday;
    CheckBox cbIsWalker;

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

                getLocation(view);

                tvForename.setText(String.valueOf(firstname));
                tvSurname.setText(String.valueOf(lastname));
                tvEmail.setText(String.valueOf(email));
                tvBirthday.setText(String.valueOf(birthday));
                cbIsWalker.setChecked(isWalker);
                tvHeader.setText("Das Profil von " + String.valueOf(username));
            }

            @Override
            public void onFailure(@NonNull Call<List<GetUsersModel>> call, @NonNull Throwable t) {
                Log.e("API",t.getMessage());
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
                } catch (IOException e) {
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