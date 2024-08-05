package com.example.whodo.features.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.whodo.R;
import com.example.whodo.features.activity.ActivityFrag_ViewPagerAdapter;
import com.example.whodo.features.activity.ActivityViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivityFragment extends Fragment {

    private static final String TAG = "TAG-1";
    private ActivityViewModel activityViewModel;
    private TextView textView1;
    private ViewPager2 Activity_ViewPager;
    private TabLayout Activity_TabLayout;
    private final String[] Titles = new String[]{"En Curso","Finalizadas","Cerradas"};
    private ActivityFrag_ViewPagerAdapter Activity_ViewPagerAdapterFrag;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        View root = inflater.inflate(R.layout.act_main_frag_activity, container, false);

        textView1 = root.findViewById(R.id.activity_textView);
        textView1.setText(activityViewModel.getText());


        Activity_ViewPager= root.findViewById(R.id.activity_ViewPager);
        Activity_TabLayout= root.findViewById(R.id.activity_TabLayout);
        Activity_ViewPagerAdapterFrag = new ActivityFrag_ViewPagerAdapter(this);
        Activity_ViewPager.setAdapter(Activity_ViewPagerAdapterFrag);
        new TabLayoutMediator(Activity_TabLayout,Activity_ViewPager,((tab, position) -> tab.setText(Titles[position]))).attach();


        return root;
    }



}