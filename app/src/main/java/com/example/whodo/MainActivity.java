package com.example.whodo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.whodo.adapters.Main_ViewPagerAdapter;
import com.example.whodo.ui.activity.ActivityFragment;
import com.example.whodo.ui.favorites.FavoritesFragment;
import com.example.whodo.ui.hire.HireFragment;
import com.example.whodo.ui.messages.MessagesFragment;
import com.example.whodo.ui.profile.ProfileFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String[] Titles = new String[5];
    //private final String[] Titles = new String[]{"","","","",""};
    ViewPager2 ViewPager2;
    TabLayout TabLayout;
    Main_ViewPagerAdapter Main_ViewPagerAdapter;
    private static final String TAG = "TAG-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ViewPager2=findViewById(R.id.Main_view_pager);
        ViewPager2.setUserInputEnabled(false);
        TabLayout=findViewById(R.id.Main_TabLayout);



        Main_ViewPagerAdapter = new Main_ViewPagerAdapter(this);
        ViewPager2.setAdapter(Main_ViewPagerAdapter);
        new TabLayoutMediator(TabLayout,ViewPager2,((tab, position) -> tab.setText(Titles[position]))).attach();
        Objects.requireNonNull(TabLayout.getTabAt(0)).setIcon(R.drawable.ic_hire_black);
        Objects.requireNonNull(TabLayout.getTabAt(1)).setIcon(R.drawable.ic_favorites_black);
        Objects.requireNonNull(TabLayout.getTabAt(2)).setIcon(R.drawable.ic_activity_black);
        Objects.requireNonNull(TabLayout.getTabAt(3)).setIcon(R.drawable.ic_message_black);
        Objects.requireNonNull(TabLayout.getTabAt(4)).setIcon(R.drawable.ic_profile_black);
        Objects.requireNonNull(TabLayout.getTabAt(0)).setText("Contratar");


        TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //do stuff here
                if (tab.getPosition() == 0)
                {
                   TabLayout.getTabAt(0).setText("Contratar");
                   Log.i(TAG, "Tab selected, text changed:"+tab.getPosition() );
                }
                if (tab.getPosition() == 1)
                {
                   TabLayout.getTabAt(1).setText("Favoritos");
                   Log.i(TAG, "Tab selected, text changed:"+tab.getPosition() );
                }
                if (tab.getPosition() == 2)
                {
                    TabLayout.getTabAt(2).setText("Actividad");
                    Log.i(TAG, "Tab selected, text changed:"+tab.getPosition() );
                }
                if (tab.getPosition() == 3)
                {
                    TabLayout.getTabAt(3).setText("Mensajes");
                    Log.i(TAG, "Tab selected, text changed:"+tab.getPosition() );
                }
                if (tab.getPosition() == 4)
                {
                    TabLayout.getTabAt(4).setText("Perfil");
                    Log.i(TAG, "Tab selected, text changed:"+tab.getPosition() );
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(TabLayout.getTabAt(tab.getPosition())).setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference mDatabaseReference = mDatabase.getReference();
        //User user = new User ("Paul", 23, "Paul@gmail.com","CHACABUCO 44",-31.4228, -62.1802,"3512043546","CUSTOMER");
        //mDatabaseReference = mDatabase.getReference (). child ("Users").child("1");
        //mDatabaseReference.setValue (user);



           }




}
