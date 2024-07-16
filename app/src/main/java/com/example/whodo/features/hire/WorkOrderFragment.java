package com.example.whodo.features.hire;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.aplication.MainActivityViewModel;
import com.example.whodo.domain.user.User;
import com.example.whodo.domain.workOrder.WorkOrder;
import com.example.whodo.features.hire.WorkOrderState.ClosedState;
import com.example.whodo.features.hire.WorkOrderState.ConfState;
import com.example.whodo.features.hire.WorkOrderState.DiagState;
import com.example.whodo.features.hire.WorkOrderState.DoneState;
import com.example.whodo.features.hire.WorkOrderState.OnEvalState;
import com.example.whodo.features.hire.WorkOrderState.OnProgState;
import com.example.whodo.features.hire.WorkOrderState.OpenState;
import com.example.whodo.features.hire.WorkOrderState.PlannedState;
import com.example.whodo.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class WorkOrderFragment extends Fragment {
    private final String TAG1 = "WORK-ORDER-FRAG";
    private HireFragmentViewModel mHireFragmentViewModel;
    private MainActivityViewModel mMainActivityViewModel;
    private LinearLayout openStateDetail_LinearLayout;
    private LinearLayout onEvalStateDetail_LinearLayout;
    private LinearLayout plannedStateDetail_LinearLayout;
    private LinearLayout confStateDetail_LinearLayout;
    private LinearLayout diagStateDetail_LinearLayout;
    private LinearLayout onProgStateDetail_LinearLayout;
    private LinearLayout doneStateDetail_LinearLayout;
    private LinearLayout closedStateDetail_LinearLayout;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_work_order_lifecycle, container, false);
        mHireFragmentViewModel = new ViewModelProvider(requireActivity()).get(HireFragmentViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        FloatingActionButton saveChangesButton = root.findViewById(R.id.SaveChangesButton);
        saveChangesButton.setOnClickListener(this::onClick);

        openStateDetail_LinearLayout=root.findViewById(R.id.openStateDetail_LinearLayout);
        onEvalStateDetail_LinearLayout=root.findViewById(R.id.onEvalStateDetail_LinearLayout);
        plannedStateDetail_LinearLayout=root.findViewById(R.id.plannedStateDetail_LinearLayout);
        confStateDetail_LinearLayout=root.findViewById(R.id.confStateDetail_LinearLayout);
        diagStateDetail_LinearLayout=root.findViewById(R.id.diagStateDetail_LinearLayout);
        onProgStateDetail_LinearLayout=root.findViewById(R.id.onProgStateDetail_LinearLayout);
        doneStateDetail_LinearLayout=root.findViewById(R.id.doneStateDetail_LinearLayout);
        closedStateDetail_LinearLayout=root.findViewById(R.id.closedStateDetail_LinearLayout);

        //onEvalStateDetail_LinearLayout.addView(new OnEvalState(requireContext()));
//        plannedStateDetail_LinearLayout.addView(new PlannedState(requireContext()));
//        confStateDetail_LinearLayout.addView(new ConfState(requireContext()));
//        diagStateDetail_LinearLayout.addView(new DiagState(requireContext()));
//        onProgStateDetail_LinearLayout.addView(new OnProgState(requireContext()));
//        doneStateDetail_LinearLayout.addView(new DoneState(requireContext()));
//        closedStateDetail_LinearLayout.addView(new ClosedState(requireContext()));
        if(mMainActivityViewModel.getPickedWorkOrder().getValue()==null) {
            openStateWorkOrder();
        } else if (Objects.equals(mMainActivityViewModel.getPickedWorkOrder().getValue().getState(), "ONEVALUATION")) {
            onEvalStateWorkOrder(mMainActivityViewModel.getPickedWorkOrder().getValue());
        }




        return root;
    }

    private void openStateWorkOrder () {
        //********************************** OPEN STATE **********************************//
        String[] mProviderServices = Objects.requireNonNull(mHireFragmentViewModel.getPickedProvider().getValue()).getSpecialization().split(",");
        User mPickedProvider  = mHireFragmentViewModel.getPickedProvider().getValue();
        User mLoggedUser = mMainActivityViewModel.getLoggedUser().getValue();
        OpenState mOpenStateItem = new OpenState(requireContext());
        mOpenStateItem.setSpinnerValues(mProviderServices);
        mOpenStateItem.setProviderValue((mHireFragmentViewModel.getPickedProvider().getValue().getName()));
        mOpenStateItem.getCreateWorkOrder_button().setOnClickListener(v -> {
            assert mLoggedUser != null;
            createOrder(mLoggedUser.getUid(),
                    mPickedProvider.getUid(),
                    mOpenStateItem.getCategoryValue(),
                    mOpenStateItem.getDescriptionValue(),
                    mOpenStateItem.getTimeLimitValue());
        });
        openStateDetail_LinearLayout.addView(mOpenStateItem);
        //********************************** OPEN STATE **********************************//
    }

    private void createOrder(String pCustomer,String pProvider ,String pCategory,String pDescription,Integer pLimitValue){
        Long mTimeLimitDate = Utils.setDateToLong(Utils.increseDate(pLimitValue,new Date()));
        Long mCreationDate= Utils.setDateToLong(new Date());
        Long mStateChangeDate=Utils.setDateToLong(new Date());
        WorkOrder WO = new WorkOrder(pCustomer, pProvider, pCategory, pDescription, mCreationDate,mTimeLimitDate,mStateChangeDate);
        mMainActivityViewModel.setWorkOrder(WO);
        Log.d(TAG1, "BOTON CREAR ORDEN PRESIONADO");
    }

    private void onEvalStateWorkOrder(WorkOrder pWorkOrder) {
        //********************************** ON EVALUATION STATE **********************************//
        User mCustomer = mMainActivityViewModel.getCustomerWorkOrder(pWorkOrder.getCustomerId());
        OnEvalState mOnEvalStateItem = new OnEvalState(requireContext());
        mOnEvalStateItem.setLimitDate(Utils.setLongToDate(pWorkOrder.getTimeLimit()));
        mOnEvalStateItem.setCustomerName(mCustomer.getName());
        mOnEvalStateItem.setCustomerAddress(mCustomer.getAddress());
        mOnEvalStateItem.setCategory(pWorkOrder.getSpecialization());
        mOnEvalStateItem.setDescription(pWorkOrder.getDescription());
        mOnEvalStateItem.setOnClickListener(v -> {

        });
        onEvalStateDetail_LinearLayout.addView(mOnEvalStateItem);
        //********************************** ON EVALUATION STATE **********************************//
    }


    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveChangesButton:
                mMainActivityViewModel.setSelectedFragment(0,View.VISIBLE);
                break;
        }
    }
}
