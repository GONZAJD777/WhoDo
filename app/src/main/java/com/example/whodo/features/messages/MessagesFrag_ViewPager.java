package com.example.whodo.features.messages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.aplication.MainActivityViewModel;

public class MessagesFrag_ViewPager extends Fragment {

    private Integer FragType=0;

    private LinearLayout LinearLayoutItems;
    private View ItemMessage;
    private View ItemNotificactions;

    private MainActivityViewModel mMainActivityViewModel;


    public MessagesFrag_ViewPager(){}
    public MessagesFrag_ViewPager(Integer Ft) {
        FragType=Ft;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_viewpager, container, false);
        LinearLayoutItems = root.findViewById(R.id.viewPager_linearLayout);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        ItemMessage = inflater.inflate(R.layout.item_messages,container,false);
        ItemNotificactions = inflater.inflate(R.layout.item_notifications,container,false);
        addFragments();
        return root;
    }
    private void addFragments() {
        LinearLayoutItems.removeAllViews();
        if (FragType==0) {
            addMessageItem ();
        }

        if (FragType==1) {
            addNotificationItem ();
        }
    }

    @SuppressLint("SetTextI18n")
    private void addMessageItem (){
        TextView textView_Name = ItemMessage.findViewById(R.id.textView_Name);
        TextView textView_Date = ItemMessage.findViewById(R.id.textView_Date);
        TextView textView_Message = ItemMessage.findViewById(R.id.textView_Message);
        TextView textView_Status = ItemMessage.findViewById(R.id.textView_Status);
        Log.d("addMessageItem", "MessageItem agregar item");
        textView_Name.setText("Fernanda");
        textView_Message.setText("Cagaste fuego, ya esta ocupada campeon pero tengo esta entre las piernas donde podes entrar");
        textView_Date.setText("1 de Junio de 2021");
        textView_Status.setText("No disponible, 1 de Junio 2021 - 10 de Junio 2021");
        LinearLayoutItems.addView(ItemMessage);
    }

    @SuppressLint("SetTextI18n")
    private void addNotificationItem (){
        TextView textView_Label = ItemNotificactions.findViewById(R.id.textView_Label);
        TextView textView_DayDate = ItemNotificactions.findViewById(R.id.textView_DayDate);
        TextView textView_DayMonthYear = ItemNotificactions.findViewById(R.id.textView_DayMonthYear);
        TextView textView_Notification = ItemNotificactions.findViewById(R.id.textView_Notification);
        TextView textView_Hour = ItemNotificactions.findViewById(R.id.textView_Hour);
        Log.d("addNotificationItem", "MessageItem agregar item");
        textView_Label.setText("Notificacion");
        textView_DayDate.setText("07");
        textView_DayMonthYear.setText("Oct 2021");
        textView_Notification.setText("Tienes una cita con Ricardo Fleitas PLOMERO/ELECTRICISTA el dia y hora indicada.");
        textView_Hour.setText("11:00");
        LinearLayoutItems.addView(ItemNotificactions);

    }
}
