package com.example.whodo.app.features.login;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.app.Callback;
import com.example.whodo.app.MainActivity;
import com.example.whodo.R;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.user.UserFactory;
import com.example.whodo.app.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class SignInFragment extends Fragment {
    private String TAG = "LOGGER-SIGN-IN-FRAGMENT";
    private ImageButton ButtonShowHide;
    private Button ButtonLoginConfirmation;
    private Button ButtonLoginGoogle;
    private Button ButtonLoginFacebook;
    private EditText ClaveSimpleEditText;
    private EditText MailSimpleEditText;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private MainActivityViewModel mMainActivityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_login_frag_sign_in, container, false);

        try{
            mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        }catch (Exception e){
            Log.w(TAG, "ERROR al crear el viewModel", e);

        }

        ClaveSimpleEditText= root.findViewById(R.id.ClaveSimpleEditText);
        MailSimpleEditText= root.findViewById(R.id.MailSimpleEditText);

        ButtonShowHide = root.findViewById(R.id.LoginShowHideButton);
        ButtonLoginConfirmation = root.findViewById(R.id.button_LoginConfirmation);
        ButtonLoginGoogle = root.findViewById(R.id.Login_with_google);
        ButtonLoginFacebook = root.findViewById(R.id.Login_with_facebook);

        ButtonShowHide.setOnClickListener(this::onClick);
        ButtonLoginConfirmation.setOnClickListener(this::onClick);
        ButtonLoginGoogle.setOnClickListener(this::onClick);
        ButtonLoginFacebook.setOnClickListener(this::onClick);


        return root;

    }
    private void signInWithEmailAndPassword(String pUserEmail, String pUserPass) {
        mAuth.signInWithEmailAndPassword(pUserEmail, pUserPass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                this.checkIfEmailVerified(pUserEmail, pUserPass); //si se consigue logear procede a verificar si el usuario verifico el mail

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "LoginUserWithEmail:failure ", task.getException());
                Toast.makeText(requireContext(), "Authentication failed ", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
            }
        });
    }
    private void checkIfEmailVerified(String pUserEmail, String pUserPass){
        if (mAuth.getCurrentUser() != null){
            if (mAuth.getCurrentUser().isEmailVerified())
            {
                //Si el usuario verifico el mail , se procede a crear el usuario y a abrir la UI
                createNewUser(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),pUserEmail,pUserPass);
            }
            else
            {
                // email is not verified, so just prompt the message to the user and restart this activity.
                // NOTE: don't forget to log out the user.
                Toast.makeText(requireContext(), "Authentication Failed, you should verify your email address ", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
            }
        }else {
            Toast.makeText(requireContext(), "User doesn't exist ", Toast.LENGTH_SHORT).show();
        }

    }
    private void createNewUser(String pAuthId,String pUserEmail, String pUserPass){

        //TODO: encryptar el password antes de guardarlo
        User NewUser = UserFactory.newLoginUser(pAuthId,pUserEmail,pUserEmail,pUserPass,1,1,new ArrayList<>(),new ArrayList<>(),
                Utils.creationDateParse(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getMetadata()).getCreationTimestamp()));
        //Setea el usuario como creado y activo 1(desactivador 0)
        //Setea el usuario como Cliente 1(proveedor 2)
        // se establecen los lenguajes y services vacios para evitar errores de nullpointer exception

        Log.d(TAG, "USER CREATION DATE:" + NewUser.getCreateDate());

        mMainActivityViewModel.createUser(NewUser, new Callback<>() {
            @Override
            public void onSuccess(User pUser) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                requireActivity().startActivity(intent);
                requireActivity().finish();
                //Luego de la creacion se procede a mostrar un mensaje, llamar a MainActivity y cerrar la actividad de logeo
                Log.d(TAG, "User has been created: " + NewUser.getAuthId());
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(requireContext(), "User couldn't be created: " + e,Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
            }
        });
    }
    @SuppressLint("NonConstantResourceId")
    private void onClick(View view){
        switch (view.getId()) {

            case R.id.LoginShowHideButton:
                Log.i("Inputtype", ""+ClaveSimpleEditText.getInputType());
                if(ClaveSimpleEditText.getInputType()==1 )
                {
                    ClaveSimpleEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ClaveSimpleEditText.setSelection(ClaveSimpleEditText.length());
                }
                else
                {
                    ClaveSimpleEditText.setInputType(InputType.TYPE_CLASS_TEXT  );
                    ClaveSimpleEditText.setSelection(ClaveSimpleEditText.length());
                }
                break;

            case R.id.button_LoginConfirmation:
                Log.i("botton1", "Presionaste el boton <login with Email>" );
                String UserEmail =MailSimpleEditText.getText().toString();
                String UserPass =ClaveSimpleEditText.getText().toString();
                if (!UserEmail.isEmpty() && !UserPass.isEmpty()) {
                    this.signInWithEmailAndPassword(UserEmail,UserPass); //verifica si el usuario esta en FirebaseAuth y devuelve los datos
                }
                else
                {
                    Toast.makeText(requireContext(), "Authentication failed. Need an Valid Email", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.Login_with_google:
                //Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Log.i("botton2", "Presionaste el boton <login with Google>" );
                //startActivityForResult(signInIntent, RC_SIGN_IN);
                break;

            case R.id.Login_with_facebook:
                Log.i("botton3", "Presionaste el boton <login with Facebook>" );
                break;
        }
    }


}
