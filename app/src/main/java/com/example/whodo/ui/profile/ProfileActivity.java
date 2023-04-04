package com.example.whodo.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.whodo.R;
import com.example.whodo.adapters.Profile_ViewPagerAdapter;

public class ProfileActivity extends AppCompatActivity {


    private androidx.viewpager2.widget.ViewPager2 ViewPager2;

    @SuppressLint({"RestrictedApi", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profile);

        //recover the FragmentId that activity will contain sent on bundle
        int FragmentId =getIntent().getExtras().getInt("FragmentId") ;
        String FragmentTitle =getIntent().getExtras().getString("FragmentTitle") ;

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(FragmentTitle);

        //actionBar.hide();

        ViewPager2 = findViewById(R.id.Profile_view_pager);
        Profile_ViewPagerAdapter Profile_ViewPagerAdapter = new Profile_ViewPagerAdapter(this,FragmentId);
        ViewPager2.setAdapter(Profile_ViewPagerAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



}
