package com.example.whodo.features.hire.WorkOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.whodo.R;

public class DiagState extends RelativeLayout {
    public DiagState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_diag_state,(ViewGroup) getRootView());

    }
}
