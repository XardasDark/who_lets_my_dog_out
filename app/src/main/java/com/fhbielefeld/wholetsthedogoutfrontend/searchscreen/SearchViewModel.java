package com.fhbielefeld.wholetsthedogoutfrontend.searchscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Suchen");
    }

    public LiveData<String> getText() {
        return mText;
    }
}