package com.example.whodo.app.features.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.resources.images.ImageManager;
import com.example.whodo.app.utils.Utils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ActivityFrag_ViewPager extends Fragment {

    private Integer FragType=0;

    private LinearLayout LinearLayoutItems;

    private MainActivityViewModel mMainActivityViewModel;

    private final String [] mStates = {"ONEVALUATION","CONFIRMED","ONPROGRESS","PLANNED","DIAGNOSED"};


    public ActivityFrag_ViewPager(){}
    public ActivityFrag_ViewPager(Integer Ft) {
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

        return root;
    }

    private void addFragments(List<WorkOrder> workOrders) {
        LinearLayoutItems.removeAllViews();
        if (FragType==0) {
            for (WorkOrder mWorkOrder : workOrders) {
                if (Arrays.asList(mStates).contains(mWorkOrder.getState()) ) {
                    addActivityItem(mWorkOrder);
                }
            }
        }
        if (FragType==1) {
            for (WorkOrder mWorkOrder : workOrders) {
                if (Objects.equals(mWorkOrder.getState(), "DONE")) {
                    addActivityItem(mWorkOrder);
                }
            }
        }
        if (FragType==2) {
            for (WorkOrder mWorkOrder : workOrders) {
                if (Objects.equals(mWorkOrder.getState(), "CLOSED")) {
                    addActivityItem(mWorkOrder);
                }
            }
        }
    }

    private void addActivityItem (WorkOrder pWorkOrder){
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
            Bitmap mServIconName = ImageManager.getStoredIcon(requireContext(),pWorkOrder.getSpecialization() + "_64", 210, 210);
            Drawable drawable = new BitmapDrawable(getResources(), mServIconName);
            mActivityWorkOrderItem.setCategoryImage(drawable);

        }else {
            Bitmap mServIconName = ImageManager.getStoredIcon(requireContext(),pWorkOrder.getSpecialization() + "_orden_64", 205, 205);
            Drawable drawable = new BitmapDrawable(getResources(), mServIconName);
            mActivityWorkOrderItem.setCategoryImage(drawable);
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
