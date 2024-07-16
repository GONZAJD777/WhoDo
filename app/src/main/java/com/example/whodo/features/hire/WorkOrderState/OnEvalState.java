package com.example.whodo.features.hire.WorkOrderState;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;

public class OnEvalState extends RelativeLayout {

    private TextView limitDate_label;
    private TextView limitDate_value;
    private TextView customerName_label;
    private TextView customerName_value;
    private TextView customerAddress_label;
    private TextView customerAddress_value;
    private TextView category_label;
    private TextView category_value;
    private TextView description_label;
    private TextView description_value;
    private TextView meetDate_label;
    private EditText meetDate_value;
    private TextView meetTime_label;
    private EditText meetTime_value;
    private TextView meetTariff_label;
    private EditText meetTariff_value;
    private Button acceptWorkOrder_button;
    private Button rejectWorkOrder_button;
    private OnClickListener onClickListener;

    public OnEvalState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_oneval_state,(ViewGroup) getRootView());

        limitDate_label=root.findViewById(R.id.limitDate_label);
        limitDate_value=root.findViewById(R.id.limitDate_value);
        customerName_label=root.findViewById(R.id.customerName_label);
        customerName_value=root.findViewById(R.id.customerName_value);
        customerAddress_label=root.findViewById(R.id.customerAddress_label);
        customerAddress_value=root.findViewById(R.id.customerAddress_value);
        category_label=root.findViewById(R.id.category_label);
        category_value=root.findViewById(R.id.category_value);
        description_label=root.findViewById(R.id.description_label);
        description_value=root.findViewById(R.id.description_value);
        meetDate_label=root.findViewById(R.id.meetDate_label);
        meetDate_value=root.findViewById(R.id.meetDate_value);
        meetTime_label=root.findViewById(R.id.meetTime_label);
        meetTime_value=root.findViewById(R.id.meetTime_value);
        meetTariff_label=root.findViewById(R.id.meetTariff_label);
        meetTariff_value=root.findViewById(R.id.meetTariff_value);
        acceptWorkOrder_button=root.findViewById(R.id.acceptWorkOrder_button);
        rejectWorkOrder_button=root.findViewById(R.id.rejectWorkOrder_button);

        acceptWorkOrder_button.setOnClickListener(onClickListener);
        rejectWorkOrder_button.setOnClickListener(onClickListener);

    }
    public void setLimitDate(String pLimitDate){ this.limitDate_value.setText(pLimitDate);}
    public void setCustomerName(String pCustomerName){this.customerName_value.setText(pCustomerName);}
    public void setCustomerAddress(String pCustomerAddress){this.customerAddress_value.setText(pCustomerAddress);}
    public void setCategory(String pCategory){this.category_value.setText(pCategory);}
    public void setDescription(String pDescription){this.description_value.setText(pDescription);}


}
