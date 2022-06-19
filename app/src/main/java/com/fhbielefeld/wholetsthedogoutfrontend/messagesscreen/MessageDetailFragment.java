package com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.AllChatsModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.MessagesModel;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.ActivitySignupBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentMessagesDetailBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentProfilBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.profilscreen.ProfilViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private PlaceholderContent.PlaceholderItem mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;

    TextView tvMessages, tvDate;

    String message = "Leer";
    String date = "Leer";

    private List<MessagesModel> messagesList;

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = PlaceholderContent.ITEM_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
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
        tvMessages = view.findViewById(R.id.message_1message);
        tvDate = view.findViewById(R.id.et_last_name);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel searchViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        binding = FragmentMessagesDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
/*    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMessagesDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        Log.e("Chat", "onCreateView");
        mToolbarLayout = rootView.findViewById(R.id.chat_detail_fragment);
        mTextView = binding.editText;

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }*/

    @Override public void onViewCreated(View view, Bundle savedInstanceState){
        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
        String spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);
        String test = "kspacey";
        Log.e("Chat", "onViewCreated");
        viewInitializations(view);

        tvMessages = view.findViewById(R.id.message_1message);

      ArrayList<String> message = new ArrayList<>(); ArrayList<String>date = new ArrayList<>(); ArrayList<String>own = new ArrayList<>();


        Retrofit retrofit = APIClient.getClient();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<List<MessagesModel>> call = apiInterface.getMessages(spUser, test);
        call.enqueue(new Callback<List<MessagesModel>>(){

            @Override
            public void onResponse(Call<List<MessagesModel>> call, Response<List<MessagesModel>> response) {

                messagesList = response.body();
                for(MessagesModel chat : messagesList){
                    message.add(chat.getMessage()); date.add(chat.getDate()); //own.add(chat.getOwn());
                    Log.e("Chat", chat.getMessage() + chat.getDate());
                    //tvMessages = view.findViewById(R.id.messages_Recyclerview);
                    if (tvMessages != null){
                        Log.e("Chat", "Ging");
                        tvMessages.setText(String.valueOf("firstname"));
                    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {

    }
}