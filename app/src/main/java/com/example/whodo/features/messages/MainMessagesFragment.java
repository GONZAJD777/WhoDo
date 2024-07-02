package com.example.whodo.features.messages;

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
import com.example.whodo.features.messages.MessagesFrag_ViewPagerAdapter;
import com.example.whodo.features.messages.MessagesViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainMessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;
    private TextView textView1;
    private TabLayout Messages_TabLayout;
    private ViewPager2 Messages_ViewPager;
    private static final String TAG = "TAG-1";
    private MessagesFrag_ViewPagerAdapter Messages_ViewPagerAdapterFrag;
    private final String[] Titles = new String[]{"Messages","Notifications"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel= new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.act_main_frag_messages, container, false);

        textView1 = root.findViewById(R.id.messages_textView);
        textView1.setText(messagesViewModel.getText());

        Messages_ViewPager= root.findViewById(R.id.Messages_ViewPager);
        Messages_TabLayout= root.findViewById(R.id.messages_TabLayout);

        Messages_ViewPagerAdapterFrag = new MessagesFrag_ViewPagerAdapter(this);
        Messages_ViewPager.setAdapter(Messages_ViewPagerAdapterFrag);
        new TabLayoutMediator(Messages_TabLayout,Messages_ViewPager,((tab, position) -> tab.setText(Titles[position]))).attach();


        return root;
    }
}