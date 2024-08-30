package com.example.whodo.app.features.activity.workOrder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.app.Callback;
import com.example.whodo.app.MainActivity;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.features.activity.workOrder.workOrderState.ConfState;
import com.example.whodo.app.features.activity.workOrder.workOrderState.DiagState;
import com.example.whodo.app.features.activity.workOrder.workOrderState.DoneState;
import com.example.whodo.app.features.activity.workOrder.workOrderState.OnEvalState;
import com.example.whodo.app.features.activity.workOrder.workOrderState.OnProgState;
import com.example.whodo.app.features.activity.workOrder.workOrderState.OpenState;
import com.example.whodo.app.features.activity.workOrder.workOrderState.PlannedState;
import com.example.whodo.app.features.activity.workOrder.workOrderState.ClosedState;
import com.example.whodo.app.features.hire.HireFragmentViewModel;
import com.example.whodo.app.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class WorkOrderFragment extends Fragment {
    private final String TAG1 = "WORK-ORDER-LIFECYCLE-FRAG";
    private HireFragmentViewModel mHireFragmentViewModel;
    private MainActivityViewModel mMainActivityViewModel;
    private NestedScrollView work0rderStates_scrollView;
    private LinearLayout workOrderStates_LinearLayout;
    private LinearLayout openStateDetail_LinearLayout;
    private LinearLayout onEvalStateDetail_LinearLayout;
    private LinearLayout plannedStateDetail_LinearLayout;
    private LinearLayout confStateDetail_LinearLayout;
    private LinearLayout diagStateDetail_LinearLayout;
    private LinearLayout onProgStateDetail_LinearLayout;
    private LinearLayout doneStateDetail_LinearLayout;
    private LinearLayout closedStateDetail_LinearLayout;
    private View openStateDetail_vertLine;
    private View onEvalStateDetail_vertLine;
    private View plannedStateDetail_vertLine;
    private View confStateDetail_vertLine;
    private View diagStateDetail_vertLine;
    private View onProgStateDetail_vertLine;
    private View doneStateDetail_vertLine;
    private View closedStateDetail_vertLine;
    private TextView orderId_label;
    private Drawable mVertLineBackGround;
    private PorterDuff.Mode mVertLineBackTintMode;
    //***************************************** Parametria, deberia levantarse de base de datos ********************//
    private Integer mWarrantyDays=7;
    private Integer mAutoClosingDays=2;
    private Integer mFeePercent=10;
    //**************************************************************************************************************//



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_work_order_lifecycle, container, false);
        mHireFragmentViewModel = new ViewModelProvider(requireActivity()).get(HireFragmentViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mMainActivityViewModel.getPickedWorkOrder().observe(getViewLifecycleOwner(),this::setWorkOrderView);
                
        orderId_label = root.findViewById(R.id.orderId_label);
        FloatingActionButton saveChangesButton = root.findViewById(R.id.SaveChangesButton);
        saveChangesButton.setOnClickListener(this::onClick);


        work0rderStates_scrollView=root.findViewById(R.id.work0rderStates_scrollView);
        workOrderStates_LinearLayout=root.findViewById(R.id.workOrderStates_LinearLayout);

        openStateDetail_LinearLayout=root.findViewById(R.id.openStateDetail_LinearLayout);
        openStateDetail_vertLine=root.findViewById(R.id.openStateDetail_vertLine);

        onEvalStateDetail_LinearLayout=root.findViewById(R.id.onEvalStateDetail_LinearLayout);
        onEvalStateDetail_vertLine=root.findViewById(R.id.onEvalStateDetail_vertLine);

        plannedStateDetail_LinearLayout=root.findViewById(R.id.plannedStateDetail_LinearLayout);
        plannedStateDetail_vertLine=root.findViewById(R.id.plannedStateDetail_vertLine);

        confStateDetail_LinearLayout=root.findViewById(R.id.confStateDetail_LinearLayout);
        confStateDetail_vertLine=root.findViewById(R.id.confStateDetail_vertLine);

        diagStateDetail_LinearLayout=root.findViewById(R.id.diagStateDetail_LinearLayout);
        diagStateDetail_vertLine=root.findViewById(R.id.diagStateDetail_vertLine);

        onProgStateDetail_LinearLayout=root.findViewById(R.id.onProgStateDetail_LinearLayout);
        onProgStateDetail_vertLine=root.findViewById(R.id.onProgStateDetail_vertLine);

        doneStateDetail_LinearLayout=root.findViewById(R.id.doneStateDetail_LinearLayout);
        doneStateDetail_vertLine=root.findViewById(R.id.doneStateDetail_vertLine);

        closedStateDetail_LinearLayout=root.findViewById(R.id.closedStateDetail_LinearLayout);
        closedStateDetail_vertLine=root.findViewById(R.id.closedStateDetail_vertLine);

        mVertLineBackGround = closedStateDetail_vertLine.getBackground();
        mVertLineBackTintMode = closedStateDetail_vertLine.getBackgroundTintMode();

        return root;
    }
    private void setWorkOrderView(WorkOrder pWorkOrder){
        clearWorkOrder();
        View fila;
        if(pWorkOrder==null) {
            openStateWorkOrder();
            fila = workOrderStates_LinearLayout.getChildAt(0); // Índice 2 para la tercera fila
        } else if (Objects.equals(pWorkOrder.getState(), "ONEVALUATION")) {
            String mOrderId= "ID de Orden: "+pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);
            onEvalStateWorkOrder(pWorkOrder);
            fila = workOrderStates_LinearLayout.getChildAt(2); // Índice 2 para la tercera fila
        } else if (Objects.equals(pWorkOrder.getState(), "PLANNED")){
            String mOrderId= "ID de Orden: "+pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);
            plannedStateWorkOrder(pWorkOrder);
            fila = workOrderStates_LinearLayout.getChildAt(4); // Índice 2 para la tercera fila
        } else if (Objects.equals(pWorkOrder.getState(), "CONFIRMED")){
            String mOrderId= "ID de Orden: "+pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);
            confStateWorkOrder(pWorkOrder);
            fila = workOrderStates_LinearLayout.getChildAt(6); // Índice 2 para la tercera fila
        } else if (Objects.equals(pWorkOrder.getState(), "DIAGNOSED")){
            String mOrderId= "ID de Orden: "+pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);
            diagStateWorkOrder(pWorkOrder);
            fila = workOrderStates_LinearLayout.getChildAt(8); // Índice 2 para la tercera fila
        } else if (Objects.equals(pWorkOrder.getState(), "ONPROGRESS")){
            String mOrderId= "ID de Orden: "+pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);
            onProgStateWorkOrder(pWorkOrder);
            fila = workOrderStates_LinearLayout.getChildAt(10); // Índice 2 para la tercera fila
        } else if (Objects.equals(pWorkOrder.getState(), "DONE")){
            String mOrderId= "ID de Orden: "+pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);
            doneStateWorkOrder(pWorkOrder);
            fila = workOrderStates_LinearLayout.getChildAt(12); // Índice 2 para la tercera fila
        } else if (Objects.equals(pWorkOrder.getState(), "CLOSED")){
            String mOrderId= "ID de Orden: "+pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);
            closedStateWorkOrder(pWorkOrder);
            fila = workOrderStates_LinearLayout.getChildAt(14); // Índice 2 para la tercera fila
        } else {
            fila = null;
        }
        work0rderStates_scrollView.post(() -> {
            assert fila != null;
            work0rderStates_scrollView.smoothScrollTo(0, fila.getTop());
        });
    }
    private void clearWorkOrder(){
        ViewGroup celdaEspecifica;
        for(int i = 1; i < 16; i += 2){
            celdaEspecifica = (ViewGroup) workOrderStates_LinearLayout.getChildAt(i);
            if(celdaEspecifica.getChildCount()>1) {
                celdaEspecifica.removeViewAt(1);
                celdaEspecifica.getChildAt(0).setBackground(mVertLineBackGround);
                celdaEspecifica.getChildAt(0).setBackgroundTintMode(mVertLineBackTintMode);
            }
            //Log.d(TAG1, "Iteracion -->"+i);
        }

    }
    //********************************** OPEN STATE **********************************//
    private void openStateWorkOrder () {
        OpenState mOpenStateItem = new OpenState(requireContext());
        String[] mProviderServices = Objects.requireNonNull(mHireFragmentViewModel.getPickedProvider().getValue()).getSpecialization().split(",");
        User mPickedProvider  = mHireFragmentViewModel.getPickedProvider().getValue();
        User mLoggedUser = mMainActivityViewModel.getLoggedUser().getValue();

        mOpenStateItem.setProviderName("Nombre: "+mPickedProvider.getName());
        mOpenStateItem.setProviderAddress("Direccion: "+mPickedProvider.getAddress());
        mOpenStateItem.setProviderPhone("Telefono: "+mPickedProvider.getPhone_ccn()+" "+mPickedProvider.getPhone());
        mOpenStateItem.setSpinnerValues(mProviderServices);

        mOpenStateItem.setLimitDateListener(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mOpenStateItem.setTimeLimit(s);
            }
            @Override
            public void onError(Exception e) { }
        }));


        mOpenStateItem.setOnClickListener(v -> {

            String mTimeLimitDate = Utils.getISOLocalDateFromString(mOpenStateItem.getTimeLimit(),"00:00:00");
            String mCreationDate= Utils.getISOLocalDate();

            assert mLoggedUser != null;
            assignOrder(mLoggedUser,
                    mPickedProvider,
                    mOpenStateItem.getCategoryValue(),
                    mOpenStateItem.getDescriptionValue(),
                    mCreationDate,
                    mTimeLimitDate);
        });

        openStateDetail_LinearLayout.addView(mOpenStateItem);
        openStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        openStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** OPEN STATE **********************************//
    private void assignOrder(User pCustomer,User pProvider ,String pCategory,String pDescription,String pCreationDate ,String pLimitDate){

        String mStateChangeDate= Utils.getISOLocalDate();

        String mCustomerPhoneNumber = pCustomer.getPhone_ccn() + pCustomer.getPhone();
        String mProviderPhoneNumber = pProvider.getPhone_ccn() + pProvider.getPhone();

        WorkOrder WO = new WorkOrder(pCustomer.getUid(),pCustomer.getName(),pCustomer.getAddress(),pCustomer.getLatitude(),pCustomer.getLongitude(),mCustomerPhoneNumber,
                                     pProvider.getUid(),pProvider.getName(),pProvider.getAddress(),pProvider.getLatitude(),pProvider.getLongitude(),mProviderPhoneNumber,
                                     pCategory, pDescription,pCreationDate,pLimitDate,mStateChangeDate);
        mMainActivityViewModel.createWorkOrder(WO);
        mMainActivityViewModel.setSelectedTab(2);
        Log.d(TAG1, "BOTON CREAR ORDEN PRESIONADO");
    }
    //********************************** ON EVALUATION STATE **********************************//
    private void onEvalStateWorkOrder(WorkOrder pWorkOrder) {
        OnEvalState mOnEvalStateItem = new OnEvalState(requireContext());

        mOnEvalStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mOnEvalStateItem.setCustomerAddress("Direccion: "+ pWorkOrder.getCustomerAddress());
        mOnEvalStateItem.setCustomerPhone("Telefono: "+ pWorkOrder.getCustomerPhoneNumber());

        mOnEvalStateItem.setLimitDate("Fecha limite: "+ Utils.getISOtoDate(pWorkOrder.getTimeLimit()));
        mOnEvalStateItem.setCategory("Categoria: "+pWorkOrder.getSpecialization());
        mOnEvalStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
                if (Objects.equals(pWorkOrder.getCustomerId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
                    //mOnEvalStateItem.disableEdition();
                }
        }
        else {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Información Importante")
                        .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                        .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                        .show();
            closeWorkOrderLifeCycle();
        }

        final int[] mInspectionFeeValue = {0};
        mOnEvalStateItem.setMeetDateTCL(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                int mSValue;
                if(s.toString().isEmpty()){
                    mSValue=0;
                } else {
                    mSValue=Integer.parseInt(s.toString());
                }
                Log.d(TAG1, "Comision Fee: "+(mFeePercent/100.0));
                Log.d(TAG1, "Comision : "+ mSValue *(mFeePercent/100.0));
                mInspectionFeeValue[0] = (int) (mSValue*(mFeePercent/100.0));
                mOnEvalStateItem.setMeetFee("Comision Plataforma: "+mInspectionFeeValue[0]+"sat");
            }
        });
        mOnEvalStateItem.setMeetDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mOnEvalStateItem.setMeetDate(s);
            }
            @Override
            public void onError(Exception e) { }
        }));
        mOnEvalStateItem.setMeetTimeOCL(v -> showTimePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mOnEvalStateItem.setMeetTime(s);
            }
            @Override
            public void onError(Exception e) { }
        }));
        mOnEvalStateItem.setAcceptButtonOCL(v -> {

            if(mOnEvalStateItem.getMeetDate().isEmpty() || mOnEvalStateItem.getMeetTime().isEmpty() ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Información Requerida")
                        .setMessage("Es necesario cargar todos los campos, excepto aquellos marcados como Opcional")
                        .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                        .show();
            }
            else {
                int mInspectionCharges=0;
                if (!mOnEvalStateItem.getMeetTariff().isEmpty()){
                    mInspectionCharges= Integer.parseInt(mOnEvalStateItem.getMeetTariff());
                }
                String mInspectionDate = Utils.getISOLocalDateFromString(mOnEvalStateItem.getMeetDate(),mOnEvalStateItem.getMeetTime());
                int mInspectionFee = (int) (mInspectionCharges*(mFeePercent/100.0));

                String mNow= Utils.getISOLocalDate();

                Log.d(TAG1, "mNow: "+mNow);
                Log.d(TAG1, "mInspectionDate: "+ mInspectionDate);
                if (Utils.isAfter(mInspectionDate,mNow) ) {
                    Log.d(TAG1, "mInspectionCharges: "+mInspectionCharges);
                    Log.d(TAG1, "mInspectionFee: "+ mInspectionFee);
                    Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");
                    planDate(pWorkOrder.getOrderId(), mInspectionDate, mInspectionCharges, mInspectionFee);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Información Importante")
                            .setMessage("La fecha de INICIO de trabajo no puede ser menor a la fecha de HOY y la fecha de FIN no puede ser menor a la fecha de INICIO.")
                            .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                            .show();
                }
            }

        });
        mOnEvalStateItem.setRejectButtonOCL(v -> {  Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");   });

        onEvalStateDetail_LinearLayout.addView(mOnEvalStateItem);
        onEvalStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        onEvalStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** ON EVALUATION STATE **********************************//
    private void planDate(String pWorkOrderID,String pInspectionDate,Integer pInspectionCharges,Integer pInspectionFee){
        String mStateChangeDate= Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setInspectionDate(pInspectionDate);
        WO.setInspectionCharges(pInspectionCharges);
        WO.setInspectionFee(pInspectionFee);
        WO.setState("PLANNED");
        WO.setStateChangeDate(mStateChangeDate);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** PLANNED STATE **********************************//
    private void plannedStateWorkOrder(WorkOrder pWorkOrder) {
        PlannedState mPlannedStateItem = new PlannedState(requireContext());
        String mInspectionDate = Utils.getISOtoDate(pWorkOrder.getInspectionDate()) ;

        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
            if (Objects.equals(pWorkOrder.getProviderId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
                //mPlannedStateItem.disableEdition();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Información Importante")
                    .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                    .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                    .show();
            closeWorkOrderLifeCycle();
        }

        mPlannedStateItem.setProviderName("Nombre: " + pWorkOrder.getProviderName());
        mPlannedStateItem.setProviderAddress("Direccion: "+ pWorkOrder.getProviderAddress());
        mPlannedStateItem.setProviderPhone("Telefono: "+ pWorkOrder.getProviderPhoneNumber());

        mPlannedStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 11));
        mPlannedStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(13, 20));
        mPlannedStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspectionCharges()+"sat");
        mPlannedStateItem.setMeetFee("Comision Plataforma: " + pWorkOrder.getInspectionFee()+"sat");

        mPlannedStateItem.setGenPaymentOrderButtonOCL(v -> { Log.d(TAG1, "BOTON GENERAR ORDEN DE PAGO PRESIONADO");    });
        mPlannedStateItem.setAcceptButtonOCL(v -> {
            confirmDate(pWorkOrder.getOrderId(),pWorkOrder.getInspectionPaymentOrder());
            Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");
        });

        mPlannedStateItem.setRejectButtonOCL(v -> {  Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");   });
        mPlannedStateItem.setInputLayoutEndIconOCL(v -> {  Log.d(TAG1, "BOTON COPIAR INVOICE PRESIONADO");   });

        plannedStateDetail_LinearLayout.addView(mPlannedStateItem);
        plannedStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        plannedStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** PLANNED STATE **********************************//
    private void confirmDate(String pWorkOrderID,String pPaymentOrderID){
        String mStateChangeDate= Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setInspectionPaymentOrder("pPaymentOrderID-123");
        WO.setState("CONFIRMED");
        WO.setStateChangeDate(mStateChangeDate);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** CONFIRMED STATE **********************************//
    private void confStateWorkOrder(WorkOrder pWorkOrder) {
        ConfState mConfStateItem = new ConfState(requireContext());
        String mInspectionDate = Utils.getISOtoDate(pWorkOrder.getInspectionDate()) ;

        mConfStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mConfStateItem.setCustomerAddress("Direccion: "+ pWorkOrder.getCustomerAddress());
        mConfStateItem.setCustomerPhone("Telefono: "+ pWorkOrder.getCustomerPhoneNumber());

        mConfStateItem.setCategory("Categoria: "+pWorkOrder.getSpecialization());
        mConfStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        mConfStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 11));
        mConfStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(13, 20));

        mConfStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspectionCharges()+"sat");
        mConfStateItem.setMeetFee("Comision Plataforma: " + pWorkOrder.getInspectionFee()+"sat");

        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
            if (Objects.equals(pWorkOrder.getCustomerId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
               //mConfStateItem.disableEdition();
            }
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Información Importante")
                    .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                    .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                    .show();
            closeWorkOrderLifeCycle();
        }

        final Integer[] mWorkFeeValue = {0};
        final Integer[] mWorkLaborCostValue = {0};
        final Integer[] mWorkMaterialsCostValue={0};

        mConfStateItem.setWorkLaborCostTCL(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    mWorkLaborCostValue[0]=0;
                } else {
                    mWorkLaborCostValue[0] = Integer.parseInt(s.toString());
                }
                Log.d(TAG1, "Comision Fee: "+(mFeePercent/100.0));
                Log.d(TAG1, "Comision Mano de obra : "+ mWorkLaborCostValue[0] *(mFeePercent/100.0));

                mWorkFeeValue[0] = (int) ((mWorkLaborCostValue[0]+mWorkMaterialsCostValue[0])*(mFeePercent/100.0));
                mConfStateItem.setJobFee("Comision Plataforma: "+ mWorkFeeValue[0] +"sat");
            }
        });
        mConfStateItem.setWorkMaterialsCostTCL(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    mWorkMaterialsCostValue[0]=0;
                } else {
                    mWorkMaterialsCostValue[0] = Integer.parseInt(s.toString());
                }
                Log.d(TAG1, "Comision Fee: "+(mFeePercent/100.0));
                Log.d(TAG1, "Comision Materiales : "+ mWorkMaterialsCostValue[0] *(mFeePercent/100.0));

                mWorkFeeValue[0] = (int) ((mWorkLaborCostValue[0]+mWorkMaterialsCostValue[0])*(mFeePercent/100.0));

                mConfStateItem.setJobFee("Comision Plataforma: "+ mWorkFeeValue[0] +"sat");
            }
        });
        mConfStateItem.setWorkStartDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkStartDate(s);
            }
            @Override
            public void onError(Exception e) { }
        }));
        mConfStateItem.setWorkStartDateTimeOCL(v -> showTimePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkStartDateTime(s);
            }
            @Override
            public void onError(Exception e) { }
        }));
        mConfStateItem.setWorkEndDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkEndDate(s);
            }
            @Override
            public void onError(Exception e) { }
        }));
        mConfStateItem.setWorkEndDateTimeOCL(v -> showTimePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkEndDateTime(s);
            }
            @Override
            public void onError(Exception e) { }
        }));

        mConfStateItem.setPresentOrderButtonOCL(v -> {
            if(mConfStateItem.getWorkStartDate().isEmpty() || mConfStateItem.getWorkEndDate().isEmpty() || mConfStateItem.getWorkJobCost().isEmpty() || mConfStateItem.getWorkTaskDetail().isEmpty() ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Información Requerida")
                        .setMessage("Es necesario cargar todos los campos, excepto aquellos marcados como Opcional")
                        .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                        .show();
            }else{
                int mWorkMaterialsCost=0;
                if (!mConfStateItem.getWorkMaterialCost().isEmpty()){
                    mWorkMaterialsCost= Integer.parseInt(mConfStateItem.getWorkMaterialCost());
                }
                String mWorkStartDate =  Utils.getISOLocalDateFromString(mConfStateItem.getWorkStartDate(),mConfStateItem.getWorkStartDateTime());
                String mWorkEndDate = Utils.getISOLocalDateFromString(mConfStateItem.getWorkEndDate(),mConfStateItem.getWorkEndDateTime());
                int mWorkLaborCost = Integer.parseInt(mConfStateItem.getWorkJobCost());
                int mWorkFee= (int) ((mWorkLaborCost + mWorkMaterialsCost)*(mFeePercent/100.0));
                String mWorkTaskDetail = mConfStateItem.getWorkTaskDetail();

                String mToday;
                try {
                    mToday=Utils.getISOLocalDate().substring(0,10)+"T00:00:00.000"+Utils.getISOLocalDate().substring(23,29);
                } catch (Exception e){
                    mToday=Utils.getISOLocalDate().substring(0,10)+"T00:00:00.000Z";
                    Log.d(TAG1, "mWorkStartDate :" + mWorkStartDate );
                }

                Log.d(TAG1, "mToday :" + mToday );

                if (Utils.isAfter(mWorkStartDate,mToday) && Utils.isAfter(mWorkEndDate,mToday) && Utils.isAfter(mWorkEndDate,mWorkStartDate) ) {
                    Log.d(TAG1, "mWorkStartDate :" + mWorkStartDate );
                    Log.d(TAG1, "mWorkEndDate :" + mWorkEndDate );
                    Log.d(TAG1, "Current Date :" + Utils.getISOLocalDate());
                    Log.d(TAG1, "mWorkFee :" + mWorkFee);
                    Log.d(TAG1, "BOTON PRESENTAR ORDEN PRESIONADO");
                    diagnoseOrder(pWorkOrder.getOrderId(),mWorkStartDate,mWorkEndDate,mWorkLaborCost,mWorkMaterialsCost,mWorkFee,mWorkTaskDetail);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Información Importante")
                            .setMessage("La fecha de INICIO de trabajo no puede ser menor a la fecha de HOY y la fecha de FIN no puede ser menor a la fecha de INICIO.")
                            .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                            .show();
                }

            }

        } );

        confStateDetail_LinearLayout.addView(mConfStateItem);
        confStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        confStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** CONFIRMED STATE **********************************//
    private void diagnoseOrder(String pWorkOrderID,String pWorkStartDate, String pWorkEndDate, Integer pWorkLaborCost,Integer pWorkMaterialsCost,Integer pWorkFee, String pWorkDetail){
        String mStateChangeDate= Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("DIAGNOSED");
        WO.setStateChangeDate(mStateChangeDate);
        WO.setWorkStartDate(pWorkStartDate);
        WO.setWorkEndDate(pWorkEndDate);
        WO.setWorkLaborCost(pWorkLaborCost);
        WO.setWorkMaterialsCost(pWorkMaterialsCost);
        WO.setWorkFee(pWorkFee);
        WO.setDetail(pWorkDetail);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** DIAGNOSE STATE **********************************//
    private void diagStateWorkOrder(WorkOrder pWorkOrder) {
        DiagState mDiagStateItem = new DiagState(requireContext());
        String mWorkStartDate = Utils.getISOtoDate(pWorkOrder.getWorkStartDate());
        String mWorkEndDate = Utils.getISOtoDate(pWorkOrder.getWorkEndDate());

        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
            if (Objects.equals(pWorkOrder.getProviderId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
                //mDiagStateItem.disableEdition();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Información Importante")
                    .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                    .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                    .show();
            closeWorkOrderLifeCycle();
        }

        mDiagStateItem.setProviderName("Nombre: " + pWorkOrder.getProviderName());
        mDiagStateItem.setProviderAddress("Direccion: " + pWorkOrder.getProviderAddress());
        mDiagStateItem.setProviderPhone("Telefono: " + pWorkOrder.getProviderPhoneNumber());

        mDiagStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate);
        mDiagStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate);
        mDiagStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getDetail());
        mDiagStateItem.setJobCost("Costo Mano de Obra: "+ pWorkOrder.getWorkLaborCost()+"sat");
        mDiagStateItem.setMaterialCost("Costo Materiales: " + pWorkOrder.getWorkMaterialsCost()+"sat");
        mDiagStateItem.setJobFee("Comision de Plataforma: " + pWorkOrder.getWorkFee()+"sat");

        mDiagStateItem.setAcceptButtonOCL(v -> {
            acceptContract(pWorkOrder.getOrderId(),
                    pWorkOrder.getWorkPaymentOrder()
                    );
            Log.d(TAG1, "BOTON GENERAR ORDEN DE PAGO PRESIONADO");    });
        mDiagStateItem.setGenPaymentOrderButtonOCL(v -> {  Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");    } );
        mDiagStateItem.setRejectButtonOCL(v -> {  Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");   });
        mDiagStateItem.setInputLayoutEndIconOCL(v -> {  Log.d(TAG1, "BOTON COPIAR INVOICE PRESIONADO");   });

        diagStateDetail_LinearLayout.addView(mDiagStateItem);
        diagStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        diagStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** DIAGNOSE STATE **********************************//
    private void acceptContract(String pWorkOrderID, String pWorkPaymentOrderID){
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("ONPROGRESS");
        WO.setStateChangeDate(mStateChangeDate);
//        WO.setWorkStartDate(pWorkStartDate);
//        WO.setWorkEndDate(pWorkEndDate);
//        WO.setWorkCost(pWorkCost);
//        WO.setDetail(pWorkDetail);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** ON PROGRESS STATE **********************************//
    private void onProgStateWorkOrder(WorkOrder pWorkOrder) {
        OnProgState mOnProgStateItem = new OnProgState(requireContext());
        String mWorkStartDate = Utils.getISOtoDate(pWorkOrder.getWorkStartDate());
        String mWorkEndDate = Utils.getISOtoDate(pWorkOrder.getWorkEndDate());
        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
            if (Objects.equals(pWorkOrder.getCustomerId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
                //mOnProgStateItem.disableEdition();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Información Importante")
                    .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                    .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                    .show();
            closeWorkOrderLifeCycle();
        }

        mOnProgStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mOnProgStateItem.setCustomerAddress("Direccion: " + pWorkOrder.getCustomerAddress());
        mOnProgStateItem.setCustomerPhone("Telefono: " + pWorkOrder.getCustomerPhoneNumber());

        mOnProgStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate);
        mOnProgStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate);
        mOnProgStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getDetail());

        mOnProgStateItem.setJobCost("Costo Mano de Obra: "+ pWorkOrder.getWorkLaborCost()+"sat");
        mOnProgStateItem.setMaterialCost("Costo Materiales: " + pWorkOrder.getWorkMaterialsCost()+"sat");
        mOnProgStateItem.setJobFee("Comision de Plataforma: " + pWorkOrder.getWorkFee()+"sat");


        mOnProgStateItem.setFinishWorkOrderButtonOCL(v -> {
            String mClosingDate = Utils.getISOLocalDate();
            String mBiggerDate = Utils.getBiggerISODate(pWorkOrder.getWorkEndDate(),mClosingDate);
            String mWarrantyDate = Utils.getISOLocalDatePlus(mWarrantyDays,mBiggerDate);

            finishOrder(pWorkOrder.getOrderId(),
                        mWarrantyDate);
            Log.d(TAG1, "BOTON FINALIZAR ORDEN DE PAGO PRESIONADO");    });

        onProgStateDetail_LinearLayout.addView(mOnProgStateItem);
        onProgStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        onProgStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** ON PROGRESS STATE **********************************//
    private void finishOrder(String pWorkOrderID,String pWarrantyEndDate){
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("DONE");
        WO.setStateChangeDate(mStateChangeDate);
        WO.setWorkWarrantyEndDate(pWarrantyEndDate);

        mMainActivityViewModel.updateWorkOrder(WO);
    }

    //********************************** DONE STATE **********************************//
    private void doneStateWorkOrder(WorkOrder pWorkOrder) {
        DoneState mDoneStateItem = new DoneState(requireContext());


        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
            if (Objects.equals(pWorkOrder.getProviderId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
                //mDoneStateItem.disableEdition();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Información Importante")
                    .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                    .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                    .show();
            closeWorkOrderLifeCycle();
        }
        String mWorkWarrantyEndDate = Utils.getISOtoDate(pWorkOrder.getWorkWarrantyEndDate());
        String mAutoClosingDate = Utils.getISOLocalDatePlus(mAutoClosingDays,pWorkOrder.getStateChangeDate());
        String mReviewWarrantyWarning = getString(R.string.WorkOrderLifeCycleFrag_DoneState_ReviewWarrantyWarning,mWorkWarrantyEndDate);
        String mReviewClosingWarning = getString(R.string.WorkOrderLifeCycleFrag_DoneState_ReviewClosingWarning,Utils.getISOtoDate(mAutoClosingDate));

        String mNowDate = Utils.getISOLocalDate();
        String mCheckDate = Utils.getBiggerISODate(mNowDate,mAutoClosingDate);
        //Se verifica si la fecha de autocierre aun esta vigente, de lo contrario se bloquea la orden
        //Al final del dia, un proceso batch colocara la orden en estado cerrado finalizando el proceso automaticamente
        if (Objects.equals(mCheckDate, mNowDate)) {
            mDoneStateItem.disableEdition();
            mReviewClosingWarning = getString(R.string.WorkOrderLifeCycleFrag_DoneState_ReviewClosingWarning_OutOfDate,Utils.getISOtoDate(mAutoClosingDate));
        }
        mDoneStateItem.setReviewClosingWarning(mReviewClosingWarning);
        mDoneStateItem.setReviewWarrantyWarning(mReviewWarrantyWarning);
        
        mDoneStateItem.setAcceptButtonOCL(v -> {
            closeOrder(pWorkOrder.getOrderId(),
                    mDoneStateItem.getProviderAppereanceScore(),
                    mDoneStateItem.getProviderCleanlinessScore(),
                    mDoneStateItem.getProviderSpeedScore(),
                    mDoneStateItem.getProviderQualityScoreScore(),
                    mDoneStateItem.getProviderReview());
            Log.d(TAG1, "BOTON FINALIZAR ORDEN DE PAGO PRESIONADO");    });

        doneStateDetail_LinearLayout.addView(mDoneStateItem);
        doneStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        doneStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** DONE STATE **********************************//
    private void closeOrder(String pWorkOrderID,Integer pAppereanceScore ,Integer pCleanlinessScore,Integer pSpeedScore,Integer pQualityScore,String pImpressions){
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("CLOSED");
        WO.setStateChangeDate(mStateChangeDate);
        WO.setAppereanceScore(pAppereanceScore);
        WO.setCleanlinessScore(pCleanlinessScore);
        WO.setSpeedScore(pSpeedScore);
        WO.setQualityScore(pQualityScore);
        WO.setImpressions(pImpressions);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** CLOSED STATE **********************************//
    private void closedStateWorkOrder(WorkOrder pWorkOrder) {
        ClosedState mClosedStateItem = new ClosedState(requireContext());
        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
            if (Objects.equals(pWorkOrder.getCustomerId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
                //mClosedStateItem.disableEdition();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Información Importante")
                    .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                    .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                    .show();
            closeWorkOrderLifeCycle();
        }
        String mWorkStartDate = Utils.getISOtoDate(pWorkOrder.getWorkStartDate());
        String mWorkEndDate = Utils.getISOtoDate(pWorkOrder.getWorkEndDate());
        String mInspectionDate = Utils.getISOtoDate(pWorkOrder.getInspectionDate());
        String mWarrantyDate = Utils.getISOtoDate(pWorkOrder.getWorkWarrantyEndDate());

        mClosedStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mClosedStateItem.setCustomerAddress("Direccion: " + pWorkOrder.getCustomerAddress());
        mClosedStateItem.setCustomerPhone("Telefono: " + pWorkOrder.getCustomerPhoneNumber());

        mClosedStateItem.setProviderName("Nombre: " + pWorkOrder.getProviderName());
        mClosedStateItem.setProviderAddress("Direccion: " + pWorkOrder.getProviderAddress());
        mClosedStateItem.setProviderPhone("Telefono: " + pWorkOrder.getProviderPhoneNumber());

        mClosedStateItem.setCategory("Categoria: "+pWorkOrder.getSpecialization());
        mClosedStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        mClosedStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 11));
        mClosedStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(13, 20));

        mClosedStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspectionCharges()+"sat");
        mClosedStateItem.setMeetFee("Comision Plataforma: " + pWorkOrder.getInspectionFee()+"sat");

        mClosedStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate);
        mClosedStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate);
        mClosedStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getDetail());

        mClosedStateItem.setJobCost("Costo Mano de Obra: "+ pWorkOrder.getWorkLaborCost()+"sat");
        mClosedStateItem.setMaterialCost("Costo Materiales: " + pWorkOrder.getWorkMaterialsCost()+"sat");
        mClosedStateItem.setJobFee("Comision de Plataforma: " + pWorkOrder.getWorkFee()+"sat");


        mClosedStateItem.setProviderAppereanceScore(pWorkOrder.getAppereanceScore());
        mClosedStateItem.setProviderCleanlinessScore(pWorkOrder.getCleanlinessScore());
        mClosedStateItem.setProviderSpeedScore(pWorkOrder.getSpeedScore());
        mClosedStateItem.setProviderQualityScore(pWorkOrder.getQualityScore());

        mClosedStateItem.setProviderReview("Reseña del cliente:\n" + pWorkOrder.getImpressions());
        mClosedStateItem.setWarrantyEndDate("Fecha limite de Garantia: " + mWarrantyDate);

        mClosedStateItem.setComplainButtonOCL(v -> { Log.d(TAG1, "BOTON FINALIZAR ORDEN DE PAGO PRESIONADO");    });

        closedStateDetail_LinearLayout.addView(mClosedStateItem);
        closedStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        closedStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** CLOSED STATE **********************************//


    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveChangesButton:
                closeWorkOrderLifeCycle();
                break;
        }
    }
    private void closeWorkOrderLifeCycle(){
        Integer mSelectedTab = mMainActivityViewModel.getSelectedTab().getValue();
        if (mSelectedTab == 0) {
            mMainActivityViewModel.setSelectedFragment(0, View.VISIBLE);
        } else {
            mMainActivityViewModel.setSelectedFragment(2, View.VISIBLE);
        }
    }

    private void showDatePickerDialog(Callback<String> pCallback) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String mDate = "01/01/1900";

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
                    // Formatear la fecha seleccionada y mostrarla en el EditText
                    String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month1 + 1, year1);
                    pCallback.onSuccess(fechaSeleccionada);},
                year, month, day);

        datePickerDialog.show();
    }
    private void showTimePickerDialog(Callback<String> pCallback) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute1) -> {
                    // Formatear la hora seleccionada y mostrarla en el EditText
                    String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute1);
                    pCallback.onSuccess(horaSeleccionada);
                },
                hour, minute, true // true para formato de 24 horas
        );

        timePickerDialog.show();
    }


}
