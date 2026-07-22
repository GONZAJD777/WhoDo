package com.example.whodo.app.features.activity.workOrder;

import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.R;
import com.example.whodo.app.Callback;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.domain.Location;
import com.example.whodo.app.domain.Phone;
import com.example.whodo.app.domain.paymentOrder.PaymentOrder;
import com.example.whodo.app.domain.paymentOrder.PaymentRequest;
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
import com.example.whodo.app.network.notifications.WorkOrderRefreshReceiver;
import com.example.whodo.app.resources.parameters.Parameter;
import com.example.whodo.app.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class WorkOrderFragment extends Fragment {
    private final String TAG1 = "LOGGER-WORK-ORDER-LIFECYCLE-FRAG";
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
    private FloatingActionButton saveChangesButton;
    private FloatingActionButton startComplaintButton;//TODO: crear la pantalla para iniciar la mediacion
    //***************************************** Parametria, deberia levantarse de base de datos ********************//
    private String[] mValidStates = {"CONFIRMED", "DIAGNOSED"}; //Estados para validar Inspeccion
    private Integer mAutoClosingDays;
    private Integer mFeePercent;
    private Integer mInspetionFeePercent;
    private Integer mInspectionMaxCost;
    private Integer mMaxWorkLimitTimeExt;
    private Integer mMaxDaysWorkEndDateExt;
    private Integer mMinDaysWorkEndDateExt;
    private Integer mMaxDaysLimitOpenState;
    private Integer mMaxDaysMeetOnEvalState;
    private Integer mMaxDaysOnConfState;
    private Integer mActivateUserTypeValidation;

    private String mSetInspectionStatusTittle="Consulta Reprogramacion de Inspeccion";
    private String mSetInspectionStatusMessage="El proveedor se reprogramo la inspeccion? \n Si respondes que NO esto se vera reflejado en el perfil del proveedor y la orden se CERRARA definitivamente." ;

    private String mValidateInspectionStatusTittle = "Consulta Sobre Inspeccion";
    private String mValidateInspectionStatusMessage = "El proveedor se presento a la cita para inspeccionar el trabajo a realizar? \n Si respondes que NO, esto se vera reflejado en su perfil para otros usuarios tengan referencia sobre su responsabilidad.";

    private String mNoDataErrorTittle ="Información Importante";
    private String mNoDataErrorMessage ="Aun no hay informacion del usuario logeado,por favor revise su conexion e intente mas tarde.";

    private String mOrderExpiredErrorTittle="Orden Expirada";
    private String mOrderExpiredErrorMessage="El plazo para la accion que intenta realizar, ha finalizado. La orden se cerrara al finalizar el dia";

    private String mEmptyFieldErrorTittle="Información Requerida";
    private String mEmptyFieldErrorMessage="Es necesario cargar todos los campos, excepto aquellos marcados como Opcional";

    private String mConfStateDatesErrorTitle ="Información Importante";
    private String mConfStateDatesErrorMessage = "La fecha de INICIO de trabajo no puede ser menor a la fecha de HOY NI LA FECHA LIMITE y la fecha de FIN no puede ser menor a la fecha de INICIO..";

    private String mOnEvalStateDatesErrorTitle ="Información Importante";
    private String mOnEvalStateDatesErrorMessage = "La fecha de INICIO de trabajo no puede ser menor a la fecha de HOY NI LA FECHA LIMITE y la fecha de FIN no puede ser menor a la fecha de INICIO..";

    private String mRejectWorkAfterInspTittle ="Consulta Sobre Inspeccion";
    private String mRejectWorkAfterInspMessage = "Estas a punto de rechazar la orden, lo que significa que has realizado la inspección y no pudiste llegar a un acuerdo con el cliente o consideras que no estas capacitado. La orden pasara a el estado CERRADO y no podrás volver a abrirla.\n ¿Deseas continuar y RECHAZAR?";

    private String mReviewWarrantyWarning = "No te preocupes, si aceptas y encuentras fallas mas tarde, tienes tiempo hasta %1$s como garantia para inciar un reclamo si no acuerdas con el proveedor.";
    private String mReviewClosingWarning = "Tienes tiempo hasta el %1$s, luego de esta fecha la orden se cerrara, se liberara el pago al proveedor y no podras iniciar reclamos por fallas.";
    private String mReviewClosingWarningOutOfDate = "Tenias tiempo hasta el %1$s, la orden ya no esta disponible para reclamos y será cerrada automaticamente.";

    private Integer mWarrantyDays;// dias otorgados como garantia una vez completada la reseña.
    private String mWarrantyMessage;
    private String mStrDiagTtlOpenState = "Fecha limite para tomar orden";
    private String mStrDiagTtlConfState = "Fecha limite para aceptar Contrato";
    private String mStrDiagTtlOnEvalState = "Fecha de Inspeccion";
    private String mStrDiagTtlLimitOnEvalState = "Fecha de Limite";
    private String mStrDiagTtlOnConfStateStartDate = "Fecha INICIO de Trabajo";
    private String mStrDiagTtlOnConfStateEndDate = "Fecha FIN de Trabajo";
    private String mStrDiagTtlDone = "Extension de plazo";
    private String mStartDayTime = "00:00:00";
    private String mEndDayTime = "23:59:59";
    private String mSaveChangesButtonColor = "#3F51B5";
    private String mStartComplaintButton ="#FFFF0000";

    //**************************************************************************************************************//
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_work_order_lifecycle, container, false);
        mHireFragmentViewModel = new ViewModelProvider(requireActivity()).get(HireFragmentViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mMainActivityViewModel.getParameters().observe(getViewLifecycleOwner(), this::setParameters);
        mMainActivityViewModel.getPickedWorkOrder().observe(getViewLifecycleOwner(), this::setWorkOrderView);
        mMainActivityViewModel.getPayment().observe(getViewLifecycleOwner(), this::redirectToPayment);
        mMainActivityViewModel.getPaymentOrder().observe(getViewLifecycleOwner(),this::verifyPaymentOrder);

        orderId_label = root.findViewById(R.id.orderId_label);
        saveChangesButton = root.findViewById(R.id.saveChangesButton);
        startComplaintButton = root.findViewById(R.id.startComplaintButton);
        saveChangesButton.setColorFilter(Color.parseColor(mSaveChangesButtonColor));
        startComplaintButton.setColorFilter(Color.parseColor(mStartComplaintButton));
        saveChangesButton.setOnClickListener(this::onClick);

        work0rderStates_scrollView = root.findViewById(R.id.work0rderStates_scrollView);
        workOrderStates_LinearLayout = root.findViewById(R.id.workOrderStates_LinearLayout);

        openStateDetail_LinearLayout = root.findViewById(R.id.openStateDetail_LinearLayout);
        openStateDetail_vertLine = root.findViewById(R.id.openStateDetail_vertLine);

        onEvalStateDetail_LinearLayout = root.findViewById(R.id.onEvalStateDetail_LinearLayout);
        onEvalStateDetail_vertLine = root.findViewById(R.id.onEvalStateDetail_vertLine);

        plannedStateDetail_LinearLayout = root.findViewById(R.id.plannedStateDetail_LinearLayout);
        plannedStateDetail_vertLine = root.findViewById(R.id.plannedStateDetail_vertLine);

        confStateDetail_LinearLayout = root.findViewById(R.id.confStateDetail_LinearLayout);
        confStateDetail_vertLine = root.findViewById(R.id.confStateDetail_vertLine);

        diagStateDetail_LinearLayout = root.findViewById(R.id.diagStateDetail_LinearLayout);
        diagStateDetail_vertLine = root.findViewById(R.id.diagStateDetail_vertLine);

        onProgStateDetail_LinearLayout = root.findViewById(R.id.onProgStateDetail_LinearLayout);
        onProgStateDetail_vertLine = root.findViewById(R.id.onProgStateDetail_vertLine);

        doneStateDetail_LinearLayout = root.findViewById(R.id.doneStateDetail_LinearLayout);
        doneStateDetail_vertLine = root.findViewById(R.id.doneStateDetail_vertLine);

        closedStateDetail_LinearLayout = root.findViewById(R.id.closedStateDetail_LinearLayout);
        closedStateDetail_vertLine = root.findViewById(R.id.closedStateDetail_vertLine);

        mVertLineBackGround = closedStateDetail_vertLine.getBackground();
        mVertLineBackTintMode = closedStateDetail_vertLine.getBackgroundTintMode();

        return root;
    }

    private void verifyPaymentOrder(PaymentOrder paymentOrder) {
        if (paymentOrder != null) {

        }
    }

    private void setParameters(List<Parameter> parameters) {
        mWarrantyDays = Integer.parseInt(Utils.findParameterById(parameters, "WO_WARRANTY_DAYS").getValue());
        mWarrantyMessage = Utils.findParameterById(parameters, "WO_WARRANTY_MESSAGE").getValue();
        mAutoClosingDays = Integer.parseInt(Utils.findParameterById(parameters, "WO_AUTO_CLOSING_DAYS").getValue());
        mFeePercent = Integer.parseInt(Utils.findParameterById(parameters, "WO_FEE_PERCENT").getValue());
        mInspetionFeePercent = Integer.parseInt(Utils.findParameterById(parameters, "WO_INSPECTION_FEE_PERCENT").getValue());
        mInspectionMaxCost = Integer.parseInt(Utils.findParameterById(parameters, "WO_INSPECTION_MAX_COST").getValue());
        mMaxWorkLimitTimeExt = Integer.parseInt(Utils.findParameterById(parameters, "WO_WORK_EXTENSION_LIMIT").getValue());
        mMaxDaysWorkEndDateExt = Integer.parseInt(Utils.findParameterById(parameters, "WO_WORK_EXT_DAYS_UPPER_LIMIT").getValue());
        mMinDaysWorkEndDateExt = Integer.parseInt(Utils.findParameterById(parameters, "WO_WORK_EXT_DAYS_LOWER_LIMIT").getValue());
        mMaxDaysLimitOpenState = Integer.parseInt(Utils.findParameterById(parameters, "WO_OPEN_STATE_LIMIT_DAYS").getValue());
        mMaxDaysMeetOnEvalState = Integer.parseInt(Utils.findParameterById(parameters, "WO_ON_EVAL_STATE_LIMIT_DAYS").getValue());
        mMaxDaysOnConfState = Integer.parseInt(Utils.findParameterById(parameters, "WO_ON_CONF_STATE_LIMIT_DAYS").getValue());
        mActivateUserTypeValidation = Integer.parseInt(Utils.findParameterById(parameters, "WO_USER_TYPE_VALIDATION").getValue());
    }
    private void setWorkOrderView(WorkOrder pWorkOrder) {
        clearWorkOrder();
        View fila = null;

        if (pWorkOrder == null) {
            openStateWorkOrder();
            startComplaintButton.setVisibility(View.INVISIBLE);
            fila = workOrderStates_LinearLayout.getChildAt(0);
        } else {
            String mOrderId = "ID de Orden: " + pWorkOrder.getOrderId();
            orderId_label.setText(mOrderId);

            String state = pWorkOrder.getState();

            if (state != null) {
                if (state.startsWith("ONEVALUATION")) {
                    onEvalStateWorkOrder(pWorkOrder);
                    startComplaintButton.setVisibility(View.INVISIBLE);
                    fila = workOrderStates_LinearLayout.getChildAt(2);

                } else if (state.startsWith("PLANNED")) {
                    plannedStateWorkOrder(pWorkOrder);
                    startComplaintButton.setVisibility(View.INVISIBLE);
                    fila = workOrderStates_LinearLayout.getChildAt(4);

                } else if (state.startsWith("CONFIRMED")) {
                    confStateWorkOrder(pWorkOrder);
                    startComplaintButton.setVisibility(View.INVISIBLE);
                    fila = workOrderStates_LinearLayout.getChildAt(6);

                } else if (state.startsWith("DIAGNOSED")) {
                    diagStateWorkOrder(pWorkOrder);
                    startComplaintButton.setVisibility(View.INVISIBLE);
                    fila = workOrderStates_LinearLayout.getChildAt(8);

                } else if (state.startsWith("ONPROGRESS")) {
                    onProgStateWorkOrder(pWorkOrder);
                    if (pWorkOrder.getWork().getWorkLimitTimeExtension() >= mMaxWorkLimitTimeExt) {
                        startComplaintButton.setVisibility(View.VISIBLE);
                    } else {
                        startComplaintButton.setVisibility(View.INVISIBLE);
                    }
                    fila = workOrderStates_LinearLayout.getChildAt(10);

                } else if (state.startsWith("DONE")) {
                    doneStateWorkOrder(pWorkOrder);
                    startComplaintButton.setVisibility(View.INVISIBLE);
                    fila = workOrderStates_LinearLayout.getChildAt(12);

                } else if (state.startsWith("CLOSED_WARRANTY")) {
                    closedStateWorkOrder(pWorkOrder);
                    startComplaintButton.setVisibility(View.INVISIBLE);
                    fila = workOrderStates_LinearLayout.getChildAt(14);
                }
            }
        }

        // capturamos fila en una variable final para usar dentro del Runnable
        final View filaFinal = fila;
        work0rderStates_scrollView.post(new Runnable() {
            @Override
            public void run() {
                if (filaFinal == null) {
                    closeWorkOrderLifeCycle();
                } else {
                    work0rderStates_scrollView.smoothScrollTo(0, filaFinal.getTop());
                }
            }
        });

        validateInspectionStatus(pWorkOrder);
    }


    //********************************** Validacion de Inspeccion **********************************//
    private void validateInspectionStatus(WorkOrder pWorkOrder) {

        if (pWorkOrder != null && pWorkOrder.getInspection().getInspectionDate() != null) {
            String mNow = Utils.getISOLocalDate();
            String mInspectionDate = pWorkOrder.getInspection().getInspectionDate();
            String[] validStates = mValidStates;

            if (Utils.isAfter(mNow, mInspectionDate) && Arrays.asList(validStates).contains(pWorkOrder.getState()) && pWorkOrder.getInspection().getInspectionFullfilment() == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(mValidateInspectionStatusTittle)
                        .setMessage(mValidateInspectionStatusMessage)
                        .setPositiveButton("SI", (dialog, which) -> {
                            setInspectionStatus(pWorkOrder.getOrderId(), which);
                        }) // Botón "Aceptar"
                        .setNegativeButton("NO", (dialog, which) -> {
                            setInspectionStatus(pWorkOrder.getOrderId(), which);
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }
    private void setInspectionStatus(String pWorkOrderId, int pStatus) {
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderId);
        if (pStatus == BUTTON_POSITIVE) {
            WO.getInspection().setInspectionFullfilment("Y");
            mMainActivityViewModel.updateWorkOrder(WO);
        } else {
            WO.getInspection().setInspectionFullfilment("N");
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(mSetInspectionStatusTittle)
                    .setMessage(mSetInspectionStatusMessage)
                    .setPositiveButton("SI", (dialog, which) -> {
                        setInspectionReschStatus(WO, which);
                    }) // Botón "Aceptar"
                    .setNegativeButton("NO", (dialog, which) -> {
                        setInspectionReschStatus(WO, which);
                    })
                    .setCancelable(false)
                    .show();
        }
    }
    private void setInspectionReschStatus(WorkOrder pWorkOrder, int pStatus) {
        if (pStatus == BUTTON_POSITIVE) {
            pWorkOrder.getInspection().setInspectionRescheduled("Y");
        } else {
            pWorkOrder.getInspection().setInspectionRescheduled("N");
            pWorkOrder.setStateChangeDate(Utils.getISOLocalDate());
            pWorkOrder.setState("CLOSED_FAILED_INSPECTION");
        }
        mMainActivityViewModel.updateWorkOrder(pWorkOrder);
        closeWorkOrderLifeCycle();
    }
    //********************************** Validacion de Inspeccion **********************************//

     //********************************** OPEN STATE **********************************//
    private void openStateWorkOrder() {
        OpenState mOpenStateItem = new OpenState(requireContext());
        String[] mProviderServices = Objects.requireNonNull(mHireFragmentViewModel.getPickedProvider().getValue()).getSpecialization().toArray(new String[0]);
                //Objects.requireNonNull(mHireFragmentViewModel.getPickedProvider().getValue()).getSpecialization().split(",");
        User mPickedProvider = mHireFragmentViewModel.getPickedProvider().getValue();
        User mLoggedUser = mMainActivityViewModel.getLoggedUser().getValue();

        mOpenStateItem.setProviderName("Nombre: " + mPickedProvider.getName());
        mOpenStateItem.setProviderAddress("Direccion: " + mPickedProvider.getAddress());
        mOpenStateItem.setProviderPhone("Telefono: " + mPickedProvider.getPhone().getCcn() + " " + mPickedProvider.getPhone().getNumber());
        mOpenStateItem.setSpinnerValues(mProviderServices);
        mOpenStateItem.setLimitDateListener(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mOpenStateItem.setTimeLimit(s);
            }

            @Override
            public void onError(Exception e) {
            }
        }, mStrDiagTtlOpenState,mMaxDaysLimitOpenState));
        mOpenStateItem.setOnClickListener(v -> {
            if (mOpenStateItem.getTimeLimit().isEmpty() || mOpenStateItem.getDescriptionValue().isEmpty()) {
                emptyFieldsNotificator();
            } else {
                String mTimeLimitDate = Utils.getISOLocalDateFromString(mOpenStateItem.getTimeLimit(), mEndDayTime);
                String mCreationDate = Utils.getISOLocalDate();

                assert mLoggedUser != null;
                assignOrder(mLoggedUser,
                        mPickedProvider,
                        mOpenStateItem.getCategoryValue(),
                        mOpenStateItem.getDescriptionValue(),
                        mCreationDate,
                        mTimeLimitDate);
            }
        });

        openStateDetail_LinearLayout.addView(mOpenStateItem);
        openStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        openStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    private void assignOrder(User pCustomer, User pProvider, String pCategory, String pDescription, String pCreationDate, String pLimitDate) {

        String mStateChangeDate = Utils.getISOLocalDate();

        String mCustomerPhoneNumber = pCustomer.getPhone().getCcn() + pCustomer.getPhone().getNumber();
        String mProviderPhoneNumber = pProvider.getPhone().getCcn() + pProvider.getPhone().getNumber();
        String mState = "ONEVALUATION";

        WorkOrder WO = WorkOrder.builder()
                .customer(WorkOrder.Customer.builder()
                        .customerId(pCustomer.getId())
                        .customerName(pCustomer.getName())
                        .customerAddress(pCustomer.getAddress())
                        .customerLocation(new Location(pCustomer.getLocation().getLatitude(), pCustomer.getLocation().getLongitude(), null))
                        .customerPhone(new Phone(pCustomer.getPhone().getNumber(),pCustomer.getPhone().getCcn()))
                        .build())
                .provider(WorkOrder.Provider.builder()
                        .providerId(pProvider.getId())
                        .providerName(pProvider.getName())
                        .providerAddress(pProvider.getAddress())
                        .providerLocation(new Location(pProvider.getLocation().getLatitude(), pProvider.getLocation().getLongitude(), null))
                        .providerPhone(new Phone(pProvider.getPhone().getNumber(),pProvider.getPhone().getCcn()))
                        .build())
                .specialization(pCategory)
                .description(pDescription)
                .creationDate(pCreationDate)
                .timeLimit(pLimitDate)
                .state(mState)
                .stateChangeDate(mStateChangeDate)
                .build();


        mMainActivityViewModel.createWorkOrder(WO);
        mMainActivityViewModel.setSelectedTab(2);
        Log.d(TAG1, "BOTON CREAR ORDEN PRESIONADO");
    }
    //********************************** ON EVALUATION STATE **********************************//
    private void onEvalStateWorkOrder(WorkOrder pWorkOrder) {
        OnEvalState mOnEvalStateItem = new OnEvalState(requireContext());

        mOnEvalStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomer().getCustomerName());
        mOnEvalStateItem.setCustomerAddress("Direccion: " + pWorkOrder.getCustomer().getCustomerAddress());
        mOnEvalStateItem.setCustomerPhone("Telefono: " + pWorkOrder.getCustomer().getCustomerPhone().getCcn()+pWorkOrder.getCustomer().getCustomerPhone().getNumber());

        mOnEvalStateItem.setLimitDate("Fecha limite: " + Utils.getISOtoDate(pWorkOrder.getTimeLimit()));
        mOnEvalStateItem.setCategory("Categoria: " + pWorkOrder.getSpecialization());
        mOnEvalStateItem.setDescription("Descripcion del trabajo: \n" + pWorkOrder.getDescription());

        boolean isEditable = this.isWorkOrderEditableByUser(mMainActivityViewModel.getLoggedUser().getValue(), pWorkOrder) && this.isWorkOrderEditableByExpiration(pWorkOrder);
        if (isEditable) {
            mOnEvalStateItem.enableEdition();
        } else {
            mOnEvalStateItem.disableEdition();
        }

        final int[] mInspectionFeeValue = {0};
        mOnEvalStateItem.setPlanLimitDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mOnEvalStateItem.setPlanLimitDate(s);
            }
            @Override
            public void onError(Exception e) {
            }
        }, mStrDiagTtlLimitOnEvalState,mMaxDaysMeetOnEvalState-1));
        mOnEvalStateItem.setMeetDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mOnEvalStateItem.setMeetDate(s);
            }
            @Override
            public void onError(Exception e) {
            }
        }, mStrDiagTtlOnEvalState,mMaxDaysMeetOnEvalState));
        mOnEvalStateItem.setMeetTimeOCL(v -> showTimePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mOnEvalStateItem.setMeetTime(s);
            }
            @Override
            public void onError(Exception e) {
            }
        }));
        mOnEvalStateItem.setMeetDateTCL(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int mSValue;
                if (s.toString().isEmpty()) {
                    mSValue = 0;
                } else {
                    mSValue = Integer.parseInt(s.toString());
                }
                Log.d(TAG1, "Comision Fee: " + (mInspetionFeePercent / 100.0));
                Log.d(TAG1, "Comision : " + mSValue * (mInspetionFeePercent / 100.0));
                mInspectionFeeValue[0] = (int) (mSValue * (mInspetionFeePercent / 100.0));
                mOnEvalStateItem.setMeetFee("Comision Plataforma: " + mInspectionFeeValue[0] + "sat");
            }
        });
        mOnEvalStateItem.setAcceptButtonOCL(v -> {
            Log.d(TAG1, "Click botón aceptar");

            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)) {
                Log.d(TAG1, "Orden expirada → notificando");
                this.orderExpiredNotificator();
            } else {
                if (mOnEvalStateItem.getMeetDate().isEmpty()
                        || mOnEvalStateItem.getMeetTime().isEmpty()
                        || mOnEvalStateItem.getPlanLimitDate().isEmpty()) {
                    Log.d(TAG1, "Campos vacíos → notificando");
                    emptyFieldsNotificator();
                } else {
                    int mInspectionCharges = 0;
                    if (!mOnEvalStateItem.getMeetTariff().isEmpty()) {
                        try {
                            mInspectionCharges = Integer.parseInt(mOnEvalStateItem.getMeetTariff());
                        } catch (NumberFormatException e) {
                            Log.e(TAG1, "Error parseando tarifa: " + e.getMessage());
                            mInspectionCharges = 0;
                        }
                    }

                    String mPlanLimitDate = Utils.getISOLocalDateFromString(
                            mOnEvalStateItem.getPlanLimitDate(), mEndDayTime);
                    String mInspectionDate = Utils.getISOLocalDateFromString(
                            mOnEvalStateItem.getMeetDate(), mOnEvalStateItem.getMeetTime());
                    int mInspectionFee = (int) (mInspectionCharges * (mInspetionFeePercent / 100.0));

                    String mNow = Utils.getISOLocalDate();

                    Log.d(TAG1, "mNow: " + mNow);
                    Log.d(TAG1, "mInspectionDate: " + mInspectionDate);
                    Log.d(TAG1, "mInspectionCharges: " + mInspectionCharges);
                    Log.d(TAG1, "mInspectionFee: " + mInspectionFee);

                    if (mInspectionCharges > mInspectionMaxCost) {
                        Log.d(TAG1, "Costo supera máximo permitido");
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle("Información Importante")
                                .setMessage("El costo de la inspección no puede superar " + mInspectionMaxCost)
                                .setPositiveButton("Aceptar", null)
                                .show();
                    } else if (Utils.isAfter(mPlanLimitDate, mInspectionDate)) {
                        Log.d(TAG1, "Error correlación de fechas → notificando");
                        dateCorrelationErrorNotificator(mOnEvalStateDatesErrorTitle, mOnEvalStateDatesErrorMessage);
                    } else {
                        Log.d(TAG1, "BOTÓN ACEPTAR ORDEN PRESIONADO → ejecutando planDate");
                        planDate(pWorkOrder.getOrderId(), mPlanLimitDate, mInspectionDate, mInspectionCharges, mInspectionFee);
                        this.updateWorkOrderStateTemp(pWorkOrder);
                    }
                }
            }
        });
        mOnEvalStateItem.setRejectButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");
                this.rejectOrder(pWorkOrder.getOrderId());
                this.updateWorkOrderStateTemp(pWorkOrder);
            }
        });

        onEvalStateDetail_LinearLayout.addView(mOnEvalStateItem);
        onEvalStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        onEvalStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    private void planDate(String pWorkOrderID,String pPlanLimitDate, String pInspectionDate, Integer pInspectionCharges, Integer pInspectionFee) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.getInspection().setInspectionDate(pInspectionDate);
        WO.getInspection().setInspectionCharges(pInspectionCharges);
        WO.getInspection().setInspectionTimeLimit(pPlanLimitDate);
        WO.getInspection().setInspectionFee(pInspectionFee);
        WO.setState("PLANNED");
        WO.setStateChangeDate(mStateChangeDate);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    private void rejectOrder(String pWorkOrderID) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
//        WO.setInspectionDate(pInspectionDate);
//        WO.setInspectionCharges(pInspectionCharges);
//        WO.setInspectionFee(pInspectionFee);
        WO.setState("CANCELED_REJECTION_AFTER_EVALUATION");
        WO.setStateChangeDate(mStateChangeDate);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** PLANNED STATE **********************************//
    //at this point customer should accept the visit and pay the tariff if it was required
    private void plannedStateWorkOrder(WorkOrder pWorkOrder) {
        PlannedState mPlannedStateItem = new PlannedState(requireContext());
        String mInspectionDate = Utils.getISOtoDate(pWorkOrder.getInspection().getInspectionDate());
        String mPlanLimitDate = Utils.getISOtoDate(pWorkOrder.getInspection().getInspectionTimeLimit());

        boolean isEditable = this.isWorkOrderEditableByUser(mMainActivityViewModel.getLoggedUser().getValue(), pWorkOrder) && this.isWorkOrderEditableByExpiration(pWorkOrder);
        if (isEditable) {
            mPlannedStateItem.enableEdition();
        } else {
            mPlannedStateItem.disableEdition();
        }

        mPlannedStateItem.setProviderName("Nombre: " + pWorkOrder.getProvider().getProviderName());
        mPlannedStateItem.setProviderAddress("Direccion: " + pWorkOrder.getProvider().getProviderAddress());
        mPlannedStateItem.setProviderPhone("Telefono: " + pWorkOrder.getProvider().getProviderPhone().getCcn()+pWorkOrder.getProvider().getProviderPhone().getNumber());

        mPlannedStateItem.setPaymentOrder("Orden de Pago: " + pWorkOrder.getInspection().getInspectionPaymentOrder());

        mPlannedStateItem.setPlanLimitDate("Fecha Limite de Aceptacion:" + mPlanLimitDate);
        mPlannedStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 11));
        mPlannedStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(13, 20));
        mPlannedStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspection().getInspectionCharges() + "sat");
        mPlannedStateItem.setMeetFee("Comision Plataforma: " + pWorkOrder.getInspection().getInspectionFee() + "sat");

        mPlannedStateItem.setGenPaymentOrderButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "BOTON GENERAR ORDEN DE PAGO PRESIONADO");

                //mMainActivityViewModel.loadPaymentOrder(pWorkOrder.getWork().getWorkPaymentOrder());

                // Crear un item
                PaymentRequest.ItemRequest item = PaymentRequest.ItemRequest.builder()
                        .id(pWorkOrder.getOrderId())
                        .title("Service suplied from "+ pWorkOrder.getProvider().getProviderName() + " to " + pWorkOrder.getCustomer().getCustomerName())
                        .description(pWorkOrder.getDescription())
                        .pictureUrl("")
                        .categoryId(pWorkOrder.getSpecialization())
                        .quantity(1)
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal(pWorkOrder.getInspection().getInspectionCharges()))
                        .build();

        // Crear los back urls
                PaymentRequest.BackUrlsRequest backUrls = PaymentRequest.BackUrlsRequest.builder()
                        .success("https://example.com/payment/success")
                        .pending("https://example.com/payment/pending")
                        .failure("https://example.com/payment/failure")
                        .build();

        // Crear el PaymentRequest con builder
                PaymentRequest mPaymentRequest = PaymentRequest.builder()
                        .items(List.of(item))
                        .backUrls(backUrls)
                        .notificationUrl("https://unabandoned-dashiest-rhoda.ngrok-free.dev/webhook/payment/success")
                        .externalReference(pWorkOrder.getInspection().getInspectionPaymentOrder())
                        .metadata(Map.of(
                                "payment_order_id", pWorkOrder.getInspection().getInspectionPaymentOrder(),
                                "work_order_id", pWorkOrder.getOrderId()
                        ))
                        .build();

        // Usar el request en tu ViewModel
                mMainActivityViewModel.createPayment(mPaymentRequest);
                this.updateWorkOrderStateTemp(pWorkOrder);
            }
        });
        mPlannedStateItem.setAcceptButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                confirmDate(pWorkOrder.getOrderId(), pWorkOrder.getInspection().getInspectionPaymentOrder());
                Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");
            }

        });

        mPlannedStateItem.setRejectButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                this.rejectDate(pWorkOrder.getOrderId());
                this.updateWorkOrderStateTemp(pWorkOrder);
                Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");
            }

        });
        mPlannedStateItem.setInputLayoutEndIconOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "BOTON COPIAR INVOICE PRESIONADO");
            }

        });

        plannedStateDetail_LinearLayout.addView(mPlannedStateItem);
        plannedStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        plannedStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    private void redirectToPayment(String pPaymentUrl) {

        if (pPaymentUrl!=null){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pPaymentUrl));
        startActivity(browserIntent);
        mMainActivityViewModel.setPayment(null);
        }
    }
    private void confirmDate(String pWorkOrderID, String pPaymentOrderID) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.getInspection().setInspectionPaymentOrder("pPaymentOrderID-123");
        WO.setState("CONFIRMED");
        WO.setStateChangeDate(mStateChangeDate);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    private void rejectDate(String pWorkOrderID) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
//        WO.setInspectionPaymentOrder("pPaymentOrderID-123");
        WO.setState("ONEVALUATION");
        WO.setStateChangeDate(mStateChangeDate);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** CONFIRMED STATE **********************************//
    private void confStateWorkOrder(WorkOrder pWorkOrder) {
        ConfState mConfStateItem = new ConfState(requireContext());
        String mInspectionDate = Utils.getISOtoDate(pWorkOrder.getInspection().getInspectionDate());

        mConfStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomer().getCustomerName());
        mConfStateItem.setCustomerAddress("Direccion: " + pWorkOrder.getCustomer().getCustomerAddress());
        mConfStateItem.setCustomerPhone("Telefono: " + pWorkOrder.getCustomer().getCustomerPhone().getCcn()+pWorkOrder.getCustomer().getCustomerPhone().getNumber());

        mConfStateItem.setCategory("Categoria: " + pWorkOrder.getSpecialization());
        mConfStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        mConfStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 11));
        mConfStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(13, 20));

        mConfStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspection().getInspectionCharges() + "sat");
        mConfStateItem.setMeetFee("Comision Plataforma: " + pWorkOrder.getInspection().getInspectionFee() + "sat");

        mConfStateItem.setPresentationLimitDate("Fecha limite para presentar la propuesta\n"+ Utils.getISOtoDate(Utils.getISOLocalDatePlus(mMaxDaysOnConfState,pWorkOrder.getInspection().getInspectionDate())) );

        boolean isEditable = this.isWorkOrderEditableByUser(mMainActivityViewModel.getLoggedUser().getValue(), pWorkOrder) && this.isWorkOrderEditableByExpiration(pWorkOrder);
        if (isEditable) {
            mConfStateItem.enableEdition();
        } else {
            mConfStateItem.disableEdition();
        }

        final Integer[] mWorkFeeValue = {0};
        final Integer[] mWorkLaborCostValue = {0};
        final Integer[] mWorkMaterialsCostValue = {0};

        mConfStateItem.setWorkLaborCostTCL(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    mWorkLaborCostValue[0] = 0;
                } else {
                    mWorkLaborCostValue[0] = Integer.parseInt(s.toString());
                }
                Log.d(TAG1, "Comision Fee: " + (mFeePercent / 100.0));
                Log.d(TAG1, "Comision Mano de obra : " + mWorkLaborCostValue[0] * (mFeePercent / 100.0));

                mWorkFeeValue[0] = (int) ((mWorkLaborCostValue[0] + mWorkMaterialsCostValue[0]) * (mFeePercent / 100.0));
                mConfStateItem.setJobFee("Comision Plataforma: " + mWorkFeeValue[0] + "sat");
            }
        });
        mConfStateItem.setWorkMaterialsCostTCL(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    mWorkMaterialsCostValue[0] = 0;
                } else {
                    mWorkMaterialsCostValue[0] = Integer.parseInt(s.toString());
                }
                Log.d(TAG1, "Comision Fee: " + (mFeePercent / 100.0));
                Log.d(TAG1, "Comision Materiales : " + mWorkMaterialsCostValue[0] * (mFeePercent / 100.0));

                mWorkFeeValue[0] = (int) ((mWorkLaborCostValue[0] + mWorkMaterialsCostValue[0]) * (mFeePercent / 100.0));

                mConfStateItem.setJobFee("Comision Plataforma: " + mWorkFeeValue[0] + "sat");
            }
        });
        mConfStateItem.setTimeLimitDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setTimeLimitDate(s);
            }

            @Override
            public void onError(Exception e) {
            }
        }, mStrDiagTtlConfState,0,null));
        mConfStateItem.setTimeLimitDateTimeOCL(v -> showTimePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setTimeLimitDateTime(s);
            }

            @Override
            public void onError(Exception e) {
            }
        }));
        mConfStateItem.setWorkStartDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkStartDate(s);
            }

            @Override
            public void onError(Exception e) {
            }
        }, mStrDiagTtlOnConfStateStartDate,0,null));
        mConfStateItem.setWorkStartDateTimeOCL(v -> showTimePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkStartDateTime(s);
            }

            @Override
            public void onError(Exception e) {
            }
        }));
        mConfStateItem.setWorkEndDateOCL(v -> showDatePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkEndDate(s);
            }

            @Override
            public void onError(Exception e) {
            }
        }, mStrDiagTtlOnConfStateEndDate,0,null));
        mConfStateItem.setWorkEndDateTimeOCL(v -> showTimePickerDialog(new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mConfStateItem.setWorkEndDateTime(s);
            }

            @Override
            public void onError(Exception e) {
            }
        }));

        mConfStateItem.setPresentOrderButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                if (mConfStateItem.getWorkStartDate().isEmpty() || mConfStateItem.getWorkEndDate().isEmpty() || mConfStateItem.getWorkJobCost().isEmpty() || mConfStateItem.getWorkTaskDetail().isEmpty()||mConfStateItem.getTimeLimitDate().isEmpty()) {
                    emptyFieldsNotificator();
                } else {
                    int mWorkMaterialsCost = 0;
                    if (!mConfStateItem.getWorkMaterialCost().isEmpty()) {
                        mWorkMaterialsCost = Integer.parseInt(mConfStateItem.getWorkMaterialCost());
                    }

                    String mWorkStartDateTime;
                    String mWorkEndDateTime;
                    String mProposalTimeLimitTime;

                    if (mConfStateItem.getWorkStartDateTime().isEmpty()) {
                        mWorkStartDateTime = "23:59";
                    } else {
                        mWorkStartDateTime = mConfStateItem.getWorkStartDateTime();
                    }
                    if (mConfStateItem.getWorkEndDateTime().isEmpty()) {
                        mWorkEndDateTime = "23:59";
                    } else {
                        mWorkEndDateTime = mConfStateItem.getWorkEndDateTime();
                    }
                    if (mConfStateItem.getTimeLimitDateTime().isEmpty()) {
                        mProposalTimeLimitTime = "23:59";
                    } else {
                        mProposalTimeLimitTime = mConfStateItem.getTimeLimitDateTime();
                    }

                    String mWorkStartDate = Utils.getISOLocalDateFromString(mConfStateItem.getWorkStartDate(), mWorkStartDateTime);
                    String mWorkEndDate = Utils.getISOLocalDateFromString(mConfStateItem.getWorkEndDate(), mWorkEndDateTime);
                    String mProposalTimeLimitDate = Utils.getISOLocalDateFromString(mConfStateItem.getTimeLimitDate(), mProposalTimeLimitTime);

                    int mWorkLaborCost = Integer.parseInt(mConfStateItem.getWorkJobCost());
                    int mWorkFee = (int) ((mWorkLaborCost + mWorkMaterialsCost) * (mFeePercent / 100.0));
                    String mWorkTaskDetail = mConfStateItem.getWorkTaskDetail();

                    String mToday = Utils.getISOLocalDate();

                    Log.d(TAG1, "mToday :" + mToday);
                    Log.d(TAG1, "mWorkStartDate :" + mWorkStartDate);
                    Log.d(TAG1, "mWorkEndDate :" + mWorkEndDate);
                    Log.d(TAG1, "Current Date :" + Utils.getISOLocalDate());
                    Log.d(TAG1, "mWorkFee :" + mWorkFee);

                    if (Utils.isAfter(mWorkStartDate, mToday) && Utils.isAfter(mWorkEndDate, mToday) && Utils.isAfter(mWorkEndDate, mWorkStartDate) && Utils.isAfter(mWorkStartDate,mProposalTimeLimitDate )) {
                        Log.d(TAG1, "BOTON PRESENTAR ORDEN PRESIONADO");
                        diagnoseOrder(pWorkOrder.getOrderId(),mProposalTimeLimitDate, mWorkStartDate, mWorkEndDate, mWorkLaborCost, mWorkMaterialsCost, mWorkFee, mWorkTaskDetail);
                    } else {
                        dateCorrelationErrorNotificator(mConfStateDatesErrorTitle,mConfStateDatesErrorMessage);
                    }

                }
            }

        });
        mConfStateItem.setRejectButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(mRejectWorkAfterInspTittle)
                        .setMessage(mRejectWorkAfterInspMessage)
                        .setPositiveButton("SI", (dialog, which) -> {
                            this.cancelOrder(pWorkOrder.getOrderId());
                            Log.d(TAG1, "CONFIRMED STATE:REJECTION ACCEPTED");
                        })
                        .setNegativeButton("NO", (dialog, which) -> {
                            Log.d(TAG1, "CONFIRMED STATE:REJECTION DENIED");
                        })
                        .setCancelable(false)
                        .show();
            }

        });

        confStateDetail_LinearLayout.addView(mConfStateItem);
        confStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        confStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    private void diagnoseOrder(String pWorkOrderID,String pProposalTimeLimit, String pWorkStartDate, String pWorkEndDate, Integer pWorkLaborCost, Integer pWorkMaterialsCost, Integer pWorkFee, String pWorkDetail) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("DIAGNOSED");
        WO.setStateChangeDate(mStateChangeDate);
        WO.getWork().setProposalTimeLimitDate(pProposalTimeLimit);
        WO.getWork().setWorkStartDate(pWorkStartDate);
        WO.getWork().setWorkEndDate(pWorkEndDate);
        WO.getWork().setWorkLaborCost(pWorkLaborCost);
        WO.getWork().setWorkMaterialsCost(pWorkMaterialsCost);
        WO.getWork().setWorkFee(pWorkFee);
        WO.getWork().setDetail(pWorkDetail);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //at this point provider make the visit to diagnose, customer paid de visit and has accepted the date
    private void cancelOrder(String pWorkOrderID) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("CANCELED_REJECTION_AFTER_INSPECTION");
        WO.setStateChangeDate(mStateChangeDate);
//        WO.setWorkStartDate(pWorkStartDate);
//        WO.setWorkEndDate(pWorkEndDate);
//        WO.setWorkLaborCost(pWorkLaborCost);
//        WO.setWorkMaterialsCost(pWorkMaterialsCost);
//        WO.setWorkFee(pWorkFee);
//        WO.setDetail(pWorkDetail);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** DIAGNOSE STATE **********************************//
    //at this point customer pay the tariff and accept the diagnose and work schedule
    private void diagStateWorkOrder(WorkOrder pWorkOrder) {
        DiagState mDiagStateItem = new DiagState(requireContext());
        String mWorkStartDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkStartDate());
        String mWorkEndDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkEndDate());

        boolean isEditable = this.isWorkOrderEditableByUser(mMainActivityViewModel.getLoggedUser().getValue(), pWorkOrder) && this.isWorkOrderEditableByExpiration(pWorkOrder);
        if (isEditable) {
            mDiagStateItem.enableEdition();
        } else {
            mDiagStateItem.disableEdition();
        }

        mDiagStateItem.setProviderName("Nombre: " + pWorkOrder.getProvider().getProviderName());
        mDiagStateItem.setProviderAddress("Direccion: " + pWorkOrder.getProvider().getProviderAddress());
        mDiagStateItem.setProviderPhone("Telefono: " + pWorkOrder.getProvider().getProviderPhone().getCcn()+pWorkOrder.getProvider().getProviderPhone().getNumber());

        mDiagStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate);
        mDiagStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate);
        mDiagStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getWork().getDetail());
        mDiagStateItem.setJobCost("Costo Mano de Obra: " + pWorkOrder.getWork().getWorkLaborCost() + "sat");
        mDiagStateItem.setMaterialCost("Costo Materiales: " + pWorkOrder.getWork().getWorkMaterialsCost() + "sat");
        mDiagStateItem.setJobFee("Comision de Plataforma: " + pWorkOrder.getWork().getWorkFee() + "sat");
        mDiagStateItem.setAcceptanceLimitDate("Tienes hasta la fecha indicada para evaluar y aceptar la propuesta\n" + Utils.getISOtoDate(pWorkOrder.getWork().getProposalTimeLimitDate()) );

        mDiagStateItem.setAcceptButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                this.acceptContract(pWorkOrder.getOrderId(), pWorkOrder.getWork().getWorkPaymentOrder());
            }
            Log.d(TAG1, "BOTON GENERAR ORDEN DE PAGO PRESIONADO");

        });
        mDiagStateItem.setGenPaymentOrderButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "LA ORDEN NO A EXPIRADO, AUN ES POSIBLE EDITAR");
            }
            Log.d(TAG1, "BOTON ACEPTAR ORDEN PRESIONADO");
        });
        mDiagStateItem.setRejectButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                this.rejectContract(pWorkOrder.getOrderId());
            }
            Log.d(TAG1, "BOTON RECHAZAR ORDEN PRESIONADO");
        });
        mDiagStateItem.setInputLayoutEndIconOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "LA ORDEN NO A EXPIRADO, AUN ES POSIBLE EDITAR");
            }
            Log.d(TAG1, "BOTON COPIAR INVOICE PRESIONADO");
        });

        diagStateDetail_LinearLayout.addView(mDiagStateItem);
        diagStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        diagStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    private void acceptContract(String pWorkOrderID, String pWorkPaymentOrderID) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("ONPROGRESS");
        WO.setStateChangeDate(mStateChangeDate);
        WO.getWork().setWorkLimitTimeExtension(0);
//        WO.setWorkStartDate(pWorkStartDate);
//        WO.setWorkEndDate(pWorkEndDate);
//        WO.setWorkCost(pWorkCost);
//        WO.setDetail(pWorkDetail);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    private void rejectContract(String pWorkOrderID) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("CONFIRMED");
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
        String mWorkStartDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkStartDate());
        String mWorkEndDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkEndDate());

        boolean isEditable = this.isWorkOrderEditableByUser(mMainActivityViewModel.getLoggedUser().getValue(), pWorkOrder) && this.isWorkOrderEditableByExpiration(pWorkOrder);
        if (isEditable) {
            mOnProgStateItem.enableEdition();
        } else {
            mOnProgStateItem.disableEdition();
        }

        mOnProgStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomer().getCustomerName());
        mOnProgStateItem.setCustomerAddress("Direccion: " + pWorkOrder.getCustomer().getCustomerAddress());
        mOnProgStateItem.setCustomerPhone("Telefono: " + pWorkOrder.getCustomer().getCustomerPhone().getCcn()+pWorkOrder.getCustomer().getCustomerPhone().getNumber());

        mOnProgStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate);
        mOnProgStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate);
        mOnProgStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getWork().getDetail());

        mOnProgStateItem.setJobCost("Costo Mano de Obra: " + pWorkOrder.getWork().getWorkLaborCost() + "sat");
        mOnProgStateItem.setMaterialCost("Costo Materiales: " + pWorkOrder.getWork().getWorkMaterialsCost() + "sat");
        mOnProgStateItem.setJobFee("Comision de Plataforma: " + pWorkOrder.getWork().getWorkFee() + "sat");


        mOnProgStateItem.setFinishWorkOrderButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "LA ORDEN NO A EXPIRADO, AUN ES POSIBLE EDITAR");
                String mClosingDate = Utils.getISOLocalDate();
                String mBiggerDate = Utils.getBiggerISODate(pWorkOrder.getWork().getWorkEndDate(), mClosingDate);
                String mWarrantyDate = Utils.getISOLocalDatePlus(mWarrantyDays, mBiggerDate);
                finishOrder(pWorkOrder.getOrderId(),mWarrantyDate);
            }

            Log.d(TAG1, "BOTON FINALIZAR ORDEN DE PAGO PRESIONADO");
        });

        onProgStateDetail_LinearLayout.addView(mOnProgStateItem);
        onProgStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        onProgStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    private void finishOrder(String pWorkOrderID, String pWarrantyEndDate) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("DONE");
        WO.setStateChangeDate(mStateChangeDate);
        WO.getWork().setWorkWarrantyEndDate(pWarrantyEndDate);

        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** DONE STATE **********************************//
    private void doneStateWorkOrder(WorkOrder pWorkOrder) {
        DoneState mDoneStateItem = new DoneState(requireContext());

        String mWorkWarrantyEndDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkWarrantyEndDate());
        String mAutoClosingDate = Utils.getISOLocalDatePlus(mAutoClosingDays, pWorkOrder.getStateChangeDate());

        Integer mWorkLimitTimeExtensionQuantity = pWorkOrder.getWork().getWorkLimitTimeExtension() + 1;
        String mNowDate = Utils.getISOLocalDate();
        String mCheckDate = Utils.getBiggerISODate(mNowDate, mAutoClosingDate);
        String mAuxReviewClosingWarning = String.format(mReviewClosingWarning, Utils.getISOtoDate(mAutoClosingDate));
        String mAuxReviewWarrantyWarning = String.format(mReviewWarrantyWarning, mWorkWarrantyEndDate);
        //Se verifica si la fecha de autocierre aun esta vigente, de lo contrario se bloquea la orden
        //Al final del dia, un proceso batch colocara la orden en estado cerrado finalizando el proceso automaticamente
        if (Objects.equals(mCheckDate, mNowDate)) {
            //TODO:Probar que verga modifica esto
            mAuxReviewClosingWarning = String.format(mReviewClosingWarningOutOfDate, Utils.getISOtoDate(mAutoClosingDate));
        }

        boolean isEditable = this.isWorkOrderEditableByUser(mMainActivityViewModel.getLoggedUser().getValue(), pWorkOrder) && this.isWorkOrderEditableByExpiration(pWorkOrder);
        if (isEditable) {
            mDoneStateItem.enableEdition();
        } else {
            mDoneStateItem.disableEdition();
        }

        mDoneStateItem.setReviewClosingWarning(mAuxReviewClosingWarning);
        mDoneStateItem.setReviewWarrantyWarning(mAuxReviewWarrantyWarning);

        mDoneStateItem.setAcceptButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "LA ORDEN NO A EXPIRADO, AUN ES POSIBLE EDITAR");
                if (mDoneStateItem.getProviderReview().isEmpty()) {
                    emptyFieldsNotificator();
                } else {
                    closeOrder(pWorkOrder.getOrderId(),
                            mDoneStateItem.getProviderAppereanceScore(),
                            mDoneStateItem.getProviderCleanlinessScore(),
                            mDoneStateItem.getProviderSpeedScore(),
                            mDoneStateItem.getProviderQualityScoreScore(),
                            mDoneStateItem.getProviderReview());
                }
            }
            Log.d(TAG1, "BOTON CERRAR ORDEN POR TRABAJO COMPLETADO PRESIONADO");
        });
        mDoneStateItem.setRejectButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "LA ORDEN NO A EXPIRADO, AUN ES POSIBLE EDITAR");
                showDatePickerDialog(new Callback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        String mExtendedWorkEndDate = Utils.getISOLocalDateFromString(s, mStartDayTime);
                        if (Utils.isAfter(pWorkOrder.getWork().getWorkEndDate(), mExtendedWorkEndDate)) {
                            mExtendedWorkEndDate = pWorkOrder.getWork().getWorkEndDate();
                        }
                        workRejected(pWorkOrder.getOrderId(), mWorkLimitTimeExtensionQuantity, mExtendedWorkEndDate);
                        Log.d(TAG1, "Fecha Seleccionada:" + s);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                }, mStrDiagTtlDone,mMinDaysWorkEndDateExt,mMaxDaysWorkEndDateExt);
            }
            Log.d(TAG1, "BOTON RECHAZAR TRABAJO PRESIONADO");
        });

        doneStateDetail_LinearLayout.addView(mDoneStateItem);
        doneStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        doneStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    private void closeOrder(String pWorkOrderID, Integer pAppereanceScore, Integer pCleanlinessScore, Integer pSpeedScore, Integer pQualityScore, String pImpressions) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("CLOSED_WARRANTY");
        WO.setStateChangeDate(mStateChangeDate);
        WO.getFeedback().setAppereanceScore(pAppereanceScore);
        WO.getFeedback().setCleanlinessScore(pCleanlinessScore);
        WO.getFeedback().setSpeedScore(pSpeedScore);
        WO.getFeedback().setQualityScore(pQualityScore);
        WO.getFeedback().setImpressions(pImpressions);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    private void workRejected(String pWorkOrderID, Integer pWorkLimitTimeExtensionQuantity, String pExtendedWorkEndDate) {
        String mStateChangeDate = Utils.getISOLocalDate();
        WorkOrder WO = new WorkOrder();
        WO.setOrderId(pWorkOrderID);
        WO.setState("ONPROGRESS");
        WO.setStateChangeDate(mStateChangeDate);
        WO.getWork().setWorkEndDate(pExtendedWorkEndDate);
        WO.getWork().setWorkLimitTimeExtension(pWorkLimitTimeExtensionQuantity);
        mMainActivityViewModel.updateWorkOrder(WO);
    }
    //********************************** CLOSED STATE **********************************//
    private void closedStateWorkOrder(WorkOrder pWorkOrder) {
        ClosedState mClosedStateItem = new ClosedState(requireContext());

        boolean isEditable = this.isWorkOrderEditableByUser(mMainActivityViewModel.getLoggedUser().getValue(), pWorkOrder) && this.isWorkOrderEditableByExpiration(pWorkOrder);
        if (isEditable) {
            mClosedStateItem.enableEdition();
        } else {
            mClosedStateItem.disableEdition();
        }

        String mWorkStartDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkStartDate());
        String mWorkEndDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkEndDate());
        String mInspectionDate = Utils.getISOtoDate(pWorkOrder.getInspection().getInspectionDate());
        String mWarrantyDate = Utils.getISOtoDate(pWorkOrder.getWork().getWorkWarrantyEndDate());

        mClosedStateItem.setCustomerName("Nombre: " + pWorkOrder.getCustomer().getCustomerName());
        mClosedStateItem.setCustomerAddress("Direccion: " + pWorkOrder.getCustomer().getCustomerAddress());
        mClosedStateItem.setCustomerPhone("Telefono: " + pWorkOrder.getCustomer().getCustomerPhone().getCcn()+pWorkOrder.getCustomer().getCustomerPhone().getNumber());

        mClosedStateItem.setProviderName("Nombre: " + pWorkOrder.getProvider().getProviderName());
        mClosedStateItem.setProviderAddress("Direccion: " + pWorkOrder.getProvider().getProviderAddress());
        mClosedStateItem.setProviderPhone("Telefono: " + pWorkOrder.getProvider().getProviderPhone().getCcn()+pWorkOrder.getProvider().getProviderPhone().getNumber());

        mClosedStateItem.setCategory("Categoria: " + pWorkOrder.getSpecialization());
        mClosedStateItem.setDescription("Descripcion del trabajo: " + pWorkOrder.getDescription());

        mClosedStateItem.setMeetDate("Fecha de Cita: " + mInspectionDate.substring(0, 11));
        mClosedStateItem.setMeetTime("Hora de Cita: " + mInspectionDate.substring(13, 20));

        mClosedStateItem.setMeetTariff("Tarifa de Visita: " + pWorkOrder.getInspection().getInspectionCharges() + "sat");
        mClosedStateItem.setMeetFee("Comision Plataforma: " + pWorkOrder.getInspection().getInspectionFee() + "sat");

        mClosedStateItem.setWorkStartDate("Fecha Inicio: " + mWorkStartDate);
        mClosedStateItem.setWorkEndDate("Fecha Fin: " + mWorkEndDate);
        mClosedStateItem.setWorkDetail("Detalle de tareas: \n" + pWorkOrder.getWork().getDetail());

        mClosedStateItem.setJobCost("Costo Mano de Obra: " + pWorkOrder.getWork().getWorkLaborCost() + "sat");
        mClosedStateItem.setMaterialCost("Costo Materiales: " + pWorkOrder.getWork().getWorkMaterialsCost() + "sat");
        mClosedStateItem.setJobFee("Comision de Plataforma: " + pWorkOrder.getWork().getWorkFee() + "sat");


        mClosedStateItem.setProviderAppereanceScore(pWorkOrder.getFeedback().getAppereanceScore());
        mClosedStateItem.setProviderCleanlinessScore(pWorkOrder.getFeedback().getCleanlinessScore());
        mClosedStateItem.setProviderSpeedScore(pWorkOrder.getFeedback().getSpeedScore());
        mClosedStateItem.setProviderQualityScore(pWorkOrder.getFeedback().getQualityScore());

        mClosedStateItem.setProviderReview("Reseña del cliente:\n" + pWorkOrder.getFeedback().getImpressions());
        mClosedStateItem.setWarrantyMessage(mWarrantyMessage);
        mClosedStateItem.setWarrantyEndDate("Fecha limite de Garantia: " + mWarrantyDate);

        mClosedStateItem.setComplainButtonOCL(v -> {
            if (!this.isWorkOrderEditableByExpiration(pWorkOrder)){
                this.orderExpiredNotificator();
            } else {
                Log.d(TAG1, "LA ORDEN NO A EXPIRADO, AUN ES POSIBLE EDITAR");
            }
            Log.d(TAG1, "BOTON ABRIR UNA QUEJA CON SOPORTE PRESIONADO");
        });

        closedStateDetail_LinearLayout.addView(mClosedStateItem);
        closedStateDetail_vertLine.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.dotted_line));
        closedStateDetail_vertLine.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
    }
    //********************************** CLOSED STATE **********************************//
    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveChangesButton:
                closeWorkOrderLifeCycle();
                break;
        }
    }
    private void showDatePickerDialog(Callback<String> pCallback,String pTitle,Integer pMinDaysAvailable ,Integer pMaxDaysAvailable ) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            // Formatear la fecha seleccionada y mostrarla en el EditText
            String fechaSeleccionada = String.format(Locale.ROOT, "%02d/%02d/%04d", dayOfMonth, month1 + 1, year1);
            pCallback.onSuccess(fechaSeleccionada);},
                year, month, day);
        datePickerDialog.setTitle(pTitle);
        if (pMinDaysAvailable!=null){
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (86400L *1000*pMinDaysAvailable-1000));

        }
        if (pMaxDaysAvailable!=null){
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (86400L *1000*pMaxDaysAvailable-1000));
        }

        datePickerDialog.show();
    }
    private void showDatePickerDialog(Callback<String> pCallback, String pTitle, Integer pMaxDaysAvailable) {
        showDatePickerDialog(pCallback,pTitle,0,pMaxDaysAvailable);
    }
    private void showTimePickerDialog(Callback<String> pCallback) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute1) -> {
                    // Formatear la hora seleccionada y mostrarla en el EditText
                    String horaSeleccionada = String.format(Locale.ROOT, "%02d:%02d", hourOfDay, minute1);
                    pCallback.onSuccess(horaSeleccionada);
                },
                hour, minute, true // true para formato de 24 horas
        );
        timePickerDialog.show();
    }
    private void closeWorkOrderLifeCycle(){
        if (mMainActivityViewModel.getSelectedTab().getValue()!=null && mMainActivityViewModel.getSelectedTab().getValue() == 0 ) {
            mMainActivityViewModel.setSelectedFragment(0, View.VISIBLE);
        } else {
            mMainActivityViewModel.setSelectedFragment(2, View.VISIBLE);
        }
    }
    private void dateCorrelationErrorNotificator(String pTitleText, String pMessageText){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(pTitleText)
                .setMessage(pMessageText)
                .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                .show();
    }
    private void noDataErrorNotificator(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(mNoDataErrorTittle)
                .setMessage(mNoDataErrorMessage)
                .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                .show();
        closeWorkOrderLifeCycle();
    }
    private void emptyFieldsNotificator(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(mEmptyFieldErrorTittle)
                .setMessage(mEmptyFieldErrorMessage)
                .setPositiveButton("Aceptar", null) // Botón "Aceptar"
                .show();
    }
    private void orderExpiredNotificator() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(mOrderExpiredErrorTittle)
                .setMessage(mOrderExpiredErrorMessage)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    closeWorkOrderLifeCycle(); // Botón "Aceptar"
                })
                .show();
        //TODO: testear que dejando una orden abierta hasta que expire e intentando realizar acciones cierre notifique y cierre la ventana. 
    }
    private void clearWorkOrder() {
        ViewGroup celdaEspecifica;
        for (int i = 1; i < 16; i += 2) {
            celdaEspecifica = (ViewGroup) workOrderStates_LinearLayout.getChildAt(i);
            if (celdaEspecifica.getChildCount() > 1) {
                celdaEspecifica.removeViewAt(1);
                celdaEspecifica.getChildAt(0).setBackground(mVertLineBackGround);
                celdaEspecifica.getChildAt(0).setBackgroundTintMode(mVertLineBackTintMode);
            }
        }

    }
    private boolean isWorkOrderEditableByUser(User pUser, WorkOrder pWorkOrder) {
        if (pUser == null) {
            noDataErrorNotificator();
            return false;
        }

        // 🚨 Nueva validación: si está en ONGOING_ACTION, no se puede editar
        if (pWorkOrder.getState() != null && pWorkOrder.getState().endsWith("_ONGOING_ACTION")) {
            return false;
        }


        boolean isCustomerState = List.of("PLANNED", "DIAGNOSED", "DONE", "CLOSED_WARRANTY")
                .contains(pWorkOrder.getState());
        boolean isProviderState = List.of("ONEVALUATION", "CONFIRMED", "ONPROGRESS")
                .contains(pWorkOrder.getState());

        boolean isUserAuthorized = (isCustomerState && Objects.equals(pWorkOrder.getCustomer().getCustomerId(), pUser.getAuthId()))
                || (isProviderState && Objects.equals(pWorkOrder.getProvider().getProviderId(), pUser.getAuthId()));

        return mActivateUserTypeValidation != 1 || isUserAuthorized;
    }

    private boolean isWorkOrderEditableByExpiration(WorkOrder pWorkOrder){

        String mExpirationDate = getFieldToValidate(pWorkOrder);
        String mDateToValidate = getDateToValidate(pWorkOrder.getState());

        return Utils.isAfter(mExpirationDate, mDateToValidate);
    }
    public String getDateToValidate (String state) {
        return switch (state) {
            case "ONEVALUATION", "PLANNED", "DIAGNOSED", "ONPROGRESS", "CLOSED_WARRANTY" -> {
                yield Utils.getISOLocalDate();
            }
            case "CONFIRMED" -> {
                yield Utils.getISOLocalDatePlus(mMaxDaysOnConfState*(-1), Utils.getISOLocalDate());
            }
            case "DONE" -> {
                yield Utils.getISOLocalDatePlus(mAutoClosingDays*(-1), Utils.getISOLocalDate());
            }
            default -> null;
        };
    }
    public String getFieldToValidate(WorkOrder pWorkOrder) {
        return switch (pWorkOrder.getState()) {
            case "ONEVALUATION" -> pWorkOrder.getTimeLimit();
            case "PLANNED" -> pWorkOrder.getInspection().getInspectionTimeLimit();
            case "CONFIRMED" -> pWorkOrder.getInspection().getInspectionDate(); // + 48 horas
            case "DIAGNOSED" -> pWorkOrder.getWork().getProposalTimeLimitDate();
            case "ONPROGRESS" -> pWorkOrder.getWork().getWorkEndDate();
            case "DONE" -> pWorkOrder.getWork().getWorkEndDate(); // + 48 horas
            case "CLOSED_WARRANTY" -> pWorkOrder.getWork().getWorkWarrantyEndDate();
            default -> null;
        };
    }
    private void updateWorkOrderStateTemp(WorkOrder pPickedWorkOrder) {
        // actualizar pickedWorkOrder
        pPickedWorkOrder.setState(pPickedWorkOrder.getState() + "_ONGOING_ACTION");
        mMainActivityViewModel.setPickedWorkOrder(pPickedWorkOrder);

        // recuperar lista desde el LiveData
        List<WorkOrder> currentList = mMainActivityViewModel.getWorkOrder().getValue();
        if (currentList != null) {
            List<WorkOrder> updatedList = new ArrayList<>(currentList);
            for (int i = 0; i < updatedList.size(); i++) {
                WorkOrder wo = updatedList.get(i);
                if (Objects.equals(wo.getOrderId(), pPickedWorkOrder.getOrderId())) {
                    WorkOrder updatedWO = wo;
                    updatedWO.setState(pPickedWorkOrder.getState() + "_ONGOING_ACTION");
                    updatedList.set(i, updatedWO);
                    break;
                }
            }
            // volver a setear la lista con el método expuesto
            mMainActivityViewModel.setWorkOrder(updatedList);
        }
    }

}
