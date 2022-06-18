package com.example.whodo;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference mDatabaseReference = mDatabase.getReference();
        //User user = new User ("Paul", 23, "Paul@gmail.com","CHACABUCO 44",-31.4228, -62.1802,"3512043546","CUSTOMER");
        //mDatabaseReference = mDatabase.getReference (). child ("Users").child("1");
        //mDatabaseReference.setValue (user);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        BottomNavigationView navView = findViewById(R.id.nav_view_login);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_login_mail_input, R.id.navigation_login_data_input).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment1);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();



    }

}
