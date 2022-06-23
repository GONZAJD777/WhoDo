package com.example.whodo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.ui.activity.ActivityFragment;
import com.example.whodo.ui.favorites.FavoritesFragment;
import com.example.whodo.ui.hire.HireFragment;
import com.example.whodo.ui.login.DataInputFragment;
import com.example.whodo.ui.login.MailInputFragment;
import com.example.whodo.ui.messages.MessagesFragment;
import com.example.whodo.ui.profile.ProfileFragment;


public class Login_ViewPagerAdapter extends FragmentStateAdapter {

    private final String[] Titles = new String[2];

    public Login_ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {super(fragmentActivity);
    }

    //***************************************************************************
    // Estos metodos crean los fragmentos principales cuando se llama al constructor
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new MailInputFragment();
            case 1: return new DataInputFragment();

        }

        return new HireFragment();
    }
    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return Titles.length;
    }


}
