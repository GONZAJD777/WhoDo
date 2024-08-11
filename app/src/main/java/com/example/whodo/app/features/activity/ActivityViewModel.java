package com.example.whodo.app.features.activity;


import androidx.lifecycle.ViewModel;

public class ActivityViewModel extends ViewModel {

    private String mText1;

    public ActivityViewModel() {
        mText1="Actividad";
    }

    public String getText() {
        return mText1;
    }
}