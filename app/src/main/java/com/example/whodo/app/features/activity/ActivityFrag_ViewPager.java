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

        mActivityWorkOrderItem.setOrderState("Estado de Orden: "+pWorkOrder.getState());
        mActivityWorkOrderItem.setLimitDate("Fecha Limite: " + Utils.getISOtoDate(pWorkOrder.getTimeLimit()));
        mActivityWorkOrderItem.setLastUpdate("Ultima Actualizacion: " + Utils.getISOtoDate(pWorkOrder.getStateChangeDate()));

        if (mWorkOrderType.equals("CUSTOMER")) {
            Bitmap mServIconName = ImageManager.getStoredIcon(requireContext(),pWorkOrder.getSpecialization() + "_64", 64, 64);
            Drawable drawable = new BitmapDrawable(getResources(), mServIconName);
            mActivityWorkOrderItem.setCategoryImage(drawable);
            mActivityWorkOrderItem.setUserName("Usuario: "+pWorkOrder.getProviderName());

        }else {
            Bitmap mServIconName = ImageManager.getStoredIcon(requireContext(),pWorkOrder.getSpecialization() + "_orden_64", 64, 64);
            Drawable drawable = new BitmapDrawable(getResources(), mServIconName);
            mActivityWorkOrderItem.setCategoryImage(drawable);
            mActivityWorkOrderItem.setUserName("Usuario: "+pWorkOrder.getCustomerName());

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

}
