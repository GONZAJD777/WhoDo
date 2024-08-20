package com.example.whodo.app.features.activity.workOrder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
            assignOrder(mLoggedUser,
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
    private void assignOrder(User pCustomer,User pProvider ,String pCategory,String pDescription,Integer pLimitValue){
        Long mTimeLimitDate = Utils.setDateToLong(Utils.increseDate(pLimitValue, new Date()));
        Long mCreationDate= Utils.setDateToLong(new Date());
        Long mStateChangeDate=Utils.setDateToLong(new Date());

        String mCustomerPhoneNumber = pCustomer.getPhone_ccn() + pCustomer.getPhone();
        String mProviderPhoneNumber = pProvider.getPhone_ccn() + pProvider.getPhone();

        WorkOrder WO = new WorkOrder(pCustomer.getUid(),pCustomer.getName(),pCustomer.getAddress(),pCustomer.getLatitude(),pCustomer.getLongitude(),mCustomerPhoneNumber,
                                     pProvider.getUid(),pProvider.getName(),pProvider.getAddress(),pProvider.getLatitude(),pProvider.getLongitude(),mProviderPhoneNumber,
                                     pCategory, pDescription, mCreationDate,mTimeLimitDate,mStateChangeDate);
        mMainActivityViewModel.createWorkOrder(WO);
        mMainActivityViewModel.setSelectedTab(2);
        Log.d(TAG1, "BOTON CREAR ORDEN PRESIONADO");
    }
    //********************************** ON EVALUATION STATE **********************************//
    private void onEvalStateWorkOrder(WorkOrder pWorkOrder) {
        OnEvalState mOnEvalStateItem = new OnEvalState(requireContext());

        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
                if (Objects.equals(pWorkOrder.getCustomerId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
                    //mOnEvalStateItem.disableEdition();
                }
        }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Información Importante")
                        .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                        .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                        .show();
            closeWorkOrderLifeCycle();
        }

        mOnEvalStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mOnEvalStateItem.setCustomerAddress("Direccion: "+ pWorkOrder.getCustomerAddress());
        mOnEvalStateItem.setCustomerPhone("Telefono: "+ pWorkOrder.getCustomerPhoneNumber());

        mOnEvalStateItem.setLimitDate("Fecha limite: "+Utils.setLongToDate(pWorkOrder.getTimeLimit()));
        mOnEvalStateItem.setCategory("Categoria: "+pWorkOrder.getSpecialization());
        mOnEvalStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        //Esto debe cambiar dinamicamente cuando se ingresa un valor en los campos
        //configurar OnTextChangeListener
        mOnEvalStateItem.setMeetFee("Comision Plataforma: 500sat");

        mOnEvalStateItem.setAcceptButtonOCL(v -> {
            Long mInspectionDate = Utils.setDateToLong(Utils.setStringToDate(mOnEvalStateItem.getMeetDate() +" "+ mOnEvalStateItem.getmeetTime()+ "hs"));
            Integer mInspectionCharges = Integer.valueOf(mOnEvalStateItem.getmeetTariff());

//            planDate(pWorkOrder.getOrderId(),
//                       mInspectionDate,
//                       mInspectionCharges);

            Log.d(TAG1, "mInspectionDate: "+mInspectionDate);
            Log.d(TAG1, "mInspectionCharges: "+mInspectionCharges);
            Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");
        });
        mOnEvalStateItem.setRejectButtonOCL(v -> {  Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");   });

        onEvalStateDetail_LinearLayout.addView(mOnEvalStateItem);
        onEvalStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        onEvalStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** ON EVALUATION STATE **********************************//
    private void planDate(String pWorkOrderID,Long pInspectionDate,Integer pInspectionCharges){
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
        String mInspectionDate = Utils.setLongToDate(pWorkOrder.getInspectionDate());

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

        mPlannedStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 10));
        mPlannedStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(11, 18));
        mPlannedStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspectionCharges()+"sat");
        mPlannedStateItem.setMeetFee("Comision Plataforma: 500sat");

        mPlannedStateItem.setGenPaymentOrderButtonOCL(v -> { Log.d(TAG1, "BOTON GENERAR ORDEN DE PAGO PRESIONADO");    });
        mPlannedStateItem.setAcceptButtonOCL(v -> {
            confirmDate(pWorkOrder.getOrderId(),
                    pWorkOrder.getInspectionPaymentOrder());
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
        Long mStateChangeDate=Utils.setDateToLong(new Date());
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
        String mInspectionDate = Utils.setLongToDate(pWorkOrder.getInspectionDate());

        if(mMainActivityViewModel.getLoggedUser().isInitialized()) {
            if (Objects.equals(pWorkOrder.getCustomerId(), mMainActivityViewModel.getLoggedUser().getValue().getUid())) {
               // mConfStateItem.disableEdition();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Información Importante")
                    .setMessage("Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.")
                    .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                    .show();
            closeWorkOrderLifeCycle();
        }

        mConfStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mConfStateItem.setCustomerAddress("Direccion: "+ pWorkOrder.getCustomerAddress());
        mConfStateItem.setCustomerPhone("Telefono: "+ pWorkOrder.getCustomerPhoneNumber());

        mConfStateItem.setCategory("Categoria: "+pWorkOrder.getSpecialization());
        mConfStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        mConfStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 10));
        mConfStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(11, 18));
        mConfStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspectionCharges()+"sat");
        mConfStateItem.setMeetFee("Comision Plataforma: 500sat");

        //Esto debe cambiar dinamicamente cuando se ingresa un valor en los campos
        //configurar OnTextChangeListener
        mConfStateItem.setJobFee("Comision Plataforma: 500sat");

        mConfStateItem.setPresentOrderButtonOCL(v -> {
            Long mWorkStartDate =  Utils.setDateToLong(Utils.setStringToDate(mConfStateItem.getWorkStartDate() +" 00:00hs"));
            Long mWorkEndDate = Utils.setDateToLong(Utils.setStringToDate(mConfStateItem.getWorkEndDate() +" 00:00hs"));
            Integer mWorkJobCost = Integer.valueOf(mConfStateItem.getWorkJobCost()+Integer.valueOf(mConfStateItem.getWorkMaterialCost()));
            String mWorkTaskDetail = mConfStateItem.getWorkTaskDetail();

            Log.d(TAG1, "mWorkStartDate :" + mWorkStartDate );
            Log.d(TAG1, "mWorkEndDate :" + mWorkEndDate );
            Log.d(TAG1, "Current Date :" + Utils.setDateToLong(new Date()));

            //diagnoseOrder(pWorkOrder.getOrderId(),mWorkStartDate,mWorkEndDate,mWorkJobCost,mWorkTaskDetail);
            Log.d(TAG1, "BOTON PRESENTAR ORDEN PRESIONADO");
        } );

        confStateDetail_LinearLayout.addView(mConfStateItem);
        confStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        confStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** CONFIRMED STATE **********************************//
    private void diagnoseOrder(String pWorkOrderID,Long pWorkStartDate, Long pWorkEndDate, Integer pWorkCost, String pWorkDetail){
        Long mStateChangeDate=Utils.setDateToLong(new Date());
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("DIAGNOSED");
        WO.setStateChangeDate(mStateChangeDate);
        WO.setWorkStartDate(pWorkStartDate);
        WO.setWorkEndDate(pWorkEndDate);
        WO.setWorkCost(pWorkCost);
        WO.setDetail(pWorkDetail);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** DIAGNOSE STATE **********************************//
    private void diagStateWorkOrder(WorkOrder pWorkOrder) {
        DiagState mDiagStateItem = new DiagState(requireContext());
        String mWorkStartDate = Utils.setLongToDate(pWorkOrder.getWorkStartDate());
        String mWorkEndDate = Utils.setLongToDate(pWorkOrder.getWorkEndDate());

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

        mDiagStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate.substring(0, 10));
        mDiagStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate.substring(0, 10));
        mDiagStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getDetail());
        mDiagStateItem.setMaterialCost("Costo Materiales: " + pWorkOrder.getWorkCost()+"sat");
        mDiagStateItem.setJobCost("Costo Mano de Obra: ");
        mDiagStateItem.setJobFee("Tarifa de Plataforma: ");

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
        Long mStateChangeDate=Utils.setDateToLong(new Date());
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
        String mWorkStartDate = Utils.setLongToDate(pWorkOrder.getWorkStartDate());
        String mWorkEndDate = Utils.setLongToDate(pWorkOrder.getWorkEndDate());
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

        mOnProgStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate.substring(0, 10));
        mOnProgStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate.substring(0, 10));
        mOnProgStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getDetail());
        mOnProgStateItem.setMaterialCost("Costo de Materiales" + pWorkOrder.getWorkCost()+"sat");
        mOnProgStateItem.setJobCost("Costo de Trabajo: 122sat");
        mOnProgStateItem.setJobFee("Comision Plataforma: 500sat");


        mOnProgStateItem.setFinishWorkOrderButtonOCL(v -> {
            finishOrder(pWorkOrder.getOrderId());
            Log.d(TAG1, "BOTON FINALIZAR ORDEN DE PAGO PRESIONADO");    });

        onProgStateDetail_LinearLayout.addView(mOnProgStateItem);
        onProgStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.dotted_line));
        onProgStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** ON PROGRESS STATE **********************************//
    private void finishOrder(String pWorkOrderID){
        Long mStateChangeDate=Utils.setDateToLong(new Date());
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("DONE");
        WO.setStateChangeDate(mStateChangeDate);
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
        Long mStateChangeDate=Utils.setDateToLong(new Date());
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
        String mWorkStartDate = Utils.setLongToDate(pWorkOrder.getWorkStartDate());
        String mWorkEndDate = Utils.setLongToDate(pWorkOrder.getWorkEndDate());
        String mInspectionDate = Utils.setLongToDate(pWorkOrder.getInspectionDate());
        String mWarrantyDate = Utils.setDateFormat(Utils.increseDate(7,Utils.setStringToDate(Utils.setLongToDate(pWorkOrder.getWorkEndDate()))), "dd-MM-yyyy HH:mm'hs'");

        mClosedStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomerName());
        mClosedStateItem.setCustomerAddress("Direccion: " + pWorkOrder.getCustomerAddress());
        mClosedStateItem.setCustomerPhone("Telefono: " + pWorkOrder.getCustomerPhoneNumber());

        mClosedStateItem.setProviderName("Nombre: " + pWorkOrder.getProviderName());
        mClosedStateItem.setProviderAddress("Direccion: " + pWorkOrder.getProviderAddress());
        mClosedStateItem.setProviderPhone("Telefono: " + pWorkOrder.getProviderPhoneNumber());

        mClosedStateItem.setCategory("Categoria: "+pWorkOrder.getSpecialization());
        mClosedStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        mClosedStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 10));
        mClosedStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(11, 18));
        mClosedStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspectionCharges()+"sat");
        mClosedStateItem.setMeetFee("Comision Plataforma: 500sat");

        mClosedStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate.substring(0, 10));
        mClosedStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate.substring(0, 10));
        mClosedStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getDetail());
        mClosedStateItem.setMaterialCost("Costo TOTAL a pagar: " + pWorkOrder.getWorkCost()+"sat");
        mClosedStateItem.setJobCost("Costo de Trabajo: 122sat");
        mClosedStateItem.setJobFee("Comision Plataforma: 500sat");

        mClosedStateItem.setProviderReview("Reseña del cliente:\n" + pWorkOrder.getImpressions());
        mClosedStateItem.setWarrantyEndDate("Fecha limite de Garantia: " + mWarrantyDate);

        //mClosedStateItem.setAcceptButtonOCL(v -> { Log.d(TAG1, "BOTON FINALIZAR ORDEN DE PAGO PRESIONADO");    });

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

}
