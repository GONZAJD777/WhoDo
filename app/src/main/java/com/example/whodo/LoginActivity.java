package com.example.whodo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.whodo.adapters.Login_ViewPagerAdapter;
import com.example.whodo.adapters.Main_ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final String[] Titles = new String[2];
    private androidx.viewpager2.widget.ViewPager2 ViewPager2;
    private com.google.android.material.tabs.TabLayout TabLayout;
    private Login_ViewPagerAdapter Login_ViewPagerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference mDatabaseReference = mDatabase.getReference();
        //User user = new User ("Paul", 23, "Paul@gmail.com","CHACABUCO 44",-31.4228, -62.1802,"3512043546","CUSTOMER");
        //mDatabaseReference = mDatabase.getReference (). child ("Users").child("1");
        //mDatabaseReference.setValue (user);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ViewPager2=findViewById(R.id.Login_view_pager);
        TabLayout=findViewById(R.id.Login_TabLayout);
        ViewPager2.setUserInputEnabled(false);
        Login_ViewPagerAdapter = new Login_ViewPagerAdapter(this);
        ViewPager2.setAdapter(Login_ViewPagerAdapter);
        new TabLayoutMediator(TabLayout,ViewPager2,((tab, position) -> tab.setText(Titles[position]))).attach();

        Objects.requireNonNull(TabLayout.getTabAt(0)).setText("Login");
        Objects.requireNonNull(TabLayout.getTabAt(1)).setText("Registrarse");

    }

}
