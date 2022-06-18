package com.fhbielefeld.wholetsthedogoutfrontend.searchscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersByDistanceModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
        String spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView_search);
        ArrayList<String> images = new ArrayList<>(), names = new ArrayList<>(), range = new ArrayList<>();
        RecylerViewAdapter myAdapter = new RecylerViewAdapter(view.getContext(),images,names,range);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Spinner spinner = view.findViewById(R.id.spinner1);
        String[] items = new String[]{"5","10","15"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Call<List<GetUsersByDistanceModel>> call = apiInterface.getUserRange(spUser,spinner.getSelectedItem().toString());
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
                        Collections.sort(usersList);
                        images.clear();names.clear();range.clear();
                        for(GetUsersByDistanceModel user : usersList){
                            images.add(user.getPicture()); names.add(user.getUsername()); range.add(user.getDistance().toString());
                        }
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<GetUsersByDistanceModel>> call, @NonNull Throwable t) {
                        Log.e("API",t.getMessage());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}