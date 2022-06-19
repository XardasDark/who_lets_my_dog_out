package com.fhbielefeld.wholetsthedogoutfrontend.searchscreen;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fhbielefeld.wholetsthedogoutfrontend.MainActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.MessagesModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentMessagesDetailBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentSearchUserProfilBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentSearchUserProfilLayoutBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen.MessageDetailRecylerAdapter;
import com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen.MessagesViewModel;
import com.fhbielefeld.wholetsthedogoutfrontend.profilscreen.ProfilFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchUserFragment extends Fragment {

    private FragmentSearchUserProfilLayoutBinding binding;
    private List<MessagesModel> messagesList;

    TextView tvForename, tvSurname, tvEmail, tvDogs, tvBirthday;
    ImageView ivAvatar;
    CheckBox cbIsWalker;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void viewInitializations(View view) {
        ivAvatar = view.findViewById(R.id.searchUserAvatar);
        tvForename = view.findViewById(R.id.searchForename);
        tvSurname = view.findViewById(R.id.searchSurname);
        tvEmail= view.findViewById(R.id.searchEmailResponse);
        tvDogs = view.findViewById(R.id.SearchDogsResponse);
        tvBirthday = view.findViewById(R.id.searchBirthdayResponse);
        cbIsWalker = view.findViewById(R.id.profilIsDogwalkerResponse);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel searchViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        binding = FragmentSearchUserProfilLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO", Context.MODE_PRIVATE);
        String spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);
        Log.e("Search", "onViewCreated");

        viewInitializations(view);

        Retrofit retrofit = APIClient.getClient();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<List<GetUsersModel>> call = apiInterface.getUser(MainActivity.targetUser);
        call.enqueue(new Callback<List<GetUsersModel>>() {

            @Override
            public void onResponse(Call<List<GetUsersModel>> call, Response<List<GetUsersModel>> response) {
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

                new DownloadImageTask((ImageView) view.findViewById(R.id.searchUserAvatar)).execute(users.get(0).getPicture());

                String firstname = users.get(0).getFirstname();
                String lastname = users.get(0).getLastname ();
                String email = users.get(0).getEmail();
                //List dogs = users.get(0).getDogs();
                String birthday = users.get(0).getBirthdate();
                Boolean isWalker = users.get(0).getDogWalker();


                tvForename.setText(String.valueOf(firstname));
                tvSurname.setText(String.valueOf(lastname));
                tvEmail.setText(String.valueOf(email));
                tvBirthday.setText(String.valueOf(birthday));
                cbIsWalker.setChecked(isWalker);
            }

            @Override
            public void onFailure(Call<List<GetUsersModel>> call, Throwable t) {
                Log.e("API",t.getMessage());
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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
