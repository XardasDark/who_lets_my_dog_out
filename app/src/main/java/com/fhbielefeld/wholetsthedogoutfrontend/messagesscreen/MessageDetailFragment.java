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

import com.fhbielefeld.wholetsthedogoutfrontend.MainActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.MessagesModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.SendMessageModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentMessagesDetailBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link MessagesFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class MessageDetailFragment extends Fragment {

    TextView tvMessages, tvDate;

    String message = "";
    String spUser = "";
    String targetUser= "";


    private List<MessagesModel> messagesList;

    private FragmentMessagesDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessageDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void viewInitializations(View view) {
        tvMessages = view.findViewById(R.id.message_my);
        tvDate = view.findViewById(R.id.et_last_name);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel messagesViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        binding = FragmentMessagesDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState){
        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
        spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);
        String test = "kspacey";
        Log.e("Chat", "onViewCreated");

        viewInitializations(view);

        ArrayList<String> message = new ArrayList<>(); ArrayList<String>date = new ArrayList<>(); ArrayList<Boolean>own = new ArrayList<>();
        RecyclerView recyclerView= view.findViewById(R.id.messages_ListMessages);
        MessageDetailRecylerAdapter myAdapter = new MessageDetailRecylerAdapter(view.getContext(),message,own,MainActivity.targetUser);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        tvMessages = view.findViewById(R.id.message_my);

        Retrofit retrofit = APIClient.getClient();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<List<MessagesModel>> call = apiInterface.getMessages(spUser, MainActivity.targetUser);
        call.enqueue(new Callback<List<MessagesModel>>(){

            @Override
            public void onResponse(Call<List<MessagesModel>> call, Response<List<MessagesModel>> response) {

                message.clear();date.clear();own.clear();

                messagesList = response.body();
                for(MessagesModel chat : messagesList){
                    message.add(chat.getMessage()); date.add(chat.getDate()); own.add(chat.getOwn());
                    Log.e("Chat", chat.getMessage() + chat.getDate());
                    //tvMessages = view.findViewById(R.id.messages_Recyclerview);
                    if (tvMessages != null){
                        Log.e("Chat", "Ging");
                        tvMessages.setText(String.valueOf("firstname"));
                    }
                    myAdapter.notifyDataSetChanged();

                    //someText.setText("Hi! I updated you manually!");
                    //tvMessages.setText(chat.getMessage());
                }
               //RecyclerView recyclerView = view.findViewById(R.id.messages_Recyclerview);
                //MessagesRecyclerAdapter myAdapter = new MessagesRecyclerAdapter(view.getContext(),username,message,images,date);
                //recyclerView.setAdapter(myAdapter);
                ///recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                //nach dem füllen der liste
                //myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MessagesModel>> call, Throwable t) {

            }

        });

    }

    public void sendMessage (View view) {

        if (tvMessages.getText().toString().equals("") || tvMessages.getText().toString() == null) {
            Toast toast = Toast.makeText(view.getContext(),"Nachricht darf nicht leer sein!",Toast.LENGTH_LONG);
            toast.show();
        } else {
            message = tvMessages.getText().toString();

            Retrofit retrofit = APIClient.getClient();

            APIInterface apiInterface = retrofit.create(APIInterface.class);
            Call<SendMessageModel> call = apiInterface.sendMessage(spUser, targetUser, message);

            call.enqueue(new Callback<SendMessageModel>() {
                @Override
                public void onResponse(Call<SendMessageModel> call, Response<SendMessageModel> response) {
                    if(!response.isSuccessful()){
                        if(response.code() == 400){
                            Log.e("API",response.message());
                            Toast toast = Toast.makeText(view.getContext(),"Code "+response.code(),Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return;
                    }
                    Toast toast = Toast.makeText(view.getContext(),"Nachricht erfolgreich versendet!",Toast.LENGTH_LONG);
                    toast.show();
                }

                @Override
                public void onFailure(Call<SendMessageModel> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {

    }
}