package com.example.whodo.features.login;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.features.login.RegisterUserFragment;
import com.example.whodo.features.login.SignInFragment;


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
            case 0: return new SignInFragment();
            case 1: return new RegisterUserFragment();

        }

        return new SignInFragment();
    }
    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return Titles.length;
    }


}
