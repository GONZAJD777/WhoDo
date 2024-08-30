package com.example.whodo.app.features.activity.workOrder.workOrderState;

import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;

public class OnEvalState extends RelativeLayout {



    private TextView customerName_label;
    private TextView customerAddress_label;
    private TextView customerPhone_label;

    private TextView limitDate_label;
    private TextView category_label;
    private TextView description_label;

    private EditText meetDate_value;
    private EditText meetTime_value;
    private EditText meetTariff_value;
    private TextView meetFee_label;


    private Button acceptWorkOrder_button;
    private Button rejectWorkOrder_button;

    public OnEvalState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_oneval_state,(ViewGroup) getRootView());

        //********************* Customer Info *********************//
        customerName_label=root.findViewById(R.id.customerName_label);
        customerAddress_label=root.findViewById(R.id.customerAddress_label);
        customerPhone_label=root.findViewById(R.id.customerPhone_label);

        //********************* Order Detail *********************//
        limitDate_label=root.findViewById(R.id.limitDate_label);
        category_label=root.findViewById(R.id.category_label);
        description_label=root.findViewById(R.id.description_label);

        //********************* Meeting Detail *********************//
        meetDate_value=root.findViewById(R.id.meetDate_value);
        meetTime_value=root.findViewById(R.id.meetTime_value);
        meetTariff_value=root.findViewById(R.id.meetTariff_value);
        meetFee_label=root.findViewById(R.id.meetFee_label);


        acceptWorkOrder_button=root.findViewById(R.id.acceptWorkOrder_button);
        rejectWorkOrder_button=root.findViewById(R.id.rejectWorkOrder_button);
    }

    public void setCustomerName(String pCustomerName){this.customerName_label.setText(pCustomerName);}
    public void setCustomerAddress(String pCustomerAddress){this.customerAddress_label.setText(pCustomerAddress);}
    public void setCustomerPhone(String pCustomerPhone){this.customerPhone_label.setText(pCustomerPhone);}

    public void setLimitDate(String pLimitDate){ this.limitDate_label.setText(pLimitDate);}
    public void setCategory(String pCategory){this.category_label.setText(pCategory);}
    public void setDescription(String pDescription){this.description_label.setText(pDescription);}

    public String getMeetDate(){ return meetDate_value.getText().toString(); }
    public void setMeetDate (String pMeetDate) {meetDate_value.setText(pMeetDate);}
    public String getMeetTime(){ return meetTime_value.getText().toString(); }
    public void setMeetTime (String pMeetTime) {meetTime_value.setText(pMeetTime);}
    public String getMeetTariff(){ return meetTariff_value.getText().toString(); }
    public void setMeetFee(String pMeetFee) { this.meetFee_label.setText(pMeetFee); }

    public void setMeetDateTCL(TextWatcher TW) {
        meetTariff_value.addTextChangedListener(TW);
    }
    public void setMeetDateOCL(OnClickListener OCL) {
        meetDate_value.setOnClickListener(OCL);
    }
    public void setMeetTimeOCL(OnClickListener OCL) {
        meetTime_value.setOnClickListener(OCL);
    }
    public void setAcceptButtonOCL(OnClickListener OCL) {
        acceptWorkOrder_button.setOnClickListener(OCL);
    }
    public void setRejectButtonOCL(OnClickListener OCL) {
        rejectWorkOrder_button.setOnClickListener(OCL);
    }


    public void disableEdition(){

        meetDate_value.setEnabled(false);
        meetTime_value.setEnabled(false);
        meetTariff_value.setEnabled(false);
        acceptWorkOrder_button.setEnabled(false);
        rejectWorkOrder_button.setEnabled(false);
    }


}
