package com.fhbielefeld.wholetsthedogoutfrontend.favoritesscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentFavoritesBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFavorites;
        favoritesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        Retrofit retrofit = APIClient.getClient();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
        String spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);

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
                List<GetUsersModel> users= response.body();
                toast = Toast.makeText(view.getContext(), users.get(0).getFirstname(),Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onFailure(@NonNull Call<List<GetUsersModel>> call, @NonNull Throwable t) {
                Log.e("API",t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}