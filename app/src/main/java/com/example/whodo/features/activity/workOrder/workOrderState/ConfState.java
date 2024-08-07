package com.example.whodo.features.activity.workOrder.workOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;

public class ConfState extends RelativeLayout {

    private TextView customerName_label;
    private TextView customerAddress_label;
    private TextView customerPhone_label;

    private TextView category_label;
    private TextView description_label;

    private TextView meetDate_label;
    private TextView meetTime_label;
    private TextView meetTariff_label;
    private TextView meetFee_label;

    private EditText workStartDate_value;
    private EditText workEndDate_value;
    private EditText workMaterialCost_value;
    private EditText workJobCost_value;
    private TextView jobFee_label;
    private EditText workTaskDetail_value;
    private Button presentOrder_button;


    public ConfState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_conf_state,(ViewGroup) getRootView());

        customerName_label= root.findViewById(R.id.customerName_label);
        customerAddress_label= root.findViewById(R.id.customerAddress_label);
        customerPhone_label= root.findViewById(R.id.customerPhone_label);

        category_label= root.findViewById(R.id.category_label);
        description_label= root.findViewById(R.id.description_label);

        meetDate_label= root.findViewById(R.id.meetDate_label);
        meetTime_label= root.findViewById(R.id.meetTime_label);
        meetTariff_label= root.findViewById(R.id.meetTariff_label);
        meetFee_label= root.findViewById(R.id.meetFee_label);

        workStartDate_value= root.findViewById(R.id.workStartDate_value);
        workEndDate_value= root.findViewById(R.id.workEndDate_value);
        workMaterialCost_value= root.findViewById(R.id.workMaterialCost_value);
        workJobCost_value= root.findViewById(R.id.workJobCost_value);
        jobFee_label= root.findViewById(R.id.jobFee_label);
        workTaskDetail_value= root.findViewById(R.id.workTaskDetail_value);

        presentOrder_button= root.findViewById(R.id.presentOrder_button);
    }

    public void setCustomerName(String pCustomerName){this.customerName_label.setText(pCustomerName);}
    public void setCustomerAddress(String pCustomerAddress){this.customerAddress_label.setText(pCustomerAddress);}
    public void setCustomerPhone(String pCustomerPhone){this.customerPhone_label.setText(pCustomerPhone);}

    public void setCategory(String pCategory){this.category_label.setText(pCategory);}
    public void setDescription(String pDescription){this.description_label.setText(pDescription);}

    public void setMeetDate(String pMeetDate) { this.meetDate_label.setText(pMeetDate); }
    public void setMeetTime(String pMeetTime) { this.meetTime_label.setText(pMeetTime); }
    public void setMeetTariff(String pMeetTariff) { this.meetTariff_label.setText(pMeetTariff); }
    public void setMeetFee(String pMeetFee) { this.meetFee_label.setText(pMeetFee); }

    public String getWorkStartDate(){ return workStartDate_value.getText().toString(); }
    public String getWorkEndDate(){ return workEndDate_value.getText().toString(); }
    public String getWorkMaterialCost(){ return workMaterialCost_value.getText().toString(); }
    public String getWorkJobCost(){ return workJobCost_value.getText().toString(); }
    public String getWorkTaskDetail(){ return workTaskDetail_value.getText().toString(); }
    public void setJobFee(String pJobFee) { this.jobFee_label.setText(pJobFee); }


    public void setPresentOrderButtonOCL(OnClickListener OCL) {
        presentOrder_button.setOnClickListener(OCL);
    }

}
