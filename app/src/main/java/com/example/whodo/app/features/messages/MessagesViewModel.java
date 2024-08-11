package com.example.whodo.app.features.messages;

import androidx.lifecycle.ViewModel;

public class MessagesViewModel extends ViewModel {

    private String mText1;

    public MessagesViewModel() {
        mText1="Messages";
    }

    public String getText() {
        return mText1;
    }
}