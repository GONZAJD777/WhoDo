package com.example.whodo.app.features.profile.fragments;

import static com.example.whodo.app.features.profile.ProfileHolderActivity.hideKeyboard;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.R;
import com.example.whodo.app.features.profile.ProfileItem;
import com.example.whodo.app.features.profile.ProfileSwitchItem;
import com.example.whodo.app.resources.parameters.Parameter;
import com.example.whodo.app.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProviderModeFragment extends Fragment {
    private List<String> LoggedUserSpecialization = new ArrayList<>();
    private String LoggedUserType;
    private ProfileItem item_Specialization;
    private LinearLayout Specialization_bottom_sheet;
    private LinearLayout SpecializationLinearLayout;
    private LinearLayout ItemsLinearLayout;
    private LinearLayout BlackBackground_bottom_sheet;
    private BottomSheetBehavior<LinearLayout> SpecializationBottomSheetBehavior;
    private BottomSheetBehavior<LinearLayout> BlackBackgroundBottomSheetBehavior;
    private FloatingActionButton SaveChangesButton;
    private ProfileSwitchItem mProfileSwitchItem;
    private MainActivityViewModel mMainActivityViewModel;
    private User mLoggedUser;
    private String TAG="PROVIDER-MODE-FRAGMENT";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_profile_frag_provider_mode, container, false);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        ItemsLinearLayout = root.findViewById(R.id.ItemsLinearLayout);

        TextView ReadyLabelButtonSpecialization = root.findViewById(R.id.ReadyLabelButtonSpecialization);
        SaveChangesButton = root.findViewById(R.id.SaveChangesButton);

        ReadyLabelButtonSpecialization.setOnClickListener(this::onClick);
        SaveChangesButton.setOnClickListener(this::onClick);
        SpecializationLinearLayout = root.findViewById(R.id.SpecializationLinearLayout);
        Specialization_bottom_sheet = root.findViewById(R.id.Specialization_bottom_sheet);
        SpecializationBottomSheetBehavior = BottomSheetBehavior.from(Specialization_bottom_sheet);

        BlackBackground_bottom_sheet = root.findViewById(R.id.BlackBackground_bottom_sheet);
        BlackBackgroundBottomSheetBehavior = BottomSheetBehavior.from(BlackBackground_bottom_sheet);
        BlackBackgroundBottomSheetBehavior.setState(STATE_EXPANDED);
        BlackBackgroundBottomSheetBehavior.setDraggable(false);
        BlackBackground_bottom_sheet.setClickable(false);

        //----------------------------------------------------------
        //SPECIALIZATION
        TextView label_Specialization = new TextView(getContext());
        label_Specialization.setText(getString(R.string.ProviderModeFrag_Specialization_Tittle));
        label_Specialization.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        item_Specialization = new ProfileItem(getContext());
        item_Specialization.setText(getString(R.string.PersonalInfoFrag_Description1));
        item_Specialization.setImage(R.drawable.mas_24);
        item_Specialization.setOnClickListener(v -> {
            setBottomSheetBehavior(SpecializationBottomSheetBehavior, 0);
            BlackBackground_bottom_sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSpecializationText(LoggedUserSpecialization,getString(R.string.ProviderModeFrag_Specialization_Tittle),item_Specialization);
                    setBottomSheetBehavior(SpecializationBottomSheetBehavior, 1);
                }
            });
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.PersonalInfoFrag_Description), Toast.LENGTH_LONG).show();
        });
        mProfileSwitchItem= new ProfileSwitchItem(getContext());
        //mProfileSwitchItem.setSwitchState(true);
        //----------------------------------------------------------
        ItemsLinearLayout.addView(mProfileSwitchItem);
        ItemsLinearLayout.addView(label_Specialization);
        ItemsLinearLayout.addView(item_Specialization);

        //model.getServices().observe(requireActivity(),this::loadServicesCheckBox);
        mMainActivityViewModel.getLoggedUser().observe(requireActivity(),this::loadUserData);

        return root;
    }

    private void loadUserData(User pUser) {
        mLoggedUser=pUser.deepCopy();
        if(mLoggedUser.getName()!=null) {
            mProfileSwitchItem.setSwitchState(!Objects.equals(mLoggedUser.getType(), 1));
            mMainActivityViewModel.getParameters().observe(requireActivity(),this::loadServicesCheckBox);

            LoggedUserSpecialization = mLoggedUser.getSpecialization();
            setSpecializationText(LoggedUserSpecialization, getString(R.string.ProviderModeFrag_Specialization_Tittle), item_Specialization);
            Log.i(TAG, "LoggedUserLanguages -->" + LoggedUserSpecialization );

        }
    }

    private void saveUserData (){
        mMainActivityViewModel.getLoggedUser().removeObservers(requireActivity());
        mMainActivityViewModel.getParameters().removeObservers(requireActivity());

        if(mProfileSwitchItem.getSwitchState()){
            LoggedUserType="2";
        } else {
            LoggedUserType="1";
        }
        mLoggedUser.setType(Integer.valueOf(LoggedUserType));

        mLoggedUser.setSpecialization(LoggedUserSpecialization);
        Log.i(TAG, "mLoggedUser.getSpecialization() -->" + mLoggedUser.getSpecialization().toString() );
        Log.i(TAG, "LoggedUserSpecialization -->" + LoggedUserSpecialization );

        mMainActivityViewModel.updateLoggedUser(mLoggedUser);
        //model.TabLayoutVisibility(View.VISIBLE);
        mMainActivityViewModel.setSelectedFragment(4,View.VISIBLE);
    }



    public void loadServicesCheckBox(@NonNull List<Parameter> pParameters) {
        clearLinearLayout(SpecializationLinearLayout);
        List<String> pServices = Utils.extractListFromParameters(pParameters, "SERVICES");

        for (String service : pServices) {
            CheckBox serviceCheckBox = new CheckBox(getContext());
            serviceCheckBox.setText(service);

            // Verifica si el servicio está en la lista y marca el CheckBox
            if (LoggedUserSpecialization.contains(service)) {
                serviceCheckBox.setChecked(true);
                Log.i(TAG, "loadServicesCheckBox --> El servicio está tildado: " + service);
            }

            serviceCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                String selectedService = compoundButton.getText().toString();
                if (isChecked) {
                    if (!LoggedUserSpecialization.contains(selectedService)) {
                        LoggedUserSpecialization.add(selectedService);
                    }
                } else {
                    LoggedUserSpecialization.remove(selectedService);
                }
                Log.i(TAG, "loadServicesCheckBox --> Servicios Tildados: " + LoggedUserSpecialization);
            });

            SpecializationLinearLayout.addView(serviceCheckBox);
        }
    }


    public void clearLinearLayout(LinearLayout LL){
        try {
            if (LL.getChildCount() > 0) {
                LL.removeAllViewsInLayout();
                Log.i("ReelItemsLinearLayout", "Removed all views");
            }
        }
        catch (Exception e)
        {
            Log.i("ReelItemsLinearLayout", "Has no childs:" + e);
        }
    }

    private void setSpecializationText(List<String> list1,String text2,ProfileItem ProfileItem1){
        if ( list1.isEmpty()) {
            ProfileItem1.setText(text2);
        }
        else
        {
            String regex = ",$";
            ProfileItem1.setText(list1.toString().replaceAll("^\\[|\\]$", ""));

        }

    }
    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.ReadyLabelButtonSpecialization:
                setSpecializationText(LoggedUserSpecialization,getString(R.string.ProviderModeFrag_Specialization_Tittle),item_Specialization);
                setBottomSheetBehavior(SpecializationBottomSheetBehavior, 1);
                break;
            case R.id.SaveChangesButton:
                saveUserData();
                break;
        }

    }
    private void setBottomSheetBehavior(BottomSheetBehavior<LinearLayout> mBottomSheetBehavior, Integer mState) {
        if (mState == 0) {
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_EXPANDED);
            BlackBackground_bottom_sheet.setClickable(true);
            BlackBackground_bottom_sheet.setAlpha(0.25F);

        } else {
            mBottomSheetBehavior.setHideable(true);
            mBottomSheetBehavior.setState(STATE_HIDDEN);
            BlackBackground_bottom_sheet.setClickable(false);
            BlackBackground_bottom_sheet.setAlpha(0);
            hideKeyboard(requireActivity());
        }
    }

}
