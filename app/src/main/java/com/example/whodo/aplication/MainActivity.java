package com.example.whodo.aplication;

import android.os.Bundle;
import android.view.View;
import com.example.whodo.R;
import com.example.whodo.domain.user.dao.FirebaseUserDAO;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  {
    private final String TAG = "MAIN-ACTIVITY";
    private MainActivityViewModel model;
    private TabLayout Main_TabLayout;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_main);

            FirebaseUserDAO FirebaseUserDao = new FirebaseUserDAO(); // Asegúrate de implementar esto
            MainActivityViewModelFactory factory = new MainActivityViewModelFactory(FirebaseUserDao);

            model = new ViewModelProvider(this,factory).get(MainActivityViewModel.class);
            model.getSelectedFragment().observe(this,this::setSelectedFragment);
            model.getFragmentVisibility().observe(this,this::setTabLayoutVisibility);

            Main_TabLayout=findViewById(R.id.Main_TabLayout);
            mFragmentManager = getSupportFragmentManager();
            Objects.requireNonNull(getSupportActionBar()).hide();

            Main_TabLayout.addTab(Main_TabLayout.newTab().setIcon(R.drawable.ic_hire_black).setText(R.string.MainAct_tab_title_hire));
            Main_TabLayout.addTab(Main_TabLayout.newTab().setIcon(R.drawable.ic_favorites_black).setText(R.string.MainAct_tab_title_favorites));
            Main_TabLayout.addTab(Main_TabLayout.newTab().setIcon(R.drawable.ic_activity_black).setText(R.string.MainAct_tab_title_activity));
            Main_TabLayout.addTab(Main_TabLayout.newTab().setIcon(R.drawable.ic_message_black).setText(R.string.MainAct_tab_title_messages));
            Main_TabLayout.addTab(Main_TabLayout.newTab().setIcon(R.drawable.ic_profile_black).setText(R.string.MainAct_tab_title_profile));

            Main_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        model.setSelectedFragment(tab.getPosition(), View.VISIBLE);
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
        }

        private void setTabLayoutVisibility(int pVisibility){
        Main_TabLayout.setVisibility(pVisibility);
        }

        private void setSelectedFragment(Fragment pFragment)
        {
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.FrameLayout, pFragment);
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            mFragmentTransaction.commit();
        }
}


