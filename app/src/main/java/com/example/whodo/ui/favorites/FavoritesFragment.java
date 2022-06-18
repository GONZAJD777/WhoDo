package com.example.whodo.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.whodo.R;
import com.example.whodo.adapters.ViewPagerAdapter;
import com.example.whodo.ui.ViewPagerFragment;

public class FavoritesFragment extends Fragment {

    private static final String TAG = "TAG-1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        ViewPager favorites_ViewPager = root.findViewById(R.id.favorites_ViewPager);
        setupViewPager(favorites_ViewPager);

        TextView textView1 = root.findViewById(R.id.favorites_textView);
        textView1.setText(favoritesViewModel.getText());
        return root;
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ViewPagerFragment(1), "");

        Log.d(TAG, "MessagesFragment creando fragmentos");
        viewPager.setAdapter(adapter);
    }
}
