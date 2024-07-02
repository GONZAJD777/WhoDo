package com.example.whodo.features.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.features.profile.fragments.CommentsFragment;
import com.example.whodo.features.profile.fragments.EditProfileFragment;
import com.example.whodo.features.profile.fragments.LegalTermsFragment;
import com.example.whodo.features.profile.fragments.PaymentsFragment;
import com.example.whodo.features.profile.fragments.PersonalInfoFragment;
import com.example.whodo.features.profile.fragments.PrivacyPoliticsFragment;
import com.example.whodo.features.profile.fragments.ProviderModeFragment;
import com.example.whodo.features.profile.fragments.RecomendationsFragment;
import com.example.whodo.features.profile.fragments.SecurityFragment;
import com.example.whodo.features.profile.fragments.SupportFragment;
import com.example.whodo.features.profile.fragments.TutorialFragment;

public class Profile_ViewPagerAdapter  extends FragmentStateAdapter {


    private final String[] Titles = new String[]{""};
    private final Integer Fragment ;

    public Profile_ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int p_fragmentId)  {
        super(fragmentActivity);
        Fragment=p_fragmentId;
    }


    //***************************************************************************
    // Estos metodos crean los fragmentos principales cuando se llama al constructor
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (Fragment)
        {
            case 0: return new EditProfileFragment();
            case 1: return new PersonalInfoFragment();
            case 2: return new SecurityFragment();
            case 3: return new PaymentsFragment();
            case 4: return new ProviderModeFragment();
            case 5: return new TutorialFragment();
            case 6: return new RecomendationsFragment();
            case 7: return new SupportFragment();
            case 8: return new CommentsFragment();
            case 9: return new LegalTermsFragment();
            case 10: return new PrivacyPoliticsFragment();


        }

        return null;
    }
    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return Titles.length;
    }
}
