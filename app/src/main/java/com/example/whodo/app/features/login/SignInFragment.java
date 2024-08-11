package com.example.whodo.app.features.login;

import static android.content.ContentValues.TAG;

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

import com.example.whodo.app.MainActivity;
import com.example.whodo.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {
    ImageButton ButtonShowHide;
    Button ButtonLoginConfirmation;
    Button ButtonLoginGoogle;
    Button ButtonLoginFacebook;
    GoogleSignInClient mGoogleSignInClient;

   private EditText ClaveSimpleEditText;
    private EditText MailSimpleEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.act_login_frag_sign_in, container, false);

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

        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


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
                else
                {
                    ClaveSimpleEditText.setInputType(InputType.TYPE_CLASS_TEXT  );
                    ClaveSimpleEditText.setSelection(ClaveSimpleEditText.length());
                }
                break;
            case R.id.button_LoginConfirmation:
                Log.i("botton1", "Presionaste el boton <login with Email>" );
            if (!MailSimpleEditText.getText().toString().equals("") && !ClaveSimpleEditText.getText().toString().equals("") ) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(MailSimpleEditText.getText().toString(), ClaveSimpleEditText.getText().toString()).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                checkIfEmailVerified();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "LoginUserWithEmail:failure", task.getException());
                                Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        });
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
    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (user.isEmailVerified())
        {
            //TODO actualizar en la base el estado y fecha de validacion de la cuenta una vez que el usuario logea tomar la variable de validacion y verificar si esta en 1, sino lo esta actualizarla a 1 y actualizar la fecha de validacion - DONE
            //TODO implementar mecanismo para borrar cuentas no validadas
            Log.d(TAG, "LoginUserWithEmail:success");
            // user is verified, so you can finish this activity or send user to activity which you want.
            Toast.makeText(requireContext(), "Authentication Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireActivity(), MainActivity.class);

            requireActivity().startActivity(intent);
            requireActivity().finish();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(requireContext(), "Authentication Failed, you should verify your email address", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

        }
    }
}
