package com.example.whodo.features.hire.WorkOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;
import com.example.whodo.uiClasses.ProfileItem;
import com.google.android.material.textfield.TextInputLayout;

public class PlannedState extends RelativeLayout {

    private TextView providerName_label;
    private TextView providerAddress_label;
    private TextView providerPhone_label;

    private TextView meetDate_label;
    private TextView meetTime_label;
    private TextView meetTariff_label;
    private TextView meetFee_label;


    private TextView paymentOrder_label;
    private EditText invoice_value;

    private TextInputLayout invoice_inputLayout;
    private Button genPaymentOrder_button;
    private Button acceptWorkOrder_button;
    private Button rejectWorkOrder_button;

    public PlannedState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_planned_state,(ViewGroup) getRootView());

        providerName_label= root.findViewById(R.id.providerName_label);
        providerAddress_label= root.findViewById(R.id.providerAddress_label);
        providerPhone_label= root.findViewById(R.id.providerPhone_label);

        meetDate_label= root.findViewById(R.id.meetDate_label);
        meetTime_label= root.findViewById(R.id.meetTime_label);
        meetTariff_label= root.findViewById(R.id.meetTariff_label);
        meetFee_label=root.findViewById(R.id.meetFee_label);

        paymentOrder_label= root.findViewById(R.id.paymentOrder_label);
        invoice_value= root.findViewById(R.id.invoice_value);

        invoice_inputLayout= root.findViewById(R.id.invoice_inputLayout);
        genPaymentOrder_button= root.findViewById(R.id.genPaymentOrder_button);
        acceptWorkOrder_button= root.findViewById(R.id.acceptWorkOrder_button);
        rejectWorkOrder_button= root.findViewById(R.id.rejectWorkOrder_button);

    }

    public void setProviderName(String pProviderName) { this.providerName_label.setText(pProviderName); }
    public void setProviderAddress(String pProviderAddress) { this.providerAddress_label.setText(pProviderAddress); }
    public void setProviderPhone(String pProviderPhoneNumber) { this.providerPhone_label.setText(pProviderPhoneNumber); }

    public void setMeetDate(String pMeetDate) { this.meetDate_label.setText(pMeetDate); }
    public void setMeetTime(String pMeetTime) { this.meetTime_label.setText(pMeetTime); }
    public void setMeetTariff(String pMeetTariff) { this.meetTariff_label.setText(pMeetTariff); }
    public void setMeetFee(String pMeetFee) { this.meetFee_label.setText(pMeetFee); }


    public void setPaymentOrder(String pPaymentOrder) { this.paymentOrder_label.setText(pPaymentOrder); }
    public void setInvoice(String pInvoice) { this.invoice_value.setText(pInvoice); }
    public String getInvoice() { return this.invoice_value.getText().toString(); }

    public void setInputLayoutEndIconOCL(OnClickListener OCL) {
        invoice_inputLayout.setEndIconOnClickListener(OCL);
    }
    public void setGenPaymentOrderButtonOCL(OnClickListener OCL) {
        genPaymentOrder_button.setOnClickListener(OCL);
    }
    public void setAcceptButtonOCL(OnClickListener OCL) {
        acceptWorkOrder_button.setOnClickListener(OCL);
    }
    public void setRejectButtonOCL(OnClickListener OCL) {
        rejectWorkOrder_button.setOnClickListener(OCL);
    }

}
