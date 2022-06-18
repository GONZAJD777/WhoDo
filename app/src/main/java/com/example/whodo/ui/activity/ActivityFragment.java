package com.example.whodo.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.whodo.R;
import com.example.whodo.adapters.ViewPagerAdapter;
import com.example.whodo.ui.ViewPagerFragment;
import com.google.android.material.tabs.TabLayout;


public class ActivityFragment extends Fragment {

    private static final String TAG = "TAG-1";
    private ActivityViewModel activityViewModel;
    private TextView textView1;
    private ViewPager Activity_ViewPager;
    private TabLayout Activity_TabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activity, container, false);

        textView1 = root.findViewById(R.id.activity_textView);
        textView1.setText(activityViewModel.getText());

        Activity_ViewPager= root.findViewById(R.id.activity_ViewPager);
        setupViewPager(Activity_ViewPager);

        Activity_TabLayout= root.findViewById(R.id.activity_TabLayout);
        Activity_TabLayout.setupWithViewPager(Activity_ViewPager);

        Activity_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ViewPagerFragment(2), "Pendientes");
        adapter.addFrag(new ViewPagerFragment(3), "Finalizadas");
        adapter.addFrag(new ViewPagerFragment(4), "Canceladas");
        Log.d(TAG, "MessagesFragment creando fragmentos");
        viewPager.setAdapter(adapter);
    }


}