package com.fhbielefeld.wholetsthedogoutfrontend.searchscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentMessagesDetailBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentSearchUserProfilBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentSearchUserProfilLayoutBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen.MessageDetailRecylerAdapter;
import com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen.MessagesViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchUserFragment extends Fragment {

    private FragmentSearchUserProfilLayoutBinding binding;
    private List<MessagesModel> messagesList;

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
        //tvMessages = view.findViewById(R.id.message_1message);
        //tvDate = view.findViewById(R.id.et_last_name);
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

    @Override public void onViewCreated(View view, Bundle savedInstanceState){
        SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
        String spUser = sp.getString("username", "");
        Log.d("WLMDO.SharedPreferences", spUser);
        Log.e("Search", "onViewCreated");

        }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {

    }
}
