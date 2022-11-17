package com.example.whodo.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.whodo.LoginActivity;
import com.example.whodo.R;

public class DataInputFragment extends Fragment {
    private EditText ClaveSimpleEditText;
    private EditText ClaveSimpleEditText2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root= inflater.inflate(R.layout.fragment_login_data_input, container, false);

        ClaveSimpleEditText= root.findViewById(R.id.ClaveSimpleEditText);
        ClaveSimpleEditText2= root.findViewById(R.id.ClaveSimpleEditText2);

        ImageButton ButtonShowHide = root.findViewById(R.id.SignUpShowHideButton);
        ImageButton ButtonShowHide2 = root.findViewById(R.id.SignUpShowHideButton2);
        Button ButtonSignUpConfirmation = root.findViewById(R.id.SignUp_User);
        Button ButtonSignUpGoogle = root.findViewById(R.id.SignUp_with_google);
        Button ButtonSignUpFacebook = root.findViewById(R.id.SignUp_with_Facebook);

        ButtonShowHide.setOnClickListener(this::onClick);
        ButtonShowHide2.setOnClickListener(this::onClick);
        ButtonSignUpConfirmation.setOnClickListener(this::onClick);
        ButtonSignUpGoogle.setOnClickListener(this::onClick);
        ButtonSignUpFacebook.setOnClickListener(this::onClick);

        ClaveSimpleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckBox lengthValidationCheckBox = root.findViewById(R.id.checkBox);
                CheckBox lowerUpperCaseCheckBox = root.findViewById(R.id.checkBox2);
                CheckBox symbolCaseCheckBox = root.findViewById(R.id.checkBox3);

                lengthValidationCheckBox.setChecked(ClaveSimpleEditText.getText().toString().trim().length() >= 8);

                lowerUpperCaseCheckBox.setChecked(hasUpperCase(ClaveSimpleEditText.getText().toString()) && hasLowerCase(ClaveSimpleEditText.getText().toString()));

                symbolCaseCheckBox.setChecked(hasSymbol(ClaveSimpleEditText.getText().toString().trim()) && hasNumbers(ClaveSimpleEditText.getText().toString().trim()));
                //symbolCaseCheckBox.setChecked(hasNumbers(ClaveSimpleEditText.getText().toString().trim()));
                //symbolCaseCheckBox.setChecked(hasSymbol(ClaveSimpleEditText.getText().toString().trim()));

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        return root;
    }

    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {

        switch (view.getId()) {

            case R.id.SignUpShowHideButton:
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

            case R.id.SignUpShowHideButton2:
                Log.i("Inputtype", ""+ClaveSimpleEditText2.getInputType());
                if(ClaveSimpleEditText2.getInputType()==1 )
                {
                    ClaveSimpleEditText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ClaveSimpleEditText2.setSelection(ClaveSimpleEditText2.length());
                }
                else //(ClaveSimpleEditText.getInputType()==128)
                {
                    ClaveSimpleEditText2.setInputType(InputType.TYPE_CLASS_TEXT  );
                    ClaveSimpleEditText2.setSelection(ClaveSimpleEditText2.length());
                }
                break;

            case R.id.SignUp_User:
                Log.i("botton1", "Presionaste el boton <Registrarse>" );
                break;
            case R.id.SignUp_with_google:
                //Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Log.i("botton2", "Presionaste el boton <Google>" );
                //startActivityForResult(signInIntent, RC_SIGN_IN);
                break;

            case R.id.SignUp_with_Facebook:
                Log.i("botton3", "Presionaste el boton <Facebook>" );
                break;
        }


    }

    private static boolean hasSymbol(CharSequence data) {
        String password = String.valueOf(data);
        return !password.matches(".*[!@#$%^&*+=?\\-].*");
    }


    private static boolean hasNumbers(CharSequence data) {
        String password = String.valueOf(data);
        return !password.matches(".*[0-9].*");
    }

    private static boolean hasUpperCase(CharSequence data) {
        String password = String.valueOf(data);
        return !password.equals(password.toLowerCase());
    }

    private static boolean hasLowerCase(CharSequence data) {
        String password = String.valueOf(data);
        return !password.equals(password.toUpperCase());
    }


    }

