package com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentMessagesBinding;
import com.fhbielefeld.wholetsthedogoutfrontend.login.ui.LoginActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.sharedpreferences.UserDataToSP;

public class MessagesFragment extends Fragment {

    private FragmentMessagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel searchViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMessages;
        searchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

     @Override public void onViewCreated(View view, Bundle savedInstanceState){
         SharedPreferences sp = this.getActivity().getSharedPreferences("WLMDO" , Context.MODE_PRIVATE);
         String spUser = sp.getString("username", "");
         Log.d("WLMDO.SharedPreferences", spUser);
     }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}