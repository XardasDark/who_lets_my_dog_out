package com.fhbielefeld.wholetsthedogoutfrontend.homescreen;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentHomeBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.profilscreen.ProfilFragment;

import java.io.InputStream;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState){
        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
        String spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);

        TextView text = view.findViewById(R.id.text_home);
        text.setText("Willkommen zur ersten version unserer APP WhoLetsMyDogOut");

        ImageView image = view.findViewById(R.id.homescreen_imageView);
        new DownloadImageTask(image).execute("https://i.imgur.com/z1pIv6S.jpeg");
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