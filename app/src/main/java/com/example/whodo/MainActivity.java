package com.example.whodo;


import android.os.Bundle;
import android.util.Log;
import com.example.whodo.BusinessClasses.User;
import com.example.whodo.adapters.Main_ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String[] Titles = new String[5];
    private TabLayout TabLayout;
    private static final String TAG = "TAG-1";
    private static final User LoggedUser = new User();
    private static ArrayList<String> Services= new ArrayList<>();
    private static ArrayList<String> Languages= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);


        MainActivityViewModel model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        // update UI
        model.getLoggedUser().observe(this, MainActivity::UpdateLoggedUser);
        model.getServices().observe(this, MainActivity::UpdateServices);
        model.getLanguages().observe(this, MainActivity::UpdateLanguages);

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


    }

    private static void UpdateServices(ArrayList<String> ArrayServices) {
        MainActivity.Services=ArrayServices;
    }

    private static void UpdateLanguages(ArrayList<String> ArrayLanguages) {
        MainActivity.Languages=ArrayLanguages;
    }

    public static ArrayList<String> getServices() {
        return MainActivity.Services;
    }
    public static ArrayList<String> getLanguages() {
        return MainActivity.Languages;

    }


    public static User getLoggedUser() {
        return MainActivity.LoggedUser;

    }
    public static void UpdateLoggedUser(User p_user){
        Log.i(TAG, "CARGANDO LOS DATOS DEL USUARIO LOGEADO");
        MainActivity.LoggedUser.setUid(p_user.getUid());
        MainActivity.LoggedUser.setName(p_user.getName());
        MainActivity.LoggedUser.setBirthday(p_user.getBirthday());
        MainActivity.LoggedUser.setEmail(p_user.getEmail());
        MainActivity.LoggedUser.setAddress(p_user.getAddress());
        MainActivity.LoggedUser.setLatitude(p_user.getLatitude());
        MainActivity.LoggedUser.setLongitude(p_user.getLongitude());
        MainActivity.LoggedUser.setPhone(p_user.getPhone());
        MainActivity.LoggedUser.setType(p_user.getType());
        MainActivity.LoggedUser.setPassword(p_user.getPassword());
        MainActivity.LoggedUser.setCreateDate(p_user.getCreateDate());
        MainActivity.LoggedUser.setDeleteDate(p_user.getDeleteDate());
        MainActivity.LoggedUser.setState(p_user.getState());
        MainActivity.LoggedUser.setIsValidated(p_user.getIsValidated());
        MainActivity.LoggedUser.setProfilePicture(p_user.getProfilePicture());
        MainActivity.LoggedUser.setLanguages(p_user.getLanguages());
        MainActivity.LoggedUser.setDescription(p_user.getDescription());


    }

}
