package com.example.whodo.ui.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whodo.R;
import com.example.whodo.adapters.Profile_ViewPagerAdapter;

public class ProfileActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        androidx.viewpager2.widget.ViewPager2 viewPager2 = findViewById(R.id.Profile_view_pager);
        Profile_ViewPagerAdapter Profile_ViewPagerAdapter = new Profile_ViewPagerAdapter(this,FragmentId);
        viewPager2.setAdapter(Profile_ViewPagerAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
