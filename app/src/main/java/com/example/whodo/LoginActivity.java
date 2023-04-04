package com.example.whodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whodo.adapters.Login_ViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final String[] Titles = new String[2];
    private androidx.viewpager2.widget.ViewPager2 ViewPager2;
    private com.google.android.material.tabs.TabLayout TabLayout;
    private Login_ViewPagerAdapter Login_ViewPagerAdapter;
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private final FirebaseUser currentUser= mAuth.getCurrentUser();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

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

        checkLoggedUser ();

    }
    private void checkLoggedUser ()
    {


        if(currentUser != null){
            currentUser.getEmail();
            Intent intent = new Intent(this,MainActivity.class);
            //Bundle b = new Bundle();
            //b.putString("LoggedUser_Email",currentUser.getEmail());
            //b.putString("LoggedUser_Uid",currentUser.getUid());
            //intent.putExtras(b); //Put your id to your next Intent
            this.startActivity(intent);
            this.finish();
            Toast.makeText(this, "You are logged with: "+currentUser.getEmail(),Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "You are not logged in the application",Toast.LENGTH_SHORT).show();
        }

    }
}
