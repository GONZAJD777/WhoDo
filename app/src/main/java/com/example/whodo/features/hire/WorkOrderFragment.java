package com.example.whodo.features.hire;

import static com.example.whodo.utils.Utils.setStringToDate;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.aplication.MainActivityViewModel;
import com.example.whodo.domain.user.User;
import com.example.whodo.domain.workOrder.WorkOrder;
import com.example.whodo.features.hire.WorkOrderState.OnEvalState;
import com.example.whodo.features.hire.WorkOrderState.OpenState;
import com.example.whodo.features.hire.WorkOrderState.PlannedState;
import com.example.whodo.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.Objects;

public class WorkOrderFragment extends Fragment {
    private final String TAG1 = "WORK-ORDER-FRAG";
    private HireFragmentViewModel mHireFragmentViewModel;
    private MainActivityViewModel mMainActivityViewModel;
    private LinearLayout openStateDetail_LinearLayout;
    private View openStateDetail_vertLine;
    private View onEvalStateDetail_vertLine;
    private View plannedStateDetail_vertLine;
    private LinearLayout onEvalStateDetail_LinearLayout;
    private LinearLayout plannedStateDetail_LinearLayout;
    private LinearLayout confStateDetail_LinearLayout;
    private LinearLayout diagStateDetail_LinearLayout;
    private LinearLayout onProgStateDetail_LinearLayout;
    private LinearLayout doneStateDetail_LinearLayout;
    private LinearLayout closedStateDetail_LinearLayout;
    private TextView orderId_label;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_work_order_lifecycle, container, false);
        mHireFragmentViewModel = new ViewModelProvider(requireActivity()).get(HireFragmentViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        orderId_label = root.findViewById(R.id.orderId_label);
        FloatingActionButton saveChangesButton = root.findViewById(R.id.SaveChangesButton);
        saveChangesButton.setOnClickListener(this::onClick);

        openStateDetail_LinearLayout=root.findViewById(R.id.openStateDetail_LinearLayout);
        openStateDetail_vertLine=root.findViewById(R.id.openStateDetail_vertLine);

        onEvalStateDetail_LinearLayout=root.findViewById(R.id.onEvalStateDetail_LinearLayout);
        onEvalStateDetail_vertLine=root.findViewById(R.id.onEvalStateDetail_vertLine);

        plannedStateDetail_LinearLayout=root.findViewById(R.id.plannedStateDetail_LinearLayout);
        plannedStateDetail_vertLine=root.findViewById(R.id.plannedStateDetail_vertLine);

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
            String mOrderId= "ID de Orden: "+mMainActivityViewModel.getPickedWorkOrder().getValue().getOrderId();
            orderId_label.setText(mOrderId);
            onEvalStateWorkOrder(mMainActivityViewModel.getPickedWorkOrder().getValue());
        } else if (Objects.equals(mMainActivityViewModel.getPickedWorkOrder().getValue().getState(), "PLANNED")){
            String mOrderId= "ID de Orden: "+mMainActivityViewModel.getPickedWorkOrder().getValue().getOrderId();
            orderId_label.setText(mOrderId);
            plannedStateWorkOrder(mMainActivityViewModel.getPickedWorkOrder().getValue());
        }


        return root;
    }

    //********************************** OPEN STATE **********************************//
    private void openStateWorkOrder () {
        String[] mProviderServices = Objects.requireNonNull(mHireFragmentViewModel.getPickedProvider().getValue()).getSpecialization().split(",");
        User mPickedProvider  = mHireFragmentViewModel.getPickedProvider().getValue();
        User mLoggedUser = mMainActivityViewModel.getLoggedUser().getValue();
        OpenState mOpenStateItem = new OpenState(requireContext());
        mOpenStateItem.setProviderName("Nombre: "+mPickedProvider.getName());
        mOpenStateItem.setProviderAddress("Direccion: "+mPickedProvider.getAddress());
        mOpenStateItem.setProviderPhone("Telefono: "+mPickedProvider.getPhone_ccn()+" "+mPickedProvider.getPhone());
        mOpenStateItem.setSpinnerValues(mProviderServices);
        mOpenStateItem.setOnClickListener(v -> {
            assert mLoggedUser != null;
            createOrder(mLoggedUser,
                    mPickedProvider,
                    mOpenStateItem.getCategoryValue(),
                    mOpenStateItem.getDescriptionValue(),
                    mOpenStateItem.getTimeLimitValue());
        });
        openStateDetail_LinearLayout.addView(mOpenStateItem);
        openStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        openStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** OPEN STATE **********************************//
    private void createOrder(User pCustomer,User pProvider ,String pCategory,String pDescription,Integer pLimitValue){
        Long mTimeLimitDate = Utils.setDateToLong(Utils.increseDate(pLimitValue,new Date()));
        Long mCreationDate= Utils.setDateToLong(new Date());
        Long mStateChangeDate=Utils.setDateToLong(new Date());

        String mCustomerPhoneNumber = pCustomer.getPhone_ccn() + pCustomer.getPhone();
        String mProviderPhoneNumber = pProvider.getPhone_ccn() + pProvider.getPhone();

        WorkOrder WO = new WorkOrder(pCustomer.getUid(),pCustomer.getName(),pCustomer.getAddress(),pCustomer.getLatitude(),pCustomer.getLongitude(),mCustomerPhoneNumber,
                                     pProvider.getUid(),pProvider.getName(),pProvider.getAddress(),pProvider.getLatitude(),pProvider.getLongitude(),mProviderPhoneNumber,
                                     pCategory, pDescription, mCreationDate,mTimeLimitDate,mStateChangeDate);
        mMainActivityViewModel.createWorkOrder(WO);
        Log.d(TAG1, "BOTON CREAR ORDEN PRESIONADO");
    }
    //********************************** ON EVALUATION STATE **********************************//
    private void onEvalStateWorkOrder(WorkOrder pWorkOrder) {
        OnEvalState mOnEvalStateItem = new OnEvalState(requireContext());

        mOnEvalStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mOnEvalStateItem.setCustomerAddress("Direccion: "+ pWorkOrder.getCustomerAddress());
        mOnEvalStateItem.setCustomerPhone("Telefono: "+ pWorkOrder.getCustomerPhoneNumber());

        mOnEvalStateItem.setLimitDate("Fecha limite: "+Utils.setLongToDate(pWorkOrder.getTimeLimit()));
        mOnEvalStateItem.setCategory("Categoria: "+pWorkOrder.getSpecialization());
        mOnEvalStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        mOnEvalStateItem.setAcceptButtonOCL(v -> {
            Long mInspectionDate = Utils.setDateToLong(Utils.setStringToDate(mOnEvalStateItem.getMeetDate() +" "+ mOnEvalStateItem.getmeetTime()));
            Integer mInspectionCharges = Integer.valueOf(mOnEvalStateItem.getmeetTariff());

            acceptDate(pWorkOrder.getOrderId(),
                       mInspectionDate,
                       mInspectionCharges);
            Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");
        });
        mOnEvalStateItem.setRejectButtonOCL(v -> {  Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");   });

        onEvalStateDetail_LinearLayout.addView(mOnEvalStateItem);
        onEvalStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        onEvalStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** ON EVALUATION STATE **********************************//
    private void acceptDate(String pWorkOrderID,Long pInspectionDate,Integer pInspectionCharges){
        Long mStateChangeDate=Utils.setDateToLong(new Date());
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setInspectionDate(pInspectionDate);
        WO.setInspectionCharges(pInspectionCharges);
        WO.setState("PLANNED");
        WO.setStateChangeDate(mStateChangeDate);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** PLANNED STATE **********************************//
    private void plannedStateWorkOrder(WorkOrder pWorkOrder) {
        PlannedState mPlannedStateItem = new PlannedState(requireContext());

        mPlannedStateItem.setProviderName("Nombre: " + pWorkOrder.getProviderName());
        mPlannedStateItem.setProviderAddress("Direccion: "+ pWorkOrder.getProviderAddress());
        mPlannedStateItem.setProviderPhone("Telefono: "+ pWorkOrder.getProviderPhoneNumber());

        mPlannedStateItem.setMeetDate("Fecha de Cita: "+Utils.setLongToDate(pWorkOrder.getTimeLimit()));
        mPlannedStateItem.setMeetTime("Hora de Cita: "+pWorkOrder.getSpecialization());
        mPlannedStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getDescription());

        mPlannedStateItem.setGenPaymentOrderButtonOCL(v -> { Log.d(TAG1, "BOTON GENERAR ORDEN DE PAGO PRESIONADO");    });
        mPlannedStateItem.setAcceptButtonOCL(v -> { Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");    });
        mPlannedStateItem.setRejectButtonOCL(v -> {  Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");   });
        mPlannedStateItem.setInputLayoutEndIconOCL(v -> {  Log.d(TAG1, "BOTON COPIAR INVOICE PRESIONADO");   });

        plannedStateDetail_LinearLayout.addView(mPlannedStateItem);
        plannedStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        plannedStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** PLANNED STATE **********************************//

    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveChangesButton:
                mMainActivityViewModel.setSelectedFragment(2,View.VISIBLE);
                break;
        }
    }
}