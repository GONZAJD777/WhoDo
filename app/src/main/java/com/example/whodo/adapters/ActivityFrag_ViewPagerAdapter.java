package com.example.whodo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.ViewPagerFragment;


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
            case 0: return new ViewPagerFragment(2);
            case 1: return new ViewPagerFragment(3);
            case 2: return new ViewPagerFragment(4);


            //adapter.addFrag(new ViewPagerFragment(1), "");
            //adapter.addFrag(new ViewPagerFragment(5), "Messages");
            //adapter.addFrag(new ViewPagerFragment(6), "Notifications");

        }

        return new ViewPagerFragment(2);
    }
    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return Titles.length;
    }


}
