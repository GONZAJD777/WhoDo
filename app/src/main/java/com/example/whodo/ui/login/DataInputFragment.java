package com.example.whodo.ui.login;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.fragment.app.Fragment;

import com.example.whodo.BusinessClasses.User;
import com.example.whodo.LoginActivity;
import com.example.whodo.MainActivity;
import com.example.whodo.R;
import com.example.whodo.crud.CRUD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;

public class DataInputFragment extends Fragment {
    private EditText MailSimpleEditText;
    private EditText ClaveSimpleEditText;
    private EditText ClaveSimpleEditText2;
    private CheckBox lengthValidationCheckBox;
    private CheckBox lowerUpperCaseCheckBox;
    private CheckBox symbolCaseCheckBox;
    private CheckBox agreementCheckbox;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root= inflater.inflate(R.layout.fragment_login_data_input, container, false);



        MailSimpleEditText= root.findViewById(R.id.MailSimpleEditText);
        ClaveSimpleEditText= root.findViewById(R.id.ClaveSimpleEditText);
        ClaveSimpleEditText2= root.findViewById(R.id.ClaveSimpleEditText2);

        lengthValidationCheckBox = root.findViewById(R.id.checkBox);
        lowerUpperCaseCheckBox = root.findViewById(R.id.checkBox2);
        symbolCaseCheckBox = root.findViewById(R.id.checkBox3);
        agreementCheckbox = root.findViewById(R.id.checkBox4);

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
                lengthValidationCheckBox.setChecked(ClaveSimpleEditText.getText().toString().trim().length() >= 8);
                lowerUpperCaseCheckBox.setChecked(hasUpperCase(ClaveSimpleEditText.getText().toString()) && hasLowerCase(ClaveSimpleEditText.getText().toString()));
                symbolCaseCheckBox.setChecked(hasSymbol(ClaveSimpleEditText.getText().toString().trim()) && hasNumbers(ClaveSimpleEditText.getText().toString()));
                //symbolCaseCheckBox.setChecked(hasNumbers(ClaveSimpleEditText.getText().toString()));
                //symbolCaseCheckBox.setChecked(hasSymbol(ClaveSimpleEditText.getText().toString().trim()));

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return root;
    }
    @SuppressLint("NonConstantResourceId")
    private void onClick(View view)  {

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

                if (lengthValidationCheckBox.isChecked() && lowerUpperCaseCheckBox.isChecked() && symbolCaseCheckBox.isChecked() && agreementCheckbox.isChecked())
                {
                    //should get most of this parameters like address and lat from googlelocations and set the other with default values to be updated later
                    // emial, pass, cell_phone are mandatory
                    CRUD crud = new CRUD();
                    Log.i("botton1", "Presionaste el boton <Registrarse>" );
                    boolean i= true;



                     //crud.ReadUser(MailSimpleEditText.getText().toString());


                    Log.i("botton1", "EL EMAIL ESTA EN USO?" + i);

                        String Name=MailSimpleEditText.getText().toString();
                        int Birthdate=19000101;
                        String Email=MailSimpleEditText.getText().toString().toUpperCase();
                        String Address="".toUpperCase();
                        double Latitude=0.0;
                        double Longitude=0.0;
                        String Phone="321";
                        String Type="CUSTOMER";
                        String Password=ClaveSimpleEditText.getText().toString();


                        //IMPORTANTE VALIDAR EL EMAIL ANTES DE HACER EL PUSH PARA NO PERMITIR DUPLICADOS
                        User User = new User(Name, Birthdate, Email,Address,Latitude,Longitude,Phone,Type,Password);

                    // Initialize Firebase Auth
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(User.getEmail(), User.getPassword())
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        assert user != null;
                                        User.setUid(user.getUid());
                                        Log.d(TAG, "createUserWithEmail:success ///// UID: " + User.getUid());

                                        crud.CreateUser(User);
                                        Toast.makeText(requireContext(), "Authentication Success. Please verify your Email",Toast.LENGTH_SHORT).show();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(requireContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(getContext(), "You need to input a valid password and accept the use terms", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.SignUp_with_google:
                //Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Log.i("botton2", "Presionaste el boton <Google>" );
                //startActivityForResult(signInIntent, RC_SIGN_IN);

                if (agreementCheckbox.isChecked())
                {
                    Toast.makeText(getContext(), "User Registered", Toast.LENGTH_LONG).show();
                    this.requireActivity().finish();
                }
                else
                {
                    Toast.makeText(getContext(), "You need to input a valid password and accept the use terms", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.SignUp_with_Facebook:
                Log.i("botton3", "Presionaste el boton <Facebook>" );

                if (agreementCheckbox.isChecked())
                {
                    Toast.makeText(getContext(), "User Registered", Toast.LENGTH_LONG).show();
                    this.requireActivity().finish();

                }
                else
                {
                    Toast.makeText(getContext(), "You need to input a valid password and accept the use terms", Toast.LENGTH_LONG).show();
                }

                break;
        }


    }

    private static boolean hasSymbol(CharSequence data) {
        String password = String.valueOf(data);
        //Log.i("Checkbox", "Clave introducida:"+ password +"|");
        //Log.i("Checkbox", "Validacion num y symbol:"+ password.matches(".*[!@#$%^&*+=?\\-].*") +"|");
        return password.matches(".*[!@#$%^&*+=?\\-].*");
    }


    private static boolean hasNumbers(CharSequence data) {
        String password = String.valueOf(data);
        //Log.i("Checkbox", "Clave introducida:"+ password +"|");
        //Log.i("Checkbox", "Validacion num y symbol:"+ password.matches(".*[0-9].*") +"|");
        return password.matches(".*[0-9].*");
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

