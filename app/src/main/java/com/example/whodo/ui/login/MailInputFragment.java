package com.example.whodo.ui.login;

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

public class MailInputFragment extends Fragment {
    ImageButton Button;
    EditText ClaveSimpleEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login_mail_input, container, false);

        Button = root.findViewById(R.id.LoginShowHideButton);
        ClaveSimpleEditText= root.findViewById(R.id.ClaveSimpleEditText);
        Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here

                Log.i("Inputtype", ""+ClaveSimpleEditText.getInputType());
                if(ClaveSimpleEditText.getInputType()==1 )
                {
                    ClaveSimpleEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                else //(ClaveSimpleEditText.getInputType()==128)
                {
                    ClaveSimpleEditText.setInputType(InputType.TYPE_CLASS_TEXT  );
                }

            }
        });






        return root;
    }
}
