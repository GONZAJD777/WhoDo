package com.example.whodo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.ViewPagerFragment;
import com.example.whodo.ui.profile.CommentsFragment;
import com.example.whodo.ui.profile.EditProfileFragment;
import com.example.whodo.ui.profile.LegalTermsFragment;
import com.example.whodo.ui.profile.PaymentsFragment;
import com.example.whodo.ui.profile.PersonalInfoFragment;
import com.example.whodo.ui.profile.PrivacyPoliticsFragment;
import com.example.whodo.ui.profile.ProviderModeFragment;
import com.example.whodo.ui.profile.RecomendationsFragment;
import com.example.whodo.ui.profile.SecurityFragment;
import com.example.whodo.ui.profile.SupportFragment;
import com.example.whodo.ui.profile.TutorialFragment;

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
