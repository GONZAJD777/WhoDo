package com.example.whodo;

import android.os.Bundle;

import com.example.whodo.adapters.Main_ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private final String[] Titles = new String[]{"Contratar","Favoritos","Actividad","Mensajes","Perfil"};
    ViewPager2 ViewPager2;
    TabLayout TabLayout;
    Main_ViewPagerAdapter ViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ViewPager2=findViewById(R.id.Main_view_pager);
        TabLayout=findViewById(R.id.Main_TabLayout);
        ViewPagerAdapter = new Main_ViewPagerAdapter(this);

        ViewPager2.setAdapter(ViewPagerAdapter);

        new TabLayoutMediator(TabLayout,ViewPager2,((tab, position) -> tab.setText(Titles[position]))).attach();

        //FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference mDatabaseReference = mDatabase.getReference();
        //User user = new User ("Paul", 23, "Paul@gmail.com","CHACABUCO 44",-31.4228, -62.1802,"3512043546","CUSTOMER");
        //mDatabaseReference = mDatabase.getReference (). child ("Users").child("1");
        //mDatabaseReference.setValue (user);



        /*BottomNavigationView navView = findViewById(R.id.nav_view_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_hire, R.id.navigation_favorites,R.id.navigation_activity, R.id.navigation_messages, R.id.navigation_profile).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/




    }




}
