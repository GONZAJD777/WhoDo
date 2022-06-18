package com.example.whodo.ui.hire;

import androidx.lifecycle.ViewModel;

public class HireViewModel extends ViewModel {

    private String mText1;

    public HireViewModel()  {
        mText1="Favoritos";
    }

    public String getText() {
        return mText1;
    }
}