package com.example.whodo.features.favorites;


import androidx.lifecycle.ViewModel;

public class FavoritesViewModel extends ViewModel {

    private String mText1;

    public FavoritesViewModel() {
        mText1="Favoritos";
    }

    public String getText() {
        return mText1;
    }
}