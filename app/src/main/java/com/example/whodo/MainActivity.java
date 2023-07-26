package com.example.whodo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.whodo.BusinessClasses.User;
import com.example.whodo.adapters.Main_ViewPagerAdapter;
import com.example.whodo.crud.CRUD;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String[] Titles = new String[5];
    private TabLayout TabLayout;
    private static final String TAG = "TAG-1";
    private static final SingletonUser LoggedUser  =SingletonUser.getInstance();
    private static final User LoggedUserSnapshot= new User();
    private static ArrayList<String> Services= new ArrayList<>();
    private static ArrayList<String> Languages= new ArrayList<>();
    private final CRUD BackgroundUpdateUser= new CRUD();
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final FirebaseUser currentUser =mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);


        MainActivityViewModel model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        model.getLoggedUserOnce().observe(this, MainActivity::UpdateLoggedUserOnce);
        model.getLoggedUser().observe(this, MainActivity::UpdateLoggedUserSnapshot);
        model.getServices().observe(this, MainActivity::UpdateServices);
        model.getLanguages().observe(this, MainActivity::UpdateLanguages);
        // update UI


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        androidx.viewpager2.widget.ViewPager2 viewPager2 = findViewById(R.id.Main_view_pager);
        viewPager2.setUserInputEnabled(false);
        TabLayout=findViewById(R.id.Main_TabLayout);

        com.example.whodo.adapters.Main_ViewPagerAdapter main_ViewPagerAdapter = new Main_ViewPagerAdapter(this);
        viewPager2.setAdapter(main_ViewPagerAdapter);

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
                    Log.i("StartUserUpdateThread()", "Ocurrio un error al llamar la funcion BackgroundUpdateUser.UpdateUser(LoggedUser): "+ e );
                }
                finally {
                    handler.postDelayed(this, 60000);
                }

            }
        }).start();
    }
    public static void UpdateServices(ArrayList<String> ArrayServices) { MainActivity.Services=ArrayServices; }
    public static void UpdateLanguages(ArrayList<String> ArrayLanguages) { MainActivity.Languages=ArrayLanguages; }
    public static ArrayList<String> getServices() {
        return MainActivity.Services;
    }
    public static ArrayList<String> getLanguages() {
        return MainActivity.Languages;
    }
    public static User getLoggedUser() { return LoggedUser; }
    public static User getLoggedUserSnapshot() { return LoggedUserSnapshot; }
    public static void UpdateLoggedUserSnapshot(User p_user){
            LoggedUserSnapshot.setUid(p_user.getUid());
            LoggedUserSnapshot.setName(p_user.getName());
            LoggedUserSnapshot.setBirthday(p_user.getBirthday());
            LoggedUserSnapshot.setEmail(p_user.getEmail());
            LoggedUserSnapshot.setAddress(p_user.getAddress());
            LoggedUserSnapshot.setLatitude(p_user.getLatitude());
            LoggedUserSnapshot.setLongitude(p_user.getLongitude());
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
            LoggedUserSnapshot.setWallet(p_user.getWallet());

    }
    private static void UpdateLoggedUserOnce(User p_user) {
        if (currentUser != null) {
            LoggedUser.setEmail(currentUser.getEmail());
            Log.i(TAG, "CARGANDO LOS DATOS DEL USUARIO LOGEADO " + currentUser.getEmail());
        } else {
            Log.i("UpdateLoggedUser()", "CURRENT USER WAS NULL!");
            LoggedUser.setEmail(p_user.getEmail());
            Log.i(TAG, "CARGANDO LOS DATOS DEL USUARIO LOGEADO " + p_user.getEmail());
        }
        LoggedUser.setUid(p_user.getUid());
        LoggedUser.setName(p_user.getName());
        LoggedUser.setBirthday(p_user.getBirthday());
        LoggedUser.setAddress(p_user.getAddress());
        LoggedUser.setLatitude(p_user.getLatitude());
        LoggedUser.setLongitude(p_user.getLongitude());
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
        LoggedUser.setWallet(p_user.getWallet());
    }


    }


