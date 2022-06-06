package com.fhbielefeld.wholetsthedogoutfrontend.profilscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfilViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profil fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}