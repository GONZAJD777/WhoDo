package com.example.whodo.ui.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.ui.messages.MessagesViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MailInputFragment extends Fragment {
    ImageButton ButtonShowHide;
    Button ButtonLoginConfirmation;
    Button ButtonLoginGoogle;
    Button ButtonLoginFacebook;

    EditText ClaveSimpleEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login_mail_input, container, false);

        ClaveSimpleEditText= root.findViewById(R.id.ClaveSimpleEditText);

        ButtonShowHide = root.findViewById(R.id.LoginShowHideButton);
        ButtonLoginConfirmation = root.findViewById(R.id.LoginConfirmation);
        ButtonLoginGoogle = root.findViewById(R.id.Login_with_google);
        ButtonLoginFacebook = root.findViewById(R.id.Login_with_facebook);


        ButtonShowHide.setOnClickListener(this::onClick);
        ButtonLoginConfirmation.setOnClickListener(this::onClick);
        ButtonLoginGoogle.setOnClickListener(this::onClick);
        ButtonLoginFacebook.setOnClickListener(this::onClick);

        return root;
    }

        @SuppressLint("NonConstantResourceId")
        public void onClick(View view)
    {

        switch (view.getId()) {

            case R.id.LoginShowHideButton:

                Log.i("Inputtype", ""+ClaveSimpleEditText.getInputType());
                if(ClaveSimpleEditText.getInputType()==1 )
                {
                    ClaveSimpleEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ClaveSimpleEditText.setSelection(ClaveSimpleEditText.length());
                }
                else //(ClaveSimpleEditText.getInputType()==128)
                {
                    ClaveSimpleEditText.setInputType(InputType.TYPE_CLASS_TEXT  );
                    ClaveSimpleEditText.setSelection(ClaveSimpleEditText.length());
                }
            case R.id.LoginConfirmation:


            case R.id.Login_with_google:
            case R.id.Login_with_facebook:
            }
    }
}
