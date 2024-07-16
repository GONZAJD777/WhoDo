package com.example.whodo.features.hire.WorkOrderState;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
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

    private TextView timeLimit_label;
    private EditText timeLimit_value;
    private TextView provider_label;
    private TextView provider_value;
    private TextView category_label;
    private Spinner category_value;
    private TextView description_label;
    private EditText description_value;
    private Button createWorkOrder_button;
    //private OnClickListener onClickListener;


    public OpenState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_open_state,(ViewGroup) getRootView());

        timeLimit_label = root.findViewById(R.id.timeLimit_label);
        timeLimit_value= root.findViewById(R.id.timeLimit_value);
        provider_label= root.findViewById(R.id.provider_label);
        provider_value= root.findViewById(R.id.provider_value);
        category_label= root.findViewById(R.id.category_label);
        category_value= root.findViewById(R.id.category_value);
        description_label= root.findViewById(R.id.description_label);
        description_value= root.findViewById(R.id.description_value);
        createWorkOrder_button= root.findViewById(R.id.createWorkOrder_button);
        //createWorkOrder_button.setOnClickListener(onClickListener);
    }

    public Button getCreateWorkOrder_button() {return createWorkOrder_button;}

    // Setter para timeLimit_value
    public void setTimeLimitValue(String timeLimitValue) { this.timeLimit_value.setText(timeLimitValue); }
    // Getter para timeLimit_value
    public Integer getTimeLimitValue() {
        if (timeLimit_value.getText().toString().isEmpty()) {return 1;}
        else {return Integer.valueOf(timeLimit_value.getText().toString());}
    }
    // Setter para provider_value
    public void setProviderValue(String providerValue) { this.provider_value.setText(providerValue); }
    // Getter para provider_value
    public String getProviderValue() {
        return provider_value.getText().toString();
    }
    // Setter para category_value
    // Setter para configurar los valores del Spinner
    public void setSpinnerValues(String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_value.setAdapter(adapter);
    }
    // Getter para category_value
    public String getCategoryValue() {
        return category_value.getSelectedItem().toString();
    }
    // Setter para description_value
    public void setDescriptionValue(String descriptionValue) { this.description_value.setText(descriptionValue); }
    // Getter para description_value
    public String getDescriptionValue() {
        return description_value.getText().toString();
    }


}
