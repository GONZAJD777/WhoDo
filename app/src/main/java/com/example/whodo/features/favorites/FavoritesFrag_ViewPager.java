package com.example.whodo.features.favorites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.aplication.MainActivityViewModel;

public class FavoritesFrag_ViewPager extends Fragment {

    private Integer FragType=0;
    private LinearLayout LinearLayoutItems;
    private View ItemFavoritesItem;
    private MainActivityViewModel mMainActivityViewModel;


    public FavoritesFrag_ViewPager(){}
    public FavoritesFrag_ViewPager(Integer Ft) {
        FragType=Ft;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_viewpager, container, false);
        LinearLayoutItems = root.findViewById(R.id.viewPager_linearLayout);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        ItemFavoritesItem = inflater.inflate(R.layout.item_favorites,container,false);

        addFragments();
        return root;
    }

    private void addFragments() {
        LinearLayoutItems.removeAllViews();
        if (FragType==0) {
            addFavoritesItem ();
        }
    }
    @SuppressLint("SetTextI18n")
    private void addFavoritesItem (){
        TextView textView_Name = ItemFavoritesItem.findViewById(R.id.textView_Name);
        TextView textView_Reviews = ItemFavoritesItem.findViewById(R.id.textView_Reviews);
        TextView textView_PricePercent = ItemFavoritesItem.findViewById(R.id.textView_PricePercent);
        TextView textView_Speed = ItemFavoritesItem.findViewById(R.id.textView_Speed);
        TextView textView_Spec = ItemFavoritesItem.findViewById(R.id.textView_Spec);
        Log.d("addFavoritesItem", "MessageItem agregar item");
        textView_Name.setText("Ricardo Fleitas");
        textView_Reviews.setText("4.3 (135)");
        textView_PricePercent.setText("1500 (4%)");
        textView_Speed.setText("6.5 dias (103)");
        textView_Spec.setText("ELECTRICIDAD/PLOMERIA");
        LinearLayoutItems.addView(ItemFavoritesItem);

    }

}
