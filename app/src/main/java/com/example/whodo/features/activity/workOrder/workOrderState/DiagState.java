package com.example.whodo.features.activity.workOrder.workOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;
import com.google.android.material.textfield.TextInputLayout;

public class DiagState extends RelativeLayout {

    private TextView providerName_label;
    private TextView providerAddress_label;
    private TextView providerPhone_label;

    private TextView workStartDate_label;
    private TextView workEndDate_label;
    private TextView materialCost_label;
    private TextView jobCost_label;
    private TextView jobFee_label;
    private TextView workDetail_label;

    private TextView paymentOrder_label;
    private EditText invoice_value;

    private TextInputLayout invoice_inputLayout;
    private Button genPaymentOrder_button;
    private Button acceptWorkOrder_button;
    private Button rejectWorkOrder_button;


    public DiagState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_diag_state,(ViewGroup) getRootView());

        providerName_label=root.findViewById(R.id.providerName_label);
        providerAddress_label=root.findViewById(R.id.providerAddress_label);
        providerPhone_label=root.findViewById(R.id.providerPhone_label);

        workStartDate_label=root.findViewById(R.id.workStartDate_label);
        workEndDate_label=root.findViewById(R.id.workEndDate_label);
        materialCost_label=root.findViewById(R.id.materialCost_label);
        jobCost_label=root.findViewById(R.id.jobCost_label);
        jobFee_label=root.findViewById(R.id.jobFee_label);
        workDetail_label=root.findViewById(R.id.workDetail_label);

        paymentOrder_label=root.findViewById(R.id.paymentOrder_label);
        invoice_value=root.findViewById(R.id.invoice_value);

        invoice_inputLayout=root.findViewById(R.id.invoice_inputLayout);
        genPaymentOrder_button=root.findViewById(R.id.genPaymentOrder_button);
        acceptWorkOrder_button=root.findViewById(R.id.acceptWorkOrder_button);
        rejectWorkOrder_button=root.findViewById(R.id.rejectWorkOrder_button);
    }

    public void setProviderName(String pProviderName) { this.providerName_label.setText(pProviderName); }
    public void setProviderAddress(String pProviderAddress) { this.providerAddress_label.setText(pProviderAddress); }
    public void setProviderPhone(String pProviderPhoneNumber) { this.providerPhone_label.setText(pProviderPhoneNumber); }

    public void setWorkStartDate(String pWorkStartDate) { this.workStartDate_label.setText(pWorkStartDate); }
    public void setWorkEndDate(String pWorkEndDate) { this.workEndDate_label.setText(pWorkEndDate); }
    public void setMaterialCost(String pMaterialCost) { this.materialCost_label.setText(pMaterialCost); }
    public void setJobCost(String pJobCost) { this.jobCost_label.setText(pJobCost); }
    public void setJobFee(String pJobFee) { this.jobFee_label.setText(pJobFee);  }
    public void setWorkDetail(String pWorkDetail) { this.workDetail_label.setText(pWorkDetail); }


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
