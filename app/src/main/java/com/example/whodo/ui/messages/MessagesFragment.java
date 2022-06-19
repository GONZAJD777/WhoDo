package com.example.whodo.ui.messages;

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
import com.google.android.material.tabs.TabLayout;

public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;
    private TextView textView1;
    private TabLayout Messages_TabLayout;
    private ViewPager2 Messages_ViewPager;
    private static final String TAG = "TAG-1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);

        textView1 = root.findViewById(R.id.messages_textView);
        textView1.setText(messagesViewModel.getText());

        Messages_ViewPager= root.findViewById(R.id.Messages_ViewPager);
        //setupViewPager(Messages_ViewPager);

        Messages_TabLayout= root.findViewById(R.id.messages_TabLayout);
        //Messages_TabLayout.setupWithViewPager(Messages_ViewPager);
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
}