package com.example.whodo.ui.messages;

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

import com.example.whodo.adapters.ViewPagerAdapter;
import com.example.whodo.R;
import com.example.whodo.ui.ViewPagerFragment;
import com.google.android.material.tabs.TabLayout;

public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;
    private TextView textView1;
    private TabLayout Messages_TabLayout;
    private ViewPager Messages_ViewPager;
    private static final String TAG = "TAG-1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);

        textView1 = root.findViewById(R.id.messages_textView);
        textView1.setText(messagesViewModel.getText());

        Messages_ViewPager= root.findViewById(R.id.Messages_ViewPager);
        setupViewPager(Messages_ViewPager);

        Messages_TabLayout= root.findViewById(R.id.messages_TabLayout);
        Messages_TabLayout.setupWithViewPager(Messages_ViewPager);
        Messages_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Messages_ViewPager.setCurrentItem(tab.getPosition());
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
        adapter.addFrag(new ViewPagerFragment(5), "Messages");
        adapter.addFrag(new ViewPagerFragment(6), "Notifications");
        Log.d(TAG, "MessagesFragment creando fragmentos");
        viewPager.setAdapter(adapter);
    }


}