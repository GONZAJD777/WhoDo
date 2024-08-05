package com.example.whodo.features.activity.workOrder.workOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.whodo.R;

public class OpenState extends RelativeLayout {


    private TextView providerName_label;
    private TextView providerAddress_label;
    private TextView providerPhone_label;
    private EditText timeLimit_value;
    private Spinner category_value;
    private EditText description_value;
    private Button createWorkOrder_button;


    public OpenState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_open_state,(ViewGroup) getRootView());

        providerName_label= root.findViewById(R.id.providerName_label);
        providerAddress_label= root.findViewById(R.id.providerAddress_label);
        providerPhone_label= root.findViewById(R.id.providerPhone_label);
        timeLimit_value= root.findViewById(R.id.timeLimit_value);
        category_value= root.findViewById(R.id.category_value);
        description_value= root.findViewById(R.id.description_value);
        createWorkOrder_button= root.findViewById(R.id.createWorkOrder_button);
    }

    public void setProviderName(String providerValue) { this.providerName_label.setText(providerValue); }
    public void setProviderAddress(String providerValue) { this.providerAddress_label.setText(providerValue); }
    public void setProviderPhone(String providerValue) { this.providerPhone_label.setText(providerValue); }

    public Integer getTimeLimitValue() {
        if (timeLimit_value.getText().toString().isEmpty()) {return 1;}
        else {return Integer.valueOf(timeLimit_value.getText().toString());}
    }

    //Metodo para asignar valores al spinner
    public void setSpinnerValues(String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_value.setAdapter(adapter);
    }
    //Metodo para obtener el valor seleccionado en el spinner

    public String getCategoryValue() {
        return category_value.getSelectedItem().toString();
    }

    public String getDescriptionValue() {
        return description_value.getText().toString();
    }

    public void setOnClickListener (OnClickListener OCL) {
        createWorkOrder_button.setOnClickListener(OCL);
    }
}
