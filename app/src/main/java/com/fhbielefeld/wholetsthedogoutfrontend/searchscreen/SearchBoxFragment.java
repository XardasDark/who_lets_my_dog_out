package com.fhbielefeld.wholetsthedogoutfrontend.searchscreen;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fhbielefeld.wholetsthedogoutfrontend.R;

public class SearchBoxFragment extends Fragment {

    public static SearchBoxFragment newInstance() {
        return new SearchBoxFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_box, container, false);
    }
}