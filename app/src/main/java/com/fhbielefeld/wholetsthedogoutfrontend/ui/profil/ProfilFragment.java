package com.fhbielefeld.wholetsthedogoutfrontend.ui.profil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fhbielefeld.wholetsthedogoutfrontend.databinding.FragmentProfilBinding;

public class ProfilFragment extends Fragment {

    private FragmentProfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfilViewModel searchViewModel =
                new ViewModelProvider(this).get(ProfilViewModel.class);

        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfil;
        searchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}