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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.features.profile.ProfileItem;
import com.example.whodo.app.uiClasses.CustomDatePicker;
import com.example.whodo.R;
import com.example.whodo.app.MainActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class PersonalInfoFragment extends Fragment {
    private String LoggedUserName;
    private String LoggedUserPhoneNumber;
    private String LoggedUserCCN;
    private String LoggedUserEmail;
    private ProfileItem item_UserName;
    private ProfileItem item_PhoneNumber;
    private ProfileItem item_Email;
    private EditText UserNameSimpleEditText;
    private EditText PhoneNumberSimpleEditText;
    private EditText CredentialsEmailSimpleEditText;
    private EditText CredentialsPassSimpleEditText;
    private EditText EmailSimpleEditText;
    private LinearLayout BlackBackground_bottom_sheet;
    private BottomSheetBehavior<LinearLayout> UserNameBottomSheetBehavior ;
    private BottomSheetBehavior<LinearLayout> PhoneBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> EmailBottomSheetBehavior;
    private Spinner countryCodeSpinner;
    private CustomDatePicker BirthdayCalendar;
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private final FirebaseUser currentUser=mAuth.getCurrentUser();
    private MainActivityViewModel model;
    private User mLoggedUser;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_profile_frag_personal_info, container, false);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        UserNameSimpleEditText = root.findViewById(R.id.UserNameSimpleEditText);
        PhoneNumberSimpleEditText = root.findViewById(R.id.PhoneNumberSimpleEditText);
        CredentialsEmailSimpleEditText = root.findViewById(R.id.EmailSimpleEditText);
        CredentialsPassSimpleEditText = root.findViewById(R.id.CredentialsPassSimpleEditText);
        EmailSimpleEditText = root.findViewById(R.id.CredentialsEmailSimpleEditText);

        countryCodeSpinner = root.findViewById(R.id.CountryCodeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),R.array.CountryCodeArray, R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        countryCodeSpinner.setAdapter(adapter);
        countryCodeSpinner.setSelection(0);




        TextView ReadyLabelButtonUserName = root.findViewById(R.id.ReadyLabelButtonUserName);
        TextView ReadyLabelButtonEmail = root.findViewById(R.id.ReadyLabelButtonEmail);
        TextView ReadyLabelButtonPhoneNumber = root.findViewById(R.id.ReadyLabelButtonPhoneNumber);
        Button UpdateEmailButton = root.findViewById(R.id.UpdateEmailButton);

        ReadyLabelButtonUserName.setOnClickListener(this::onClick);
        ReadyLabelButtonEmail.setOnClickListener(this::onClick);
        ReadyLabelButtonPhoneNumber.setOnClickListener(this::onClick);
        UpdateEmailButton.setOnClickListener(this::onClick);

        LinearLayout linearLayout = root.findViewById(R.id.linearLayout);
        LinearLayout UserName_bottom_sheet = root.findViewById(R.id.UserName_bottom_sheet);
        LinearLayout PhoneNumber_bottom_sheet = root.findViewById(R.id.PhoneNumber_bottom_sheet);
        LinearLayout Email_bottom_sheet = root.findViewById(R.id.Email_bottom_sheet);
        BlackBackground_bottom_sheet = root.findViewById(R.id.BlackBackground_bottom_sheet);
        UserNameBottomSheetBehavior = BottomSheetBehavior.from(UserName_bottom_sheet);
        PhoneBottomSheetBehavior = BottomSheetBehavior.from(PhoneNumber_bottom_sheet);
        EmailBottomSheetBehavior = BottomSheetBehavior.from(Email_bottom_sheet);
        BottomSheetBehavior<LinearLayout> BlackBackgroundBottomSheetBehavior = BottomSheetBehavior.from(BlackBackground_bottom_sheet);

        UserNameBottomSheetBehavior.setDraggable(false);
        PhoneBottomSheetBehavior.setDraggable(false);
        EmailBottomSheetBehavior.setDraggable(false);
        //UserNameBottomSheetBehavior.setExpandedOffset(500);
        //PhoneBottomSheetBehavior.setExpandedOffset(500);
        //EmailBottomSheetBehavior.setExpandedOffset(500);

        BlackBackgroundBottomSheetBehavior.setState(STATE_EXPANDED);
        BlackBackgroundBottomSheetBehavior.setDraggable(false);
        BlackBackground_bottom_sheet.setClickable(false);
        BlackBackground_bottom_sheet.setFocusable(false);
        //----------------------------------------------------------
        //USERNAME
        TextView label_UserName = new TextView(getContext());
        label_UserName.setText(getString(R.string.PersonalInfoFrag_UserName));
        label_UserName.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_UserName = new ProfileItem(getContext());
        item_UserName.setText(getString(R.string.PersonalInfoFrag_UserName));
        item_UserName.setImage(R.drawable.lapiz_de_usuario_48);
        item_UserName.setOnClickListener(v -> {
            setBottomSheetBehavior(UserNameBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserNameText(UserNameSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_UserName1), item_UserName);
                    setBottomSheetBehavior(UserNameBottomSheetBehavior,1);
                }
            });
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Description), Toast.LENGTH_LONG).show();


        });
        UserNameBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
                        setUserNameText(UserNameSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_UserName1), item_UserName);
                        setBottomSheetBehavior(UserNameBottomSheetBehavior,1);
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
        //PHONENUMBER
        TextView label_PhoneNumber = new TextView(getContext());
        label_PhoneNumber.setText(getString(R.string.PersonalInfoFrag_PhoneNumber));
        label_PhoneNumber.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_PhoneNumber = new ProfileItem(getContext());
        item_PhoneNumber.setText(getString(R.string.PersonalInfoFrag_PhoneNumber));
        item_PhoneNumber.setImage(R.drawable.telefono_48);
        item_PhoneNumber.setOnClickListener(v -> {
            setBottomSheetBehavior(PhoneBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserPhoneNumber(PhoneNumberSimpleEditText.getText().toString(),countryCodeSpinner.getSelectedItem().toString(),getString(R.string.PersonalInfoFrag_PhoneNumber1),item_PhoneNumber);
                    setBottomSheetBehavior(PhoneBottomSheetBehavior,1);
                }
            });

            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Location), Toast.LENGTH_LONG).show();
        });
        PhoneBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
                        setUserPhoneNumber(PhoneNumberSimpleEditText.getText().toString(),countryCodeSpinner.getSelectedItem().toString(),getString(R.string.PersonalInfoFrag_PhoneNumber1),item_PhoneNumber);
                        setBottomSheetBehavior(PhoneBottomSheetBehavior,1);
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
        //EMAIL
        //TODO agregar validacion cuando cliente cambia de EMAIL y actualizar la base
        TextView label_Email = new TextView(getContext());
        label_Email.setText(getString(R.string.PersonalInfoFrag_Email));
        label_Email.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Email = new ProfileItem(getContext());
        item_Email.setText(getString(R.string.PersonalInfoFrag_Email));
        item_Email.setImage(R.drawable.email_addresss_48);
        item_Email.setOnClickListener(v -> {
            setBottomSheetBehavior(EmailBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //setUserEmailText(EmailSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Email1),item_Email);
                    EmailSimpleEditText.setText(LoggedUserEmail);
                    setBottomSheetBehavior(EmailBottomSheetBehavior,1);
                }
            });
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Location), Toast.LENGTH_LONG).show();
        });
        EmailBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
                        //setUserEmailText(EmailSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Email1),item_Email);
                        EmailSimpleEditText.setText(LoggedUserEmail);
                        setBottomSheetBehavior(EmailBottomSheetBehavior,1);
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
        //BIRTHDAY
        TextView label_Birthday = new TextView(getContext());
        label_Birthday.setText(getString(R.string.PersonalInfoFrag_Birthday));
        label_Birthday.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        BirthdayCalendar = new CustomDatePicker(getContext());
        BirthdayCalendar.setPadding(85, 85, 85, 85);
        //----------------------------------------------------------

        linearLayout.addView(label_UserName);
        linearLayout.addView(item_UserName);
        linearLayout.addView(label_PhoneNumber);
        linearLayout.addView(item_PhoneNumber);
        linearLayout.addView(label_Email);
        linearLayout.addView(item_Email);
        linearLayout.addView(label_Birthday);
        linearLayout.addView(BirthdayCalendar);

        FloatingActionButton SaveChangesButton = root.findViewById(R.id.SaveChangesButton);
        SaveChangesButton.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG).show();
            saveUserData();
        });

        return root;
    }


    public void onStart() {
        super.onStart();
        model.getLoggedUser().observe(requireActivity(),this::loadUserData);
    }
    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.ReadyLabelButtonUserName:
                setUserNameText(UserNameSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_UserName1), item_UserName);
                setBottomSheetBehavior(UserNameBottomSheetBehavior,1);
                break;
            case R.id.ReadyLabelButtonEmail:
                //setUserEmailText(EmailSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Email1),item_Email);
                EmailSimpleEditText.setText(LoggedUserEmail);
                setBottomSheetBehavior(EmailBottomSheetBehavior,1);
                break;
            case R.id.ReadyLabelButtonPhoneNumber:
                setUserPhoneNumber(PhoneNumberSimpleEditText.getText().toString(),countryCodeSpinner.getSelectedItem().toString(),getString(R.string.PersonalInfoFrag_PhoneNumber1),item_PhoneNumber);
                setBottomSheetBehavior(PhoneBottomSheetBehavior,1);
                break;
            case R.id.UpdateEmailButton:
                //mostrar cartelito con confirmacion informando que necesitara validar el email y se desconectara.
                //si acepta, dar update al auth email
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if (currentUser != null) {
                                    reAuthenticate(); // Comienda el proceso de Update solicitando credenciales
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this.requireContext());
                builder.setMessage(R.string.PersonalInfoFrag_UpdEmailDialog).setPositiveButton("SI", dialogClickListener).setNegativeButton("NO", dialogClickListener).show();

                break;
        }
        }
    private void reAuthenticate (){
        if (!CredentialsEmailSimpleEditText.getText().toString().equals(EmailSimpleEditText.getText().toString())){
                if ((CredentialsEmailSimpleEditText!=null && CredentialsPassSimpleEditText!=null) && (!CredentialsEmailSimpleEditText.getText().toString().equals("") && !CredentialsPassSimpleEditText.getText().toString().equals(""))){
                    AuthCredential vCredentials= EmailAuthProvider.getCredential(CredentialsEmailSimpleEditText.getText().toString(),CredentialsPassSimpleEditText.getText().toString());

                    assert currentUser != null;
                    currentUser.reauthenticate(vCredentials).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("WHODO_LOG", "User re-authenticated.");
                                updateAuthEmail();
                            } else {
                                Toast.makeText(requireContext(), "Wrong credentials",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "Credentials cannot be null or empty",Toast.LENGTH_SHORT).show();
                }
        } else {
                Toast.makeText(requireContext(), "Old Email and the new one are equal",Toast.LENGTH_SHORT).show();
        }
    }
    private void updateAuthEmail(){
        assert currentUser != null;
        currentUser.updateEmail(EmailSimpleEditText.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("WHODO_LOG", "User email address updated.");
                    //desconectar al usuario
                    sendVerificationEmail();
                } else {
                    Toast.makeText(requireContext(), "We found a problem trying to update the email, please check your data and try again",Toast.LENGTH_SHORT).show();

                    Log.d("WHODO_LOG", "Error al actualizar el email " );
                }
            }
        });
    }
    private void sendVerificationEmail(){
        assert currentUser != null;
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireContext(), "Email Update Success. Please verify your Email",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // email not sent, so display message and restart the activity or do whatever you wish to do
                    Toast.makeText(getContext(), "We could not send you validation email, please rollback the update with the link we send you", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadUserData (User pUser) {
            mLoggedUser=pUser.deepCopy();
            if(mLoggedUser.getName()!=null) {
                setUserNameText(mLoggedUser.getName(), getString(R.string.PersonalInfoFrag_UserName1), item_UserName);
                UserNameSimpleEditText.setText(mLoggedUser.getName());
                setUserPhoneNumber(mLoggedUser.getPhone(), mLoggedUser.getPhone_ccn(), getString(R.string.PersonalInfoFrag_PhoneNumber1), item_PhoneNumber);
                PhoneNumberSimpleEditText.setText(mLoggedUser.getPhone());
                countryCodeSpinner.setSelection(setCCNIndex());
                setUserEmailText(mLoggedUser.getEmail(), getString(R.string.PersonalInfoFrag_Email1), item_Email);
                EmailSimpleEditText.setText(mLoggedUser.getEmail());
                String YEAR = String.valueOf(mLoggedUser.getBirthday()).substring(0, 4);
                String MONTH = String.valueOf(mLoggedUser.getBirthday()).substring(4, 6);
                String DAY = String.valueOf(mLoggedUser.getBirthday()).substring(6, 8);
                BirthdayCalendar.updateDate(Integer.parseInt(YEAR), Integer.parseInt(MONTH) - 1, Integer.parseInt(DAY));
            }
    }
    private void saveUserData (){
        model.getLoggedUser().removeObservers(requireActivity());
        model.getLanguages().removeObservers(requireActivity());
        model.getServices().removeObservers(requireActivity());

        mLoggedUser.setName(LoggedUserName);
        mLoggedUser.setPhone(LoggedUserPhoneNumber);
        mLoggedUser.setPhone_ccn(LoggedUserCCN);
        mLoggedUser.setEmail(LoggedUserEmail);
        mLoggedUser.setBirthday(Long.parseLong(SetBirthday()));
        model.updateLoggedUser(mLoggedUser);
        //model.TabLayoutVisibility(View.VISIBLE);
        model.setSelectedFragment(4,View.VISIBLE);
    }
    private String SetBirthday (){
        String CustomDate;
        String MONTH;
        String DAY;
        if (BirthdayCalendar.getDayOfMonth() <10){
            DAY = "0"+BirthdayCalendar.getDayOfMonth();
        } else {
            DAY = BirthdayCalendar.getDayOfMonth()+"";
        }
        if ((BirthdayCalendar.getMonth() + 1) <10) {
            MONTH = "0"+(BirthdayCalendar.getMonth()+1);
        } else {
            MONTH = (BirthdayCalendar.getMonth()+1)+"";
        }
        CustomDate = BirthdayCalendar.getYear() + MONTH + DAY + "";
        Log.i("CustomDate", "CustomDate: " + CustomDate);
        return CustomDate;
    }
    private int setCCNIndex(){
        int index=0;
        for(int i = 0; i< countryCodeSpinner.getCount(); i++) {
          if (countryCodeSpinner.getItemAtPosition(i).toString().equals(LoggedUserCCN))
          {index=i;}
        }
        return index;
    }
    private void setUserNameText(String UserName,String EmptyString, ProfileItem ProfileItem1){
        if ( UserName.trim().length() != 0  ) {
            ProfileItem1.setText(UserName);
                LoggedUserName = UserName;
        }
        else
        {
                LoggedUserName = mLoggedUser.getEmail();
                UserNameSimpleEditText.setText(LoggedUserName);
                ProfileItem1.setText(LoggedUserName);
        }
    }
    private void setUserEmailText(String Email,String EmptyString, ProfileItem ProfileItem1){
        if ( Email.trim().length() != 0  ) {
            LoggedUserEmail = Email;
            //Actualizar el email implica desconectar, por lo que nunca se reemplaza si cierra la ventana
            ProfileItem1.setText(LoggedUserEmail);
        }
        else
        {
                LoggedUserEmail = mLoggedUser.getEmail();
                ProfileItem1.setText(LoggedUserEmail);
        }
    }
    private void setUserPhoneNumber(String NIM,String CCN,String EmptyString, ProfileItem MenuItem){
        if ( NIM.trim().length() != 0 && !Objects.equals(CCN, "-")) {
           LoggedUserPhoneNumber = NIM;
           LoggedUserCCN=CCN;
           MenuItem.setText(CCN+" "+NIM);
        }
        else
        {
            MenuItem.setText(EmptyString);
            LoggedUserPhoneNumber="";
            LoggedUserCCN="-";
        }
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
