package com.example.whodo.app.features.profile.fragments;

import static com.example.whodo.app.features.profile.ProfileHolderActivity.hideKeyboard;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.SingletonUser;
import com.example.whodo.app.features.profile.ProfileItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SecurityFragment extends Fragment {

    private final String TAG1="SecurityFragment";
    private String LoggedUserEmail;
    private ProfileItem item_Password;
    private ProfileItem item_Wallet;
    //----------------------------------------
    //WALLET ADDRESS VARIABLES
    private EditText WalletEmailSimpleEditText;
    private EditText WalletPassSimpleEditText;
    private EditText WalletAddressSimpleEditText;
    private BottomSheetBehavior<LinearLayout> PasswordBottomSheetBehavior ;
    //----------------------------------------
    //PASSWORD UPDATE VARIABLES
    private EditText EmailSimpleEditText;
    private EditText PasswordSimpleEditText;
    private EditText NewPass1SimpleEditText;
    private EditText NewPass2SimpleEditText;
    private BottomSheetBehavior<LinearLayout> WalletAddressBottomSheetBehavior;
    //----------------------------------------
    private LinearLayout BlackBackground_bottom_sheet;


    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private final FirebaseUser currentUser=mAuth.getCurrentUser();
    private MainActivityViewModel model;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_profile_frag_security, container, false);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        EmailSimpleEditText = root.findViewById(R.id.EmailSimpleEditText);
        PasswordSimpleEditText = root.findViewById(R.id.PasswordSimpleEditText);
        NewPass1SimpleEditText = root.findViewById(R.id.NewPass1SimpleEditText);
        NewPass2SimpleEditText = root.findViewById(R.id.NewPass2SimpleEditText);

        WalletEmailSimpleEditText = root.findViewById(R.id.WalletEmailSimpleEditText);
        WalletPassSimpleEditText = root.findViewById(R.id.WalletPassSimpleEditText);
        WalletAddressSimpleEditText = root.findViewById(R.id.WalletAddressSimpleEditText);

        TextView ReadyLabelButtonPassUpdate = root.findViewById(R.id.ReadyLabelButtonPassUpdate);
        TextView ReadyLabelButtonWalletAddress = root.findViewById(R.id.ReadyLabelButtonWalletAddress);
        Button UpdatePasswordButton = root.findViewById(R.id.UpdatePasswordButton);
        Button UpdateWalletAddressButton = root.findViewById(R.id.UpdateWalletAddressButton);
        ReadyLabelButtonPassUpdate.setOnClickListener(this::onClick);
        ReadyLabelButtonWalletAddress.setOnClickListener(this::onClick);
        UpdatePasswordButton.setOnClickListener(this::onClick);
        UpdateWalletAddressButton.setOnClickListener(this::onClick);
        FloatingActionButton saveChangesButton = root.findViewById(R.id.SaveChangesButton);
        saveChangesButton.setOnClickListener(this::onClick);


        LinearLayout Items_LinearLayout = root.findViewById(R.id.Items_LinearLayout);
        LinearLayout PasswordChange_bottom_sheet = root.findViewById(R.id.PasswordChange_bottom_sheet);
        LinearLayout WalletAddress_bottom_sheet = root.findViewById(R.id.WalletAddress_bottom_sheet);

        BlackBackground_bottom_sheet = root.findViewById(R.id.BlackBackground_bottom_sheet);
        PasswordBottomSheetBehavior = BottomSheetBehavior.from(PasswordChange_bottom_sheet);
        WalletAddressBottomSheetBehavior = BottomSheetBehavior.from(WalletAddress_bottom_sheet);

        BottomSheetBehavior<LinearLayout> BlackBackgroundBottomSheetBehavior = BottomSheetBehavior.from(BlackBackground_bottom_sheet);

        PasswordBottomSheetBehavior.setDraggable(false);
        WalletAddressBottomSheetBehavior.setDraggable(false);

        //UserNameBottomSheetBehavior.setExpandedOffset(500);
        //PhoneBottomSheetBehavior.setExpandedOffset(500);
        //EmailBottomSheetBehavior.setExpandedOffset(500);

        BlackBackgroundBottomSheetBehavior.setState(STATE_EXPANDED);
        BlackBackgroundBottomSheetBehavior.setDraggable(false);
        BlackBackground_bottom_sheet.setClickable(false);
        BlackBackground_bottom_sheet.setFocusable(false);
        //----------------------------------------------------------
        //PASSWORD
        TextView label_Password = new TextView(getContext());
        label_Password.setText(getString(R.string.SecurityFrag_Password_Label_Title));
        label_Password.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Password = new ProfileItem(getContext());
        item_Password.setText(getString(R.string.SecurityFrag_Password_ItemLabel_Title));
        item_Password.setImage(R.drawable.verificacion_de_escudo_24);
        item_Password.setOnClickListener(v -> {
            setBottomSheetBehavior(PasswordBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EmailSimpleEditText.setText(SingletonUser.getInstance().getEmail());
                    PasswordSimpleEditText.setText("");
                    NewPass1SimpleEditText.setText("");
                    NewPass2SimpleEditText.setText("");
                    setBottomSheetBehavior(PasswordBottomSheetBehavior,1);

                }
            });
        });
        PasswordBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                    case STATE_HIDDEN:
                        //Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        //Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        setBottomSheetBehavior(PasswordBottomSheetBehavior,1);
                        EmailSimpleEditText.setText(SingletonUser.getInstance().getEmail());
                        PasswordSimpleEditText.setText("");
                        NewPass1SimpleEditText.setText("");
                        NewPass2SimpleEditText.setText("");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        //----------------------------------------------------------
        //WALLET ADDRESS
        TextView label_Wallet = new TextView(getContext());
        label_Wallet.setText(getString(R.string.SecurityFrag_WalletAddress_Label_Title));
        label_Wallet.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Wallet = new ProfileItem(getContext());
        item_Wallet.setText(getString(R.string.SecurityFrag_WalletAddress_Label_Title1));
        item_Wallet.setImage(R.drawable.cartera_24);
        item_Wallet.setOnClickListener(v -> {
            setBottomSheetBehavior(WalletAddressBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WalletEmailSimpleEditText.setText(LoggedUserEmail);
                    item_Wallet.setText(getString(R.string.SecurityFrag_WalletAddress_Label_Title1));
                    setBottomSheetBehavior(WalletAddressBottomSheetBehavior,1);
                }
            });

            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Location), Toast.LENGTH_LONG).show();
        });
        WalletAddressBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                    case STATE_HIDDEN:
                        WalletEmailSimpleEditText.setText(LoggedUserEmail);
                        item_Wallet.setText(getString(R.string.SecurityFrag_WalletAddress_Label_Title1));
                        setBottomSheetBehavior(WalletAddressBottomSheetBehavior,1);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        //Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        //----------------------------------------------------------


        Items_LinearLayout.addView(label_Password);
        Items_LinearLayout.addView(item_Password);
        Items_LinearLayout.addView(label_Wallet);
        Items_LinearLayout.addView(item_Wallet);


        loadUserData();
        return root;
    }

    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveChangesButton:
                saveUserData();
                break;
            case R.id.ReadyLabelButtonPassUpdate:
                setBottomSheetBehavior(PasswordBottomSheetBehavior,1);
                EmailSimpleEditText.setText(SingletonUser.getInstance().getEmail());
                PasswordSimpleEditText.setText("");
                NewPass1SimpleEditText.setText("");
                NewPass2SimpleEditText.setText("");
                break;
            case R.id.ReadyLabelButtonWalletAddress:
                WalletEmailSimpleEditText.setText(LoggedUserEmail);
                item_Wallet.setText(getString(R.string.SecurityFrag_WalletAddress_Label_Title1));
                setBottomSheetBehavior(WalletAddressBottomSheetBehavior,1);
                break;
            case R.id.UpdatePasswordButton:
                DialogInterface.OnClickListener PasswordDialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if (currentUser != null) {
                                    reAuthenticate(EmailSimpleEditText.getText().toString(),PasswordSimpleEditText.getText().toString(),"UpdatePassword"); // Comienda el proceso de Update solicitando credenciales
                                    Log.d("REAUTHENTICATE", "EMAIL CREDENTIAL: "+EmailSimpleEditText.getText().toString());
                                    Log.d("REAUTHENTICATE", "PASSWORD CREDENTIAL: "+PasswordSimpleEditText.getText().toString());
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder PasswordBuilder = new AlertDialog.Builder(this.requireContext());
                PasswordBuilder.setMessage(R.string.PersonalInfoFrag_UpdPasswordDialog).setPositiveButton("SI", PasswordDialogClickListener).setNegativeButton("NO", PasswordDialogClickListener).show();
                break;
            case R.id.UpdateWalletAddressButton:
                DialogInterface.OnClickListener WalletDialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if (currentUser != null) {
                                    reAuthenticate(WalletEmailSimpleEditText.getText().toString(),WalletPassSimpleEditText.getText().toString(),"UpdateWalletAddress"); // Comienza el proceso de Update solicitando credenciales
                                    Log.d("REAUTHENTICATE", "EMAIL CREDENTIAL: "+WalletEmailSimpleEditText.getText().toString());
                                    Log.d("REAUTHENTICATE", "PASSWORD CREDENTIAL: "+WalletPassSimpleEditText.getText().toString());
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder WalletBuilder = new AlertDialog.Builder(this.requireContext());
                WalletBuilder.setMessage(R.string.PersonalInfoFrag_UpdWalletAddressDialog).setPositiveButton("SI", WalletDialogClickListener).setNegativeButton("NO", WalletDialogClickListener).show();
                break;
        }
    }
    private void reAuthenticate (String pMail,String pPass, String action){
            if ((pMail!=null && pPass!=null) && (!pMail.equals("") && !pPass.equals("")))
            {
                AuthCredential vCredentials= EmailAuthProvider.getCredential(pMail,pPass);

                assert currentUser != null;
                currentUser.reauthenticate(vCredentials).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("WHODO_LOG", "User re-authenticated.");

                            if (Objects.equals(action, "UpdatePassword"))
                            {
                                updateAuthPassword(NewPass2SimpleEditText.getText().toString());
                            }
                            else if (Objects.equals(action, "UpdateWalletAddress"))
                            {
                                updateWalletAddress(WalletAddressSimpleEditText.getText().toString());
                            }

                        } else {
                            Toast.makeText(requireContext(), "Wrong credentials",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Credentials cannot be null or empty",Toast.LENGTH_SHORT).show();
            }

    }
    private void updateAuthPassword(String pPass){
        assert currentUser != null;
        currentUser.updatePassword(pPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("WHODO_LOG", "User PASSWORD updated.");
                    Toast.makeText(requireContext(), "User PASSWORD updated.",Toast.LENGTH_SHORT).show();
                    SingletonUser.getInstance().setPassword(pPass);
                } else {
                    Toast.makeText(requireContext(), "We found a problem trying to update the PASSWORD, please check your data and try again",Toast.LENGTH_SHORT).show();
                    Log.d("WHODO_LOG", "Error al actualizar el email " );
                }
            }
        });
    }
    private void updateWalletAddress(String pWallet) { }
    private void saveUserData(){
        model.setSelectedFragment(4,View.VISIBLE);
        Log.d(TAG1, "Pressed Save Button");
    }
    private void loadUserData () {
        LoggedUserEmail=SingletonUser.getInstance().getEmail();
        EmailSimpleEditText.setText(LoggedUserEmail);
        WalletEmailSimpleEditText.setText(LoggedUserEmail);
        item_Wallet.setText(getString(R.string.SecurityFrag_WalletAddress_Label_Title1));
    }

    private void setBottomSheetBehavior (BottomSheetBehavior<LinearLayout> mBottomSheetBehavior, Integer mState){
        if (mState==0){
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_EXPANDED);
            BlackBackground_bottom_sheet.setClickable(true);
            BlackBackground_bottom_sheet.setAlpha(0.25F);
        } else {
            hideKeyboard(requireActivity());
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_HIDDEN);
            BlackBackground_bottom_sheet.setClickable(false);
            BlackBackground_bottom_sheet.setAlpha(0);
        }
    }

}
