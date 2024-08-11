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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class ProviderModeFragment extends Fragment {
    private String LoggedUserSpecialization;
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
    private MainActivityViewModel model;
    private User mLoggedUser;
    private String TAG="PROVIDER-MODE-FRAGMENT";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_profile_frag_provider_mode, container, false);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
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
                    setSpecializationText(LoggedUserSpecialization.toString(),getString(R.string.ProviderModeFrag_Specialization_Tittle),item_Specialization);
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
        model.getLoggedUser().observe(requireActivity(),this::loadUserData);

        return root;
    }

    private void loadUserData(User pUser) {
        mLoggedUser=pUser.deepCopy();
        if(mLoggedUser.getName()!=null) {
            mProfileSwitchItem.setSwitchState(!Objects.equals(mLoggedUser.getType(), "1"));
            model.getServices().observe(requireActivity(),this::loadServicesCheckBox);
            LoggedUserSpecialization = mLoggedUser.getSpecialization();
            setSpecializationText(mLoggedUser.getSpecialization(), getString(R.string.ProviderModeFrag_Specialization_Tittle), item_Specialization);
        }
    }

    public void loadServicesCheckBox(@NonNull ArrayList<String> pServices){
        clearLinearLayout(SpecializationLinearLayout);
        //*//******************************************************************************
        //*//Se comienza a recorrer la lista con los servicio para agregar los botones
        for(int i = 0; i< pServices.size(); i++) {
            CheckBox ServiceCheckBox = new CheckBox(getContext());
            ServiceCheckBox.setText(pServices.get(i));
            //*//Se valida cuales estan checkeados por el usuario para filtrar
            if ( LoggedUserSpecialization.toUpperCase().contains(pServices.get(i).toUpperCase()))      {
                ServiceCheckBox.setChecked(true);
                Log.i(TAG, "loadServicesCheckBox --> Checkeando filtrado, el servicio esta tildado " + pServices.get(i) );
            }
            //Se configuran los listeners de cambio de estado antes de agregar los Checkbox
            ServiceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){//Si se tilda el checkbox -->
                        LoggedUserSpecialization=LoggedUserSpecialization+compoundButton.getText()+",";
                        Log.i(TAG, "loadServicesCheckBox --> Checkeando filtrado, el servicio fue tildado " + compoundButton.getText().toString() );
                    } else {

                        LoggedUserSpecialization=LoggedUserSpecialization.replace(compoundButton.getText()+",","" );
                        Log.i(TAG, "loadServicesCheckBox --> Checkeando filtrado, el servicio fue destildado " + compoundButton.getText().toString() );
                    }
                    Log.i(TAG, "loadServicesCheckBox --> Servicios Tildados: " + LoggedUserSpecialization);

                }
            });
            //Se agrega el checkbox al LinearLayout
            SpecializationLinearLayout.addView(ServiceCheckBox);
        }
    }

    public void loadServicesRadioButton(ArrayList<String> pServices){
        clearLinearLayout(SpecializationLinearLayout);
        RadioGroup RadioGroupFilter = new RadioGroup(requireContext());
        SpecializationLinearLayout.addView(RadioGroupFilter);

        //*//******************************************************************************
        //*//Se comienza a recorrer la lista con los servicio para agregar los botones
        //*//y validar cuales estan checkeados por el usuario para filtrar
        for(int i = 0; i< pServices.size(); i++) {
            RadioButton ServiceCheckBox = new RadioButton(requireContext());
            ServiceCheckBox.setText(pServices.get(i));
            //******************************************************************************
            //Se valida si el servicio del checkbox esta en la variable ServicePickerFilter
            //para aplicar el filtro
            //******************************************************************************
            ServiceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        LoggedUserSpecialization=compoundButton.getText().toString();
                    }
                }
            });
            //se agrega el Checkbox con la configuracion
            RadioGroupFilter.addView(ServiceCheckBox);
            ServiceCheckBox.setChecked(mLoggedUser.getSpecialization().toUpperCase().contains(pServices.get(i).toUpperCase()));
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
    private void saveUserData (){
        model.getLoggedUser().removeObservers(requireActivity());
        model.getLanguages().removeObservers(requireActivity());
        model.getServices().removeObservers(requireActivity());

        if(mProfileSwitchItem.getSwitchState()){
            LoggedUserType="2";
        } else {
            LoggedUserType="1";
        }
        mLoggedUser.setType(LoggedUserType);
        mLoggedUser.setSpecialization(LoggedUserSpecialization);

        model.updateLoggedUser(mLoggedUser);
        //model.TabLayoutVisibility(View.VISIBLE);
        model.setSelectedFragment(4,View.VISIBLE);
    }
    private void setSpecializationText(String text1,String text2,ProfileItem ProfileItem1){
        if ( text1.trim().length() != 0  ) {
            String regex = ",$";
            ProfileItem1.setText(text1.replaceAll(regex,""));
        }
        else
        {
            ProfileItem1.setText(text2);
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
