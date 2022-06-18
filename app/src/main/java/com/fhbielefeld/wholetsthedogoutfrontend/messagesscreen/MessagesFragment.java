package com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.AllChatsModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersByDistanceModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.MessagesModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentMessagesBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.login.ui.LoginActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.searchscreen.SearchFragment;
import com.fhbielefeld.wholetsthedogoutfrontend.sharedpreferences.UserDataToSP;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MessagesFragment extends Fragment {

    private FragmentMessagesBinding binding;
    private List<AllChatsModel> usersList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel searchViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

     @Override public void onViewCreated(View view, Bundle savedInstanceState){
         SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
         String spUser = sp.getString("username", "");
         Log.d("WLMDO.SharedPreferences", spUser);

         ArrayList<String>images = new ArrayList<>(); ArrayList<String>username = new ArrayList<>(); ArrayList<String> message = new ArrayList<>(); ArrayList<String>date = new ArrayList<>();


         Retrofit retrofit = APIClient.getClient();

         APIInterface apiInterface = retrofit.create(APIInterface.class);

         Call<List<AllChatsModel>> call = apiInterface.getAllChats(spUser);
         call.enqueue(new Callback<List<AllChatsModel>>(){

             @Override
             public void onResponse(Call<List<AllChatsModel>> call, Response<List<AllChatsModel>> response) {

                 usersList = response.body();
                 for(AllChatsModel user : usersList){
                     username.add(user.getUsername()); message.add(user.getMessage()); images.add("Foo"); date.add(user.getDateTime());
                 }
                 RecyclerView recyclerView = view.findViewById(R.id.messages_Recyclerview);
                 MessagesRecyclerAdapter myAdapter = new MessagesRecyclerAdapter(view.getContext(),username,message,images,date);
                 recyclerView.setAdapter(myAdapter);
                 recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                 //nach dem füllen der liste
                 myAdapter.notifyDataSetChanged();
             }

             @Override
             public void onFailure(Call<List<AllChatsModel>> call, Throwable t) {

             }
         });




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}