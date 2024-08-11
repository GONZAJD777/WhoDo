package com.example.whodo.app;

import android.os.Bundle;
import android.view.View;
import com.example.whodo.R;
import com.example.whodo.app.resources.images.ImageManager;
import com.example.whodo.app.resources.images.ImagesViewModel;
import com.example.whodo.app.domain.user.dao.FirebaseUserDAO;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  {
    private final String TAG = "MAIN-ACTIVITY";
    private MainActivityViewModel mMainActivityViewModel;
    private TabLayout Main_TabLayout;
    private FragmentManager mFragmentManager;
    private ImagesViewModel mImagesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);


        FirebaseUserDAO FirebaseUserDao = new FirebaseUserDAO(); // Asegúrate de implementar esto
        MainActivityViewModelFactory factory = new MainActivityViewModelFactory(FirebaseUserDao);


        mMainActivityViewModel = new ViewModelProvider(this,factory).get(MainActivityViewModel.class);
        mMainActivityViewModel.getSelectedFragment().observe(this,this::setSelectedFragment);
        mMainActivityViewModel.getFragmentVisibility().observe(this,this::setTabLayoutVisibility);

        mImagesViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);
        mImagesViewModel.getMapIconList().observe(this,this::LoadImages);

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
                        mMainActivityViewModel.setSelectedFragment(tab.getPosition(), View.VISIBLE);
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
        }

        private void LoadImages(List<String> mapIconList){
            List<String> mMissingMapIcons = ImageManager.checkMissingMapIcons(mapIconList, this);
            if(!mMissingMapIcons.isEmpty()){
                mImagesViewModel.getMapIcons().observe(this,mMapIcons -> {
                    ImageManager.loadMapIcons(mMapIcons, this, new Callback<List<String>>() {
                        @Override
                        public void onSuccess(List<String> pMapIconNameList) {
                            mImagesViewModel.setStorageLoadedMapIcons(pMapIconNameList);
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });
                });
                mImagesViewModel.setMapIcons(mMissingMapIcons);
            }

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


