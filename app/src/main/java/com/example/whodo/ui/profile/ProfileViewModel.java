package com.example.whodo.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private String mText1;


    public ProfileViewModel() {

        mText1="Gonzalo";


    }

    public String getText() { return mText1;
    }
    
}