package com.example.whodo.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.whodo.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MailInputFragment extends Fragment {
    ImageButton ButtonShowHide;
    Button ButtonLoginConfirmation;
    Button ButtonLoginGoogle;
    Button ButtonLoginFacebook;
    GoogleSignInClient mGoogleSignInClient;

   private EditText ClaveSimpleEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login_mail_input, container, false);

        ClaveSimpleEditText= root.findViewById(R.id.ClaveSimpleEditText);

        ButtonShowHide = root.findViewById(R.id.LoginShowHideButton);
        ButtonLoginConfirmation = root.findViewById(R.id.button_LoginConfirmation);
        ButtonLoginGoogle = root.findViewById(R.id.Login_with_google);
        ButtonLoginFacebook = root.findViewById(R.id.Login_with_facebook);

        ButtonShowHide.setOnClickListener(this::onClick);
        ButtonLoginConfirmation.setOnClickListener(this::onClick);
        ButtonLoginGoogle.setOnClickListener(this::onClick);
        ButtonLoginFacebook.setOnClickListener(this::onClick);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        return root;

    }

        @SuppressLint("NonConstantResourceId")
        private void onClick(View view)
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
                break;
            case R.id.button_LoginConfirmation:
                Log.i("botton1", "Presionaste el boton <Registrarse>" );
                break;
            case R.id.Login_with_google:
                //Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Log.i("botton2", "Presionaste el boton <Google>" );
                //startActivityForResult(signInIntent, RC_SIGN_IN);
                break;

            case R.id.Login_with_facebook:
                Log.i("botton3", "Presionaste el boton <Facebook>" );
                break;
            }
    }
}
