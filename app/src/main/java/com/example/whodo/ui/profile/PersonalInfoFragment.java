package com.example.whodo.ui.profile;

import static com.example.whodo.MainActivity.getLoggedUser;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.whodo.MainActivity;
import com.example.whodo.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonalInfoFragment extends Fragment {

    private String LoggedUserName;
    private String LoggedUserPhoneNumber;
    private String LoggedUserEmail;
    private ProfileItem item_UserName;
    private ProfileItem item_PhoneNumber;
    private ProfileItem item_Email;
    private EditText UserNameSimpleEditText;
    private EditText PhoneNumberSimpleEditText;
    private EditText EmailSimpleEditText;
    private LinearLayout BlackBackground_bottom_sheet;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_profile_frag_personal_info, container, false);

        UserNameSimpleEditText = root.findViewById(R.id.UserNameSimpleEditText);
        PhoneNumberSimpleEditText = root.findViewById(R.id.PhoneNumberSimpleEditText);
        EmailSimpleEditText = root.findViewById(R.id.EmailSimpleEditText);
        LinearLayout linearLayout = root.findViewById(R.id.linearLayout);
        LinearLayout UserName_bottom_sheet = root.findViewById(R.id.UserName_bottom_sheet);
        LinearLayout PhoneNumber_bottom_sheet = root.findViewById(R.id.PhoneNumber_bottom_sheet);
        LinearLayout Email_bottom_sheet = root.findViewById(R.id.Email_bottom_sheet);
        BlackBackground_bottom_sheet = root.findViewById(R.id.BlackBackground_bottom_sheet);
        BottomSheetBehavior<LinearLayout> UserNameBottomSheetBehavior = BottomSheetBehavior.from(UserName_bottom_sheet);
        BottomSheetBehavior<LinearLayout> PhoneBottomSheetBehavior = BottomSheetBehavior.from(PhoneNumber_bottom_sheet);
        BottomSheetBehavior<LinearLayout> EmailBottomSheetBehavior = BottomSheetBehavior.from(Email_bottom_sheet);
        BottomSheetBehavior<LinearLayout> BlackBackgroundBottomSheetBehavior = BottomSheetBehavior.from(BlackBackground_bottom_sheet);
        UserNameBottomSheetBehavior.setFitToContents(true);
        PhoneBottomSheetBehavior.setFitToContents(true);
        EmailBottomSheetBehavior.setFitToContents(true);
        BlackBackgroundBottomSheetBehavior.setState(STATE_EXPANDED);
        BlackBackgroundBottomSheetBehavior.setDraggable(false);
        BlackBackground_bottom_sheet.setClickable(false);

        //----------------------------------------------------------
        //USERNAME
        TextView label_UserName = new TextView(getContext());
        label_UserName.setText(getString(R.string.PersonalInfoFrag_UserName));
        label_UserName.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_UserName = new ProfileItem(getContext());
        item_UserName.setText(getString(R.string.PersonalInfoFrag_UserName));
        item_UserName.setImage(R.drawable.ic_activity_black);
        item_UserName.setOnClickListener(v -> {
            setBottomSheetBehavior(UserNameBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserInfoText(UserNameSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_UserName1),item_UserName);
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
                        Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                        Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        setUserInfoText(UserNameSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_UserName1),item_UserName);
                        setBottomSheetBehavior(UserNameBottomSheetBehavior,1);
                        break;
                    case STATE_HIDDEN:
                        Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        setUserInfoText(UserNameSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_UserName1),item_UserName);
                        setBottomSheetBehavior(UserNameBottomSheetBehavior,1);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
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
        item_PhoneNumber.setImage(R.drawable.auriculares_24);
        item_PhoneNumber.setOnClickListener(v -> {
            setBottomSheetBehavior(PhoneBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserInfoText(PhoneNumberSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_PhoneNumber1),item_PhoneNumber);
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
                        Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                        Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        setUserInfoText(PhoneNumberSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_PhoneNumber1),item_PhoneNumber);
                        setBottomSheetBehavior(PhoneBottomSheetBehavior,1);
                        break;
                    case STATE_HIDDEN:
                        Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        setUserInfoText(PhoneNumberSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_PhoneNumber1),item_PhoneNumber);
                        setBottomSheetBehavior(PhoneBottomSheetBehavior,1);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        //----------------------------------------------------------
        //EMAIL
        TextView label_Email = new TextView(getContext());
        label_Email.setText(getString(R.string.PersonalInfoFrag_Email));
        label_Email.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Email = new ProfileItem(getContext());
        item_Email.setText(getString(R.string.PersonalInfoFrag_Email));
        item_Email.setImage(R.drawable.camara_24);
        item_Email.setOnClickListener(v -> {
            setBottomSheetBehavior(EmailBottomSheetBehavior,0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserInfoText(EmailSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Email1),item_Email);
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
                        Log.i("BottomSheetBehavior", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetBehavior", "STATE_DRAGGING");
                        break;
                    case STATE_COLLAPSED:
                        Log.i("BottomSheetBehavior", "STATE_COLLAPSED");
                        setUserInfoText(EmailSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Email1),item_Email);
                        setBottomSheetBehavior(EmailBottomSheetBehavior,1);
                        break;
                    case STATE_HIDDEN:
                        Log.i("BottomSheetBehavior", "STATE_HIDDEN");
                        setUserInfoText(EmailSimpleEditText.getText().toString(),getString(R.string.PersonalInfoFrag_Email1),item_Email);
                        setBottomSheetBehavior(EmailBottomSheetBehavior,1);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetBehavior", "STATE_SETTLING");
                        break;
                    case STATE_HALF_EXPANDED:
                        Log.i("BottomSheetBehavior", "STATE_HALF_EXPANDED");
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        //----------------------------------------------------------
        linearLayout.addView(label_UserName);
        linearLayout.addView(item_UserName);
        linearLayout.addView(label_PhoneNumber);
        linearLayout.addView(item_PhoneNumber);
        linearLayout.addView(label_Email);
        linearLayout.addView(item_Email);

        FloatingActionButton SaveChangesButton = root.findViewById(R.id.SaveChangesButton);
        SaveChangesButton.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG).show();
            saveUserData();
        });

        loadUserData();
        return root;
    }

    private void loadUserData () {

        setUserInfoText(MainActivity.getLoggedUser().getName(),getString(R.string.PersonalInfoFrag_UserName1),item_UserName);
        UserNameSimpleEditText.setText(MainActivity.getLoggedUser().getName());
        setUserInfoText(MainActivity.getLoggedUser().getPhone(),getString(R.string.PersonalInfoFrag_PhoneNumber1),item_PhoneNumber);
        PhoneNumberSimpleEditText.setText(MainActivity.getLoggedUser().getPhone());
        setUserInfoText(MainActivity.getLoggedUser().getEmail(),getString(R.string.PersonalInfoFrag_Email1),item_Email);
        EmailSimpleEditText.setText(MainActivity.getLoggedUser().getEmail());

    }
    private void saveUserData (){

        MainActivity.getLoggedUser().setName(LoggedUserName);
        MainActivity.getLoggedUser().setPhone(LoggedUserPhoneNumber);
        MainActivity.getLoggedUser().setEmail(LoggedUserEmail);

        requireActivity().finish();
    }

    private void setUserInfoText(String text1,String text2,ProfileItem ProfileItem1){
        if ( text1.trim().length() != 0  ) {
            ProfileItem1.setText(text1);
            if (item_UserName.equals(ProfileItem1)) {
                LoggedUserName = text1;
            } else if (item_PhoneNumber.equals(ProfileItem1)) {
                LoggedUserPhoneNumber = text1;
            } else if (item_Email.equals(ProfileItem1)) {
                LoggedUserEmail = text1;
            }
        }
        else
        {
            ProfileItem1.setText(text2);
            LoggedUserEmail="";
        }

    }
    private void setBottomSheetBehavior (BottomSheetBehavior<LinearLayout> mBottomSheetBehavior, Integer mState){
        if (mState==0){
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_EXPANDED);
            BlackBackground_bottom_sheet.setClickable(true);
            BlackBackground_bottom_sheet.setAlpha(0.25F);

        } else {
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_HIDDEN);
            BlackBackground_bottom_sheet.setClickable(false);
            BlackBackground_bottom_sheet.setAlpha(0);
        }
    }





}
