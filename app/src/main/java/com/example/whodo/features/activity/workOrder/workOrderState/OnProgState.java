package com.example.whodo.features.activity.workOrder.workOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;

public class OnProgState extends RelativeLayout {

    private TextView customerName_label;
    private TextView customerAddress_label;
    private TextView customerPhone_label;

    private TextView workStartDate_label;
    private TextView workEndDate_label;
    private TextView materialCost_label;
    private TextView jobCost_label;
    private TextView jobFee_label;
    private TextView workDetail_label;

    private Button finishWorkOrder_button;



    public OnProgState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_onprog_state,(ViewGroup) getRootView());

        customerName_label= root.findViewById(R.id.customerName_label);
        customerAddress_label= root.findViewById(R.id.customerAddress_label);
        customerPhone_label= root.findViewById(R.id.customerPhone_label);

        workStartDate_label= root.findViewById(R.id.workStartDate_label);
        workEndDate_label= root.findViewById(R.id.workEndDate_label);
        materialCost_label= root.findViewById(R.id.materialCost_label);
        jobCost_label= root.findViewById(R.id.jobCost_label);
        jobFee_label= root.findViewById(R.id.jobFee_label);
        workDetail_label= root.findViewById(R.id.workDetail_label);

        finishWorkOrder_button= root.findViewById(R.id.finishWorkOrder_button);

    }

    public void setCustomerName(String pCustomerName){this.customerName_label.setText(pCustomerName);}
    public void setCustomerAddress(String pCustomerAddress){this.customerAddress_label.setText(pCustomerAddress);}
    public void setCustomerPhone(String pCustomerPhone){this.customerPhone_label.setText(pCustomerPhone);}

    public void setWorkStartDate(String pWorkStartDate){this.workStartDate_label.setText(pWorkStartDate);}
    public void setWorkEndDate(String pWorkEndDate){this.workEndDate_label.setText(pWorkEndDate);}
    public void setMaterialCost(String pMaterialCost){this.materialCost_label.setText(pMaterialCost);}
    public void setJobCost(String pJobCost){this.jobCost_label.setText(pJobCost);}
    public void setJobFee(String pJobFee) { this.jobFee_label.setText(pJobFee); }
    public void setWorkDetail(String pWorkDetail){this.workDetail_label.setText(pWorkDetail);}

    public void setFinishWorkOrderButtonOCL(OnClickListener OCL) {
        finishWorkOrder_button.setOnClickListener(OCL);
    }



}
