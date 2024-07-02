package com.example.whodo.features.favorites;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whodo.aplication.ViewPagerFragment;


public class FavoritesFrag_ViewPagerAdapter extends FragmentStateAdapter {

    private final String[] Titles = new String[]{"Favoritos"};

    public FavoritesFrag_ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    //***************************************************************************
    // Estos metodos crean los fragmentos principales cuando se llama al constructor
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ViewPagerFragment(1);

            //adapter.addFrag(new ViewPagerFragment(5), "Messages");
            //adapter.addFrag(new ViewPagerFragment(6), "Notifications");
     }

    //cuenta la cantidad de items en el array Titles
    @Override
    public final int getItemCount() {
        return Titles.length;
    }


}
