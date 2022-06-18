package com.example.whodo.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.ui.messages.MessagesViewModel;

public class MailInputFragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);

        return inflater.inflate(R.layout.fragment_login_mail_input, container, false);
    }
}
