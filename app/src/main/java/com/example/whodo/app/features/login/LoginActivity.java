package com.example.whodo.app.features.login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.whodo.R;
import com.example.whodo.app.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
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

    private void checkLoggedUser (){
        if(currentUser != null){

            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            Toast.makeText(this, "You are logged with: " + currentUser.getEmail(),Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "You are not logged in the application",Toast.LENGTH_SHORT).show();
        }

    }
}
