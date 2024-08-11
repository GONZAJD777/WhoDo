package com.example.whodo.app.features.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ActivityFrag_ViewPagerAdapter extends FragmentStateAdapter {

    private final String[] Titles = new String[]{"Pendientes","Finalizadas","Canceladas"};

    public ActivityFrag_ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    //***************************************************************************
    // Estos metodos crean los fragmentos principales cuando se llama al constructor
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new ActivityFrag_ViewPager(0);
            case 1: return new ActivityFrag_ViewPager(1);
            case 2: return new ActivityFrag_ViewPager(2);
        }

        return new ActivityFrag_ViewPager(2);
    }
    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return Titles.length;
    }


}
