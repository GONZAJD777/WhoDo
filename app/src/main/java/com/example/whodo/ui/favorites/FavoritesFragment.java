package com.example.whodo.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.whodo.R;

public class FavoritesFragment extends Fragment {

    private static final String TAG = "TAG-1";
    private ViewPager2 Favorites_ViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        Favorites_ViewPager = root.findViewById(R.id.favorites_ViewPager);
        //setupViewPager(Favorites_ViewPager);

        //TextView textView1 = root.findViewById(R.id.favorites_textView);
        //textView1.setText("");
        //FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        //textView1.setText(favoritesViewModel.getText());
        return root;
    }


}
