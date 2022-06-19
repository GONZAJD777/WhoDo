package com.example.whodo.ui.activity;

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
import com.example.whodo.adapters.ActivityFrag_ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class ActivityFragment extends Fragment {

    private static final String TAG = "TAG-1";
    private ActivityViewModel activityViewModel;
    private TextView textView1;
    private ViewPager2 Activity_ViewPager;
    private TabLayout Activity_TabLayout;
    private final String[] Titles = new String[]{"Pendientes","Finalizadas","Canceladas"};
    private ActivityFrag_ViewPagerAdapter Activity_ViewPagerAdapterFrag;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activity, container, false);

        textView1 = root.findViewById(R.id.activity_textView);
        textView1.setText(activityViewModel.getText());


        Activity_ViewPager= root.findViewById(R.id.activity_ViewPager);
        //setupViewPager(Activity_ViewPager);

        Activity_TabLayout= root.findViewById(R.id.activity_TabLayout);
        //Activity_TabLayout.setupWithViewPager(Activity_ViewPager);

        Activity_ViewPagerAdapterFrag = new ActivityFrag_ViewPagerAdapter(this);

        Activity_ViewPager.setAdapter(Activity_ViewPagerAdapterFrag);


        //new TabLayoutMediator(Activity_TabLayout,Activity_ViewPager,((tab, position) -> tab.setText(adapter.getFragList[position]))).attach();



        /*Activity_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Activity_ViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        // TODO
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });*/

        return root;
    }




}