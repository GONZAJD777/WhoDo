package com.example.whodo.aplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.domain.workOrder.WorkOrder;
import com.example.whodo.features.activity.ActivityWorkOrderItem;
import com.example.whodo.utils.Utils;

import java.util.List;
import java.util.Objects;


public class ViewPagerFragment extends Fragment {

    private Integer FragType=0;

    private LinearLayout LinearLayoutItems;
    private View ItemMessage;
    private View ItemFavoritesItem;
    private View ItemNotificactions;

    private MainActivityViewModel mMainActivityViewModel;

    public ViewPagerFragment(){}
    public ViewPagerFragment(Integer Ft) {
        FragType=Ft;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_viewpager, container, false);
        Log.d("onCreateView", "MessViewPagerFragment agregando message_list");
        LinearLayoutItems = root.findViewById(R.id.viewPager_linearLayout);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        mMainActivityViewModel.getWorkOrder().observe(getViewLifecycleOwner(),this::addFragments);


        ItemMessage = inflater.inflate(R.layout.item_messages,container,false);
        ItemNotificactions = inflater.inflate(R.layout.item_notifications,container,false);
        ItemFavoritesItem = inflater.inflate(R.layout.item_favorites,container,false);



        return root;
    }

    private void addFragments(List<WorkOrder> workOrders) {
        LinearLayoutItems.removeAllViews();
        if (FragType==1) {addFavoritesItem();}

        if (FragType==2) {
            for (WorkOrder mWorkOrder : workOrders) {
                addPendingItem(mWorkOrder);
            }
        }

        if (FragType==3) {addClosedItem();}

        if (FragType==4) {addCanceledItem();}

        if (FragType==5) {addMessageItem();}

        if (FragType==6) {addNotificationItem();}
    }


    @SuppressLint("SetTextI18n")
    private void addFavoritesItem (){
        TextView textView_Name = ItemFavoritesItem.findViewById(R.id.textView_Name);
        TextView textView_Reviews = ItemFavoritesItem.findViewById(R.id.textView_Reviews);
        TextView textView_PricePercent = ItemFavoritesItem.findViewById(R.id.textView_PricePercent);
        TextView textView_Speed = ItemFavoritesItem.findViewById(R.id.textView_Speed);
        TextView textView_Spec = ItemFavoritesItem.findViewById(R.id.textView_Spec);
        Log.d("addFavoritesItem", "MessageItem agregar item");
        textView_Name.setText("Ricardo Fleitas");
        textView_Reviews.setText("4.3 (135)");
        textView_PricePercent.setText("1500 (4%)");
        textView_Speed.setText("6.5 dias (103)");
        textView_Spec.setText("ELECTRICIDAD/PLOMERIA");
        LinearLayoutItems.addView(ItemFavoritesItem);

    }

    private void addClosedItem (){

    }

    private void addCanceledItem (){

    }

    private void addPendingItem (WorkOrder pWorkOrder){
        ActivityWorkOrderItem mActivityWorkOrderItem = new ActivityWorkOrderItem(requireContext());
        String mWorkOrderType;
        String mCustomerStates = "PLANNED,DIAGNOSED,DONE";
        String mProviderStates = "ONEVALUATION,CONFIRMED,ONPROGRESS";
        if (Objects.equals(pWorkOrder.getCustomerId(), Objects.requireNonNull(mMainActivityViewModel.getLoggedUser().getValue()).getUid()))
        {mWorkOrderType="CUSTOMER";}
        else {mWorkOrderType="PROVIDER";}

        mActivityWorkOrderItem.setOrderState("Orden "+pWorkOrder.getState());
        mActivityWorkOrderItem.setLimitDate("Fecha Limite: " + Utils.setLongToDate(pWorkOrder.getTimeLimit()));
        mActivityWorkOrderItem.setLastUpdate("Ultima Actualizacion: " + Utils.setLongToDate(pWorkOrder.getStateChangeDate()));

        if (mWorkOrderType.equals("CUSTOMER")) {
            mActivityWorkOrderItem.setCategoryImage(AppCompatResources.getDrawable(requireContext(), getImageFromString(pWorkOrder.getSpecialization(), 64, 1)));
        }else {
            mActivityWorkOrderItem.setCategoryImage(AppCompatResources.getDrawable(requireContext(), getImageFromString(pWorkOrder.getSpecialization(), 64,2)));
        }

        if (mWorkOrderType.equals("CUSTOMER") && mCustomerStates.contains(pWorkOrder.getState())){
            mActivityWorkOrderItem.setActionIndicator(AppCompatResources.getDrawable(requireContext(), android.R.drawable.presence_online));
        } else if (mWorkOrderType.equals("PROVIDER") && mProviderStates.contains(pWorkOrder.getState())){
            mActivityWorkOrderItem.setActionIndicator(AppCompatResources.getDrawable(requireContext(), android.R.drawable.presence_online));
        } else {
            mActivityWorkOrderItem.setActionIndicator(AppCompatResources.getDrawable(requireContext(), android.R.drawable.presence_invisible));
        }
        mActivityWorkOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivityViewModel.setPickedWorkOrder(pWorkOrder);
                mMainActivityViewModel.setSelectedFragment(15,View.GONE);
            }
        });
        LinearLayoutItems.addView(mActivityWorkOrderItem);

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

    private int getImageFromString (String pService,int pSize,Integer pType) {
        int ServiceImage = R.drawable.proveevarios;
        if (pSize==64){
            switch (pType)
            {
                case 1:
                    switch (pService) {
                        case "Plomero":
                            ServiceImage = R.drawable.plomeria_64;
                            break;
                        case "Pintor":
                            ServiceImage = R.drawable.pintura_64;
                            break;
                        case "Electricista":
                            ServiceImage = R.drawable.electricidad_64;
                            break;
                        case "Carpintero":
                            ServiceImage = R.drawable.carpinteria_64;
                            break;
                        case "Albañil":
                            ServiceImage = R.drawable.alba_ileria_64;
                            break;
                        case "Tecnico de Computacion":
                            ServiceImage = R.drawable.computacion_64;
                            break;
                        case "Tecnico de Refrigeracion":
                            ServiceImage = R.drawable.refrigeracion_64;
                            break;
                        case "Jardinero":
                            ServiceImage = R.drawable.jardineria_64;
                            break;
                        case "Cerrajero":
                            ServiceImage = R.drawable.cerrajeria_64;
                            break;
                        case "Gasista":
                            ServiceImage = R.drawable.gasista_64;
                            break;
                    }
                    break;
                case 2:
                    switch (pService) {
                        case "Plomero":
                            ServiceImage = R.drawable.plomeria_orange_64;
                            break;
                        case "Pintor":
                            ServiceImage = R.drawable.pintura_orange_64;
                            break;
                        case "Electricista":
                            ServiceImage = R.drawable.electricidad_orange_64;
                            break;
                        case "Carpintero":
                            ServiceImage = R.drawable.carpinteria_orange_64;
                            break;
                        case "Albañil":
                            ServiceImage = R.drawable.alba_ileria_orange_64;
                            break;
                        case "Tecnico de Computacion":
                            ServiceImage = R.drawable.computacion_orange_64;
                            break;
                        case "Tecnico de Refrigeracion":
                            ServiceImage = R.drawable.refrigeracion_orange_64;
                            break;
                        case "Jardinero":
                            ServiceImage = R.drawable.jardineria_orange_64;
                            break;
                        case "Cerrajero":
                            ServiceImage = R.drawable.cerrajeria_orange_64;
                            break;
                        case "Gasista":
                            ServiceImage = R.drawable.gasista_orange_64;
                            break;
                    }
                    break;
            }
        }

        return ServiceImage;
    }
}
