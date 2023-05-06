package com.example.whodo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.ui.activity.ActivityFragment;
import com.example.whodo.ui.favorites.FavoritesFragment;
import com.example.whodo.ui.hire.HireFragment;
import com.example.whodo.ui.messages.MessagesFragment;
import com.example.whodo.ui.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;



public class Main_ViewPagerAdapter extends FragmentStateAdapter {

    //private final String[] Titles = new String[5];

    public Main_ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {super(fragmentActivity);
    }

    //***************************************************************************
    // Estos metodos crean los fragmentos principales cuando se llama al constructor

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new HireFragment();
            case 1: return new FavoritesFragment();
            case 2: return new ActivityFragment();
            case 3: return new MessagesFragment();
            case 4: return new ProfileFragment();
        }

        //return new HireFragment();
        return null;
    }
    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return 5;
    }


}
