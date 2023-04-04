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
import androidx.viewpager2.widget.ViewPager2;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String[] Titles = new String[5];
    private ViewPager2 ViewPager2;
    private TabLayout TabLayout;
    private Main_ViewPagerAdapter Main_ViewPagerAdapter;
    private static final String TAG = "TAG-1";
    private static final User LoggedUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);


        MainActivityViewModel model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        // update UI
        model.getLoggedUser().observe(this, MainActivity::UpdateLoggedUser);

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
                //Objects.requireNonNull(TabLayout.getTabAt(tab.getPosition())).setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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

        //this.Uid="";
        //this.Name=Name;
        //this.Birthday=Birthday;
        //this.Email=Email;
        //this.Address=Address;
        //this.Latitude=Latitude;
        //this.Longitude=Longitude;
        //this.Phone=Phone;
        //this.Type=Type;
        //this.Password=Password;
        //this.CreateDate=19700101;
        //this.DeleteDate=20991231;
        //this.State=1;
        //this.isValidated=0;

    }

}
