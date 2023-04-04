package com.example.whodo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.ViewPagerFragment;


public class MessagesFrag_ViewPagerAdapter extends FragmentStateAdapter {

    private final String[] Titles = new String[]{"Messages","Notifications"};

    public MessagesFrag_ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    //***************************************************************************
    // Estos metodos crean los fragmentos principales cuando se llama al constructor
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ViewPagerFragment(5);
            case 1:
                return new ViewPagerFragment(6);

        }
        return new ViewPagerFragment(5);
    }
    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return Titles.length;
    }


}
