package com.example.whodo.ui.profile;

import static com.example.whodo.MainActivity.getLoggedUser;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.whodo.LoginActivity;
import com.example.whodo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {


    private static final int PICK_IMAGE_REQUEST = 22;

    private TextView label_UserName;
    private ImageView Profile_Picture;

    @SuppressLint({"LongLogTag", "MissingInflatedId"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_main_frag_profile, container, false);

        //---------- Edit profile Activity instatiation --------------
        TextView label_EditProfile = root.findViewById(R.id.label_EditProfile);
        label_EditProfile.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG).show();
            profileActivityIntent(0,getString(R.string.profileFrag_text_profile_EditProfile));
            });
        //---------- Edit profile Activity instatiation --------------

        Profile_Picture=root.findViewById(R.id.ProfilePicture);
        label_UserName = root.findViewById(R.id.text_profile1);
        label_UserName.setText(getLoggedUser().getName());

        Picasso.get().load(getLoggedUser().getProfilePicture()).into(Profile_Picture);
        LinearLayout linearLayout = root.findViewById(R.id.linearLayout);

        TextView label_AccountConfig = new TextView(getContext());
        label_AccountConfig.setText(getString(R.string.profileFrag_text_profile_Config));
        label_AccountConfig.setPadding(85, 50, 0, 0);
        //----------------------------------------------------------
        ProfileItem item_Profile_PersonalInfo = new ProfileItem(getContext());
        item_Profile_PersonalInfo.setText(getString(R.string.profileFrag_ItemText_PersonalInfo));
        item_Profile_PersonalInfo.setImage(R.drawable.ic_activity_black);
        item_Profile_PersonalInfo.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_PersonalInfo), Toast.LENGTH_LONG).show();
            profileActivityIntent(1,getString(R.string.profileFrag_ItemText_PersonalInfo));
        });
        //----------------------------------------------------------
        ProfileItem item_Profile_SesionSecurity = new ProfileItem(getContext());
        item_Profile_SesionSecurity.setText(getString(R.string.profileFrag_ItemText_SesionSecurity));
        //item_Profile_SesionSecurity.setImage(R.drawable.);
        item_Profile_SesionSecurity.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_SesionSecurity), Toast.LENGTH_LONG).show();
            profileActivityIntent(2,getString(R.string.profileFrag_ItemText_SesionSecurity));
        });
        //----------------------------------------------------------
        ProfileItem item_Profile_Payment = new ProfileItem(getContext());
        item_Profile_Payment.setText(getString(R.string.profileFrag_ItemText_Payment));
        //item_Profile_Payment.setImage(R.drawable.);
        item_Profile_Payment.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_Payment), Toast.LENGTH_LONG).show();
            profileActivityIntent(3,getString(R.string.profileFrag_ItemText_Payment));
        });

        //----------------------------------------------------------
        TextView label_UserType = new TextView(getContext());
        label_UserType.setText(getString(R.string.profileFrag_text_profile_UserType));
        label_UserType.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        ProfileItem item_Profile_ProviderSettings = new ProfileItem(getContext());
        item_Profile_ProviderSettings.setText(getString(R.string.profileFrag_ItemText_ProviderSettings));
        //item_Profile_ProviderSettings.setImage(R.drawable.);
        item_Profile_ProviderSettings.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_ProviderSettings), Toast.LENGTH_LONG).show();
            profileActivityIntent(4,getString(R.string.profileFrag_ItemText_ProviderSettings));
        });

        //----------------------------------------------------------
        TextView label_Asistency = new TextView(getContext());
        label_Asistency.setText(getString(R.string.profileFrag_text_profile_Asistency));
        label_Asistency.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        ProfileItem item_Profile_HowItWorks = new ProfileItem(getContext());
        item_Profile_HowItWorks.setText(getString(R.string.profileFrag_ItemText_HowItWorks));
        //item_Profile_HowItWorks.setImage(R.drawable.);
        item_Profile_HowItWorks.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_HowItWorks), Toast.LENGTH_LONG).show();
            profileActivityIntent(5,getString(R.string.profileFrag_ItemText_HowItWorks));
        });

        //----------------------------------------------------------
        ProfileItem item_Profile_Recomendations = new ProfileItem(getContext());
        item_Profile_Recomendations.setText(getString(R.string.profileFrag_ItemText_Recomendations));
        //item_Profile_Recomendations.setImage(R.drawable.);
        item_Profile_Recomendations.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_Recomendations), Toast.LENGTH_LONG).show();
            profileActivityIntent(6,getString(R.string.profileFrag_ItemText_Recomendations));
        });

        //----------------------------------------------------------
        ProfileItem item_Profile_Support = new ProfileItem(getContext());
        item_Profile_Support.setText(getString(R.string.profileFrag_ItemText_Support));
        //item_Profile_Support.setImage(R.drawable.);
        item_Profile_Support.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_Support), Toast.LENGTH_LONG).show();
            profileActivityIntent(7,getString(R.string.profileFrag_ItemText_Support));
        });

        //----------------------------------------------------------
        ProfileItem item_Profile_Comments = new ProfileItem(getContext());
        item_Profile_Comments.setText(getString(R.string.profileFrag_ItemText_Comments));
        //item_Profile_Comments.setImage(R.drawable.);
        item_Profile_Comments.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_Comments), Toast.LENGTH_LONG).show();
            profileActivityIntent(8,getString(R.string.profileFrag_ItemText_Comments));
        });

        //----------------------------------------------------------
        TextView label_Legal = new TextView(getContext());
        label_Legal.setText(getString(R.string.profileFrag_text_profile_Legal));
        label_Legal.setPadding(85, 85, 0, 0);
        //----------------------------------------------------------
        ProfileItem item_Profile_LegalTerms = new ProfileItem(getContext());
        item_Profile_LegalTerms.setText(getString(R.string.profileFrag_ItemText_LegalTerms));
        //item_Profile_LegalTerms.setImage(R.drawable.);
        item_Profile_LegalTerms.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_LegalTerms), Toast.LENGTH_LONG).show();
            profileActivityIntent(9,getString(R.string.profileFrag_ItemText_LegalTerms));
        });

        //----------------------------------------------------------
        ProfileItem item_Profile_PrivacyPolitics = new ProfileItem(getContext());
        item_Profile_PrivacyPolitics.setText(getString(R.string.profileFrag_ItemText_PrivacyPolitics));
        //item_Profile_PrivacyPolitics.setImage(R.drawable.);
        item_Profile_PrivacyPolitics.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Presionaste: " + getString(R.string.profileFrag_ItemText_PrivacyPolitics), Toast.LENGTH_LONG).show();
            profileActivityIntent(10,getString(R.string.profileFrag_ItemText_PrivacyPolitics));
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
        linearLayout.addView(item_Profile_Payment);
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

        label_CloseSession.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            requireActivity().startActivity(intent);
            FirebaseAuth.getInstance().signOut();
            requireActivity().finish();
        });

        Log.i("Operacion ProfileFragment.OnCreate()", "Se ejecuta OnCreate() ");

        return root;
    }

    @SuppressLint("LongLogTag")
    public void onResume() {
        super.onResume();
        label_UserName.setText(getLoggedUser().getName());
        Picasso.get().load(getLoggedUser().getProfilePicture()).into(Profile_Picture);
        Log.i("Operacion ProfileFragment.onResume()", "La informacion del usuario a cambiado");
    }

    public void profileActivityIntent(int p_fragmentId, String p_fragmentTitle)
    {
        Intent intent = new Intent(requireActivity(), ProfileActivity.class);
        Bundle b = new Bundle();
        b.putInt("FragmentId",p_fragmentId);
        b.putString("FragmentTitle",p_fragmentTitle);
        intent.putExtras(b); //Put your id to your next Intent
        requireActivity().startActivity(intent);
    }





}