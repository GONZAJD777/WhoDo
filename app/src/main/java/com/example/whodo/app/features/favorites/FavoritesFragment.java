package com.example.whodo.app.features.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.whodo.R;
import com.google.android.material.tabs.TabLayout;

public class FavoritesFragment extends Fragment {

    private static final String TAG = "TAG-1";
    private ViewPager2 Favorites_ViewPager;
    private TabLayout Favorites_TabLayout;
    private FavoritesFrag_ViewPagerAdapter Favorites_ViewPagerAdapterFrag;
    private final String[] Titles = new String[]{"Favoritos"};

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.act_main_frag_favorites, container, false);
        FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        TextView textView1 = root.findViewById(R.id.favorites_textView);
        textView1.setText("");
        textView1.setText(favoritesViewModel.getText());


        Favorites_ViewPager = root.findViewById(R.id.favorites_ViewPager);
        //Favorites_TabLayout= root.findViewById(R.id.activity_TabLayout);
        Favorites_ViewPagerAdapterFrag = new FavoritesFrag_ViewPagerAdapter(this);
        Favorites_ViewPager.setAdapter(Favorites_ViewPagerAdapterFrag);
        //new TabLayoutMediator(Favorites_TabLayout,Favorites_ViewPager,((tab, position) -> tab.setText(Titles[position]))).attach();
        return root;
    }


}
