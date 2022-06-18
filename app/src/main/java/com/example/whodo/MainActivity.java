package com.example.whodo;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference mDatabaseReference = mDatabase.getReference();
        //User user = new User ("Paul", 23, "Paul@gmail.com","CHACABUCO 44",-31.4228, -62.1802,"3512043546","CUSTOMER");
        //mDatabaseReference = mDatabase.getReference (). child ("Users").child("1");
        //mDatabaseReference.setValue (user);



        BottomNavigationView navView = findViewById(R.id.nav_view_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_hire, R.id.navigation_favorites,R.id.navigation_activity, R.id.navigation_messages, R.id.navigation_profile).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


    }




}
