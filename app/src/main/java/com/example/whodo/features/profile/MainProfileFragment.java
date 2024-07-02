package com.example.whodo.features.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.aplication.MainActivityViewModel;
import com.example.whodo.domain.user.User;
import com.example.whodo.features.login.LoginActivity;
import com.example.whodo.R;
import com.example.whodo.uiClasses.ProfileItem;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


public class MainProfileFragment extends Fragment {


    private static final int PICK_IMAGE_REQUEST = 22;

    private TextView label_UserName;
    private ImageView Profile_Picture;
    private MainActivityViewModel model;

    @SuppressLint({"LongLogTag", "MissingInflatedId"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_main_frag_profile, container, false);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        Profile_Picture=root.findViewById(R.id.ProfilePicture);
        label_UserName = root.findViewById(R.id.text_profile1);
        LinearLayout linearLayout = root.findViewById(R.id.linearLayout);
        TextView label_AccountConfig = new TextView(getContext());
        label_AccountConfig.setText(getString(R.string.profileFrag_text_profile_Config));
        label_AccountConfig.setPadding(85, 50, 0, 0);
        //----------------------------------------------------------
        TextView label_EditProfile = root.findViewById(R.id.label_EditProfile);
        label_EditProfile.setOnClickListener(v -> {
            openFragment(5);
        });
        //----------------------------------------------------------
        ProfileItem item_Profile_PersonalInfo = new ProfileItem(getContext());
        item_Profile_PersonalInfo.setText(getString(R.string.profileFrag_ItemText_PersonalInfo));
        item_Profile_PersonalInfo.setImage(R.drawable.usuario_de_archivo_24_grueso);
        item_Profile_PersonalInfo.setOnClickListener(v -> {
            openFragment(6);
        });
        //----------------------------------------------------------
        ProfileItem item_Profile_SesionSecurity = new ProfileItem(getContext());
        item_Profile_SesionSecurity.setText(getString(R.string.profileFrag_ItemText_SesionSecurity));
        item_Profile_SesionSecurity.setImage(R.drawable.monedas_24);
        item_Profile_SesionSecurity.setOnClickListener(v -> {
            openFragment(7);
        });
        //----------------------------------------------------------
        TextView label_UserType = new TextView(getContext());
        label_UserType.setText(getString(R.string.profileFrag_text_profile_UserType));
        label_UserType.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        ProfileItem item_Profile_ProviderSettings = new ProfileItem(getContext());
        item_Profile_ProviderSettings.setText(getString(R.string.profileFrag_ItemText_ProviderSettings));
        item_Profile_ProviderSettings.setImage(R.drawable.actualizar_24);
        item_Profile_ProviderSettings.setOnClickListener(v -> {
            openFragment(8);
        });
        //----------------------------------------------------------
        TextView label_Asistency = new TextView(getContext());
        label_Asistency.setText(getString(R.string.profileFrag_text_profile_Asistency));
        label_Asistency.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        ProfileItem item_Profile_HowItWorks = new ProfileItem(getContext());
        item_Profile_HowItWorks.setText(getString(R.string.profileFrag_ItemText_HowItWorks));
        item_Profile_HowItWorks.setImage(R.drawable.interrogatorio_24);
        item_Profile_HowItWorks.setOnClickListener(v -> {
            openFragment(9);
        });

        //----------------------------------------------------------
        ProfileItem item_Profile_Recomendations = new ProfileItem(getContext());
        item_Profile_Recomendations.setText(getString(R.string.profileFrag_ItemText_Recomendations));
        item_Profile_Recomendations.setImage(R.drawable.exclamacion_24);
        item_Profile_Recomendations.setOnClickListener(v -> {
            openFragment(10);
        });
        //----------------------------------------------------------
        ProfileItem item_Profile_Support = new ProfileItem(getContext());
        item_Profile_Support.setText(getString(R.string.profileFrag_ItemText_Support));
        item_Profile_Support.setImage(R.drawable.auriculares_24);
        item_Profile_Support.setOnClickListener(v -> {
            openFragment(11);
        });
        //----------------------------------------------------------
        ProfileItem item_Profile_Comments = new ProfileItem(getContext());
        item_Profile_Comments.setText(getString(R.string.profileFrag_ItemText_Comments));
        item_Profile_Comments.setImage(R.drawable.sobre_24);
        item_Profile_Comments.setOnClickListener(v -> {
            openFragment(12);
        });
        //----------------------------------------------------------
        TextView label_Legal = new TextView(getContext());
        label_Legal.setText(getString(R.string.profileFrag_text_profile_Legal));
        label_Legal.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        ProfileItem item_Profile_LegalTerms = new ProfileItem(getContext());
        item_Profile_LegalTerms.setText(getString(R.string.profileFrag_ItemText_LegalTerms));
        item_Profile_LegalTerms.setImage(R.drawable.diploma_24);
        item_Profile_LegalTerms.setOnClickListener(v -> {
            openFragment(13);
        });
        //----------------------------------------------------------
        ProfileItem item_Profile_PrivacyPolitics = new ProfileItem(getContext());
        item_Profile_PrivacyPolitics.setText(getString(R.string.profileFrag_ItemText_PrivacyPolitics));
        item_Profile_PrivacyPolitics.setImage(R.drawable.banco_24);
        item_Profile_PrivacyPolitics.setOnClickListener(v -> {
            openFragment(14);

        });
        //----------------------------------------------------------
        TextView label_CloseSession = new TextView(getContext());
        SpannableString mitextoU = new SpannableString(getString(R.string.profileFrag_text_profile_CloseSession));
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        label_CloseSession.setText(mitextoU);
        label_CloseSession.setPadding(85, 85, 0, 0);
        label_CloseSession.setTypeface(label_CloseSession.getTypeface(), Typeface.BOLD);
        //----------------------------------------------------------

        TextView label_Version = new TextView(getContext());
        label_Version.setText(getString(R.string.profileFrag_text_profile_Version));
        label_Version.setPadding(0, 85, 0, 0);
        label_Version.setGravity(1);

        linearLayout.addView(label_AccountConfig);
        linearLayout.addView(item_Profile_PersonalInfo);
        linearLayout.addView(item_Profile_SesionSecurity);
        linearLayout.addView(label_UserType);
        linearLayout.addView(item_Profile_ProviderSettings);
        linearLayout.addView(label_Asistency);
        linearLayout.addView(item_Profile_HowItWorks);
        linearLayout.addView(item_Profile_Recomendations);
        linearLayout.addView(item_Profile_Support);
        linearLayout.addView(item_Profile_Comments);
        linearLayout.addView(label_Legal);
        linearLayout.addView(item_Profile_LegalTerms);
        linearLayout.addView(item_Profile_PrivacyPolitics);
        linearLayout.addView(label_CloseSession);
        linearLayout.addView(label_Version);

        label_CloseSession.setOnClickListener(v -> {LogoutUser();});

        Log.i("Operacion ProfileFragment.OnCreate()", "Se ejecuta OnCreate() ");

        model.getLoggedUser().observe(requireActivity(),this::loadUserData);
        return root;
    }

    private void openFragment(int pFragment){
        model.setSelectedFragment(pFragment,View.GONE);
       // model.TabLayoutVisibility(View.GONE);
    }

    private void loadUserData(User pUser) {
        if (pUser.getName()!=null) {
                label_UserName.setText(pUser.getName());
                Picasso.get().load(pUser.getProfilePicture()).into(Profile_Picture);
            }
        }

    @SuppressLint("LongLogTag")
    public void onResume() {
        super.onResume();
        model.getLoggedUser().observe(requireActivity(),this::loadUserData);
        Log.i("Operacion ProfileFragment.onResume()", "La informacion del usuario a cambiado");
    }

    private void LogoutUser (){
        //Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        requireActivity().startActivity(intent);
        FirebaseAuth.getInstance().signOut();
        requireActivity().finish();
    }




}