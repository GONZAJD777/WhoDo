package com.example.whodo;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.whodo.BusinessClasses.User;
import com.example.whodo.adapters.Main_ViewPagerAdapter;
import com.example.whodo.crud.CRUD;
import com.example.whodo.ui.hire.HireFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {
    private final String[] Titles = new String[5];
    private final String TAG = "MAIN-ACTIVITY";
    private TabLayout TabLayout;
    private static final SingletonUser LoggedUser=SingletonUser.getInstance();
    private static final User LoggedUserSnapshot=new User();
    private final CRUD BackgroundUpdateUser=new CRUD();
    private static final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static final FirebaseUser currentUser=mAuth.getCurrentUser();
    private MainActivityViewModel model;
    private static ArrayList<User> Providers;
    private static ArrayList<String> Services=new ArrayList<>();
    private static ArrayList<String> Languages=new ArrayList<>();
    private boolean ProvidersAdded;
    private boolean ServicesAdded;
    private ViewPager2 viewPager2;
    private Main_ViewPagerAdapter main_ViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        ProvidersAdded=false;
        ServicesAdded=false;
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        model.getLoggedUserOnce().observe(this, this::UpdateLoggedUserOnce);
        model.getLoggedUser().observe(this, this::UpdateLoggedUserSnapshot);
        model.getServices().observe(this, this::SetServices);
        model.getLanguages().observe(this, this::SetLanguages);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        viewPager2 = findViewById(R.id.Main_view_pager);
        viewPager2.setUserInputEnabled(false);
        main_ViewPagerAdapter = new Main_ViewPagerAdapter(this);
        viewPager2.setAdapter(main_ViewPagerAdapter);

        TabLayout=findViewById(R.id.Main_TabLayout);
        new TabLayoutMediator(TabLayout, viewPager2,((tab, position) -> tab.setText(Titles[position]))).attach();
        Objects.requireNonNull(TabLayout.getTabAt(0)).setIcon(R.drawable.ic_hire_black);
        Objects.requireNonNull(TabLayout.getTabAt(1)).setIcon(R.drawable.ic_favorites_black);
        Objects.requireNonNull(TabLayout.getTabAt(2)).setIcon(R.drawable.ic_activity_black);
        Objects.requireNonNull(TabLayout.getTabAt(3)).setIcon(R.drawable.ic_message_black);
        Objects.requireNonNull(TabLayout.getTabAt(4)).setIcon(R.drawable.ic_profile_black);
        Objects.requireNonNull(TabLayout.getTabAt(0)).setText("Contratar");
        TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                {
                   Objects.requireNonNull(TabLayout.getTabAt(0)).setText(R.string.MainAct_tab_title_hire);
                   Log.i(TAG, "onTabSelected --> Tab selected, text changed: "+tab.getPosition() );
                }
                if (tab.getPosition() == 1)
                {
                   Objects.requireNonNull(TabLayout.getTabAt(1)).setText(R.string.MainAct_tab_title_favorites);
                    Log.i(TAG, "onTabSelected --> Tab selected, text changed: "+tab.getPosition() );
                }
                if (tab.getPosition() == 2)
                {
                    Objects.requireNonNull(TabLayout.getTabAt(2)).setText(R.string.MainAct_tab_title_messages);
                    Log.i(TAG, "onTabSelected --> Tab selected, text changed: "+tab.getPosition() );
                }
                if (tab.getPosition() == 3)
                {
                    Objects.requireNonNull(TabLayout.getTabAt(3)).setText(R.string.MainAct_tab_title_activity);
                    Log.i(TAG, "onTabSelected --> Tab selected, text changed: "+tab.getPosition() );
                }
                if (tab.getPosition() == 4)
                {
                    Objects.requireNonNull(TabLayout.getTabAt(4)).setText(R.string.MainAct_tab_title_profile);
                    Log.i(TAG, "onTabSelected --> Tab selected, text changed: "+tab.getPosition() );
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(TabLayout.getTabAt(tab.getPosition())).setText("");
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }});

        StartUserUpdateThread();
    }
    private void StartUserUpdateThread (){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                try{
                    BackgroundUpdateUser.UpdateUser(LoggedUser,LoggedUserSnapshot);
                }
                catch (Exception e)
                {
                    Log.i(TAG, "StartUserUpdateThread --> Ocurrio un error al llamar la funcion BackgroundUpdateUser.UpdateUser(LoggedUser): "+ e );
                }
                finally {
                    handler.postDelayed(this, 60000);
                }
            }
        }).start();
    }


    public void setProviders(ArrayList<User> pProviders) {
        MainActivity.Providers=pProviders;
    }
    public static ArrayList<User> getProviders() {
        return MainActivity.Providers;
    }
    public void SetServices(ArrayList<String> ArrayServices) {
        MainActivity.Services=ArrayServices;
    }
    public static ArrayList<String> getServices() {
        return MainActivity.Services;
    }
    public void SetLanguages(ArrayList<String> ArrayLanguages) {
        MainActivity.Languages=ArrayLanguages;
    }
    public static ArrayList<String> getLanguages() {
        return MainActivity.Languages;
    }
    public User getLoggedUser() { return LoggedUser; }
    public User getLoggedUserSnapshot() { return LoggedUserSnapshot; }
    public void UpdateLoggedUserSnapshot(User p_user){
            LoggedUserSnapshot.setUid(p_user.getUid());
            LoggedUserSnapshot.setName(p_user.getName());
            LoggedUserSnapshot.setBirthday(p_user.getBirthday());
            LoggedUserSnapshot.setEmail(p_user.getEmail());
            LoggedUserSnapshot.setAddress(p_user.getAddress());
            LoggedUserSnapshot.setLatitude(p_user.getLatitude());
            LoggedUserSnapshot.setLongitude(p_user.getLongitude());
            LoggedUserSnapshot.setGeohash(p_user.getGeohash());
            LoggedUserSnapshot.setPhone(p_user.getPhone());
            LoggedUserSnapshot.setPhone_ccn(p_user.getPhone_ccn());
            LoggedUserSnapshot.setType(p_user.getType());
            LoggedUserSnapshot.setPassword(p_user.getPassword());
            LoggedUserSnapshot.setCreateDate(p_user.getCreateDate());
            LoggedUserSnapshot.setDeleteDate(p_user.getDeleteDate());
            LoggedUserSnapshot.setState(p_user.getState());
            LoggedUserSnapshot.setIsValidated(p_user.getIsValidated());
            LoggedUserSnapshot.setProfilePicture(p_user.getProfilePicture());
            LoggedUserSnapshot.setLanguages(p_user.getLanguages());
            LoggedUserSnapshot.setDescription(p_user.getDescription());
            LoggedUserSnapshot.setSpecialization(p_user.getSpecialization());

    }
    private void UpdateLoggedUserOnce(User p_user) {
        if (currentUser != null) {
            LoggedUser.setEmail(currentUser.getEmail());
            Log.i(TAG, "UpdateLoggedUserOnce --> CARGANDO LOS DATOS DEL USUARIO LOGEADO " + currentUser.getEmail());
        } else {
            Log.i(TAG, "UpdateLoggedUser --> CURRENT USER WAS NULL!");
            LoggedUser.setEmail(p_user.getEmail());
            Log.i(TAG, "UpdateLoggedUserOnce --> CARGANDO LOS DATOS DEL USUARIO LOGEADO " + p_user.getEmail());
        }
        LoggedUser.setUid(p_user.getUid());
        LoggedUser.setName(p_user.getName());
        LoggedUser.setBirthday(p_user.getBirthday());
        LoggedUser.setAddress(p_user.getAddress());
        LoggedUser.setLatitude(p_user.getLatitude());
        LoggedUser.setLongitude(p_user.getLongitude());
        LoggedUser.setGeohash(p_user.getGeohash());
        LoggedUser.setPhone(p_user.getPhone());
        LoggedUser.setPhone_ccn(p_user.getPhone_ccn());
        LoggedUser.setType(p_user.getType());
        LoggedUser.setPassword(p_user.getPassword());
        LoggedUser.setCreateDate(p_user.getCreateDate());
        LoggedUser.setDeleteDate(p_user.getDeleteDate());
        LoggedUser.setState(p_user.getState());
        LoggedUser.setIsValidated(1);
        LoggedUser.setProfilePicture(p_user.getProfilePicture());
        LoggedUser.setLanguages(p_user.getLanguages());
        LoggedUser.setDescription(p_user.getDescription());
        LoggedUser.setSpecialization(p_user.getSpecialization());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



}


