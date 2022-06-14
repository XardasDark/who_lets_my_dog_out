package com.fhbielefeld.wholetsthedogoutfrontend.searchscreen;

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
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersByDistanceModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentSearchBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private List<GetUsersByDistanceModel> usersList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSearch;
        searchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        Retrofit retrofit = APIClient.getClient();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<List<GetUsersByDistanceModel>> call = apiInterface.getUserRange("ncage","15");
        call.enqueue(new Callback<List<GetUsersByDistanceModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetUsersByDistanceModel>> call, @NonNull Response<List<GetUsersByDistanceModel>> response) {
                Toast toast = null;
                if(!response.isSuccessful()){
                    if(response.code() == 400){
                        Log.e("API",response.message());
                        toast = Toast.makeText(view.getContext(),"Code "+response.code(),Toast.LENGTH_LONG);
                    }
                    toast.show();
                    return;
                }

                usersList = response.body();
                for(GetUsersByDistanceModel user : usersList){
                    toast = Toast.makeText(view.getContext(), user.getUsername(),Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetUsersByDistanceModel>> call, @NonNull Throwable t) {
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