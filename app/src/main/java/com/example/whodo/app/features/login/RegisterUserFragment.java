package com.example.whodo.app.features.login;

import static android.content.ContentValues.TAG;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.R;
import com.example.whodo.app.domain.user.UserDTO;
import com.example.whodo.app.domain.user.UserMapper;
import com.example.whodo.app.domain.user.dao.FirebaseUserDAO;
import com.example.whodo.app.domain.user.dao.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RegisterUserFragment extends Fragment {
    private EditText MailSimpleEditText;
    private EditText ClaveSimpleEditText;
    private EditText ClaveSimpleEditText2;
    private CheckBox lengthValidationCheckBox;
    private CheckBox lowerUpperCaseCheckBox;
    private CheckBox symbolCaseCheckBox;
    private CheckBox agreementCheckbox;
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root= inflater.inflate(R.layout.act_login_frag_register_user, container, false);

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

                if (!MailSimpleEditText.getText().toString().equals("") && lengthValidationCheckBox.isChecked() && lowerUpperCaseCheckBox.isChecked() && symbolCaseCheckBox.isChecked() && agreementCheckbox.isChecked())
                {
                        mAuth.createUserWithEmailAndPassword(MailSimpleEditText.getText().toString(),ClaveSimpleEditText.getText().toString()).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        sendVerificationEmail();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(requireContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
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
    private void sendVerificationEmail()
    {
        currentUser= mAuth.getCurrentUser();
        assert currentUser != null;
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            String Name=MailSimpleEditText.getText().toString().toUpperCase();
                            String Email=MailSimpleEditText.getText().toString();
                            String Password=ClaveSimpleEditText.getText().toString();
                            String Uid=currentUser.getUid();

                            User DefaultUser = new User(Uid,Name,Email,Password);
                            DefaultUser.setCreateDate(creationDateParse(Objects.requireNonNull(mAuth.getCurrentUser().getMetadata()).getCreationTimestamp()));

                            UserDao<UserDTO> userDao = new FirebaseUserDAO();
                            userDao.create(UserMapper.toDTO(DefaultUser), new Callback<UserDTO>() {
                                @Override
                                public void onSuccess(UserDTO userDTO) {
                                    //Luego de la creacion se procede a mostrar un mensaje, llamar a MainActivity y cerrar la actividad de logeo
                                    Log.d(TAG, "createUserWithEmail:success ///// UID: " + DefaultUser.getUid());
                                    Toast.makeText(requireContext(), "Authentication Success. Please verify your Email",Toast.LENGTH_SHORT).show();

                                    // email sent
                                    // after email is sent just logout the user and finish this activity
                                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                                    requireActivity().startActivity(intent);
                                    FirebaseAuth.getInstance().signOut();
                                    requireActivity().finish();
                                }
                                @Override
                                public void onError(Exception e) {
                                    Toast.makeText(requireContext(), "Authentication Failed. Please verify your Email:" + e,Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Toast.makeText(getContext(), "We could not send you validation email, please try again using a valid email address", Toast.LENGTH_LONG).show();
                            currentUser.delete();
                        }
                    }
                });
    }

    private long creationDateParse ( Long pTimestamp)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        long dateString = Long.parseLong(formatter.format(new Date(Long.parseLong(String.valueOf(pTimestamp)))));
        Log.i("DATE", "User Creation Date: "+ dateString);
        return dateString;
    }

    }

