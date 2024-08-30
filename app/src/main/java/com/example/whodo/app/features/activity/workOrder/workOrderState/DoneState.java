package com.example.whodo.app.features.activity.workOrder.workOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;

public class DoneState extends RelativeLayout {

    private TextView mReviewWarrantyWarning_label;
    private TextView mReviewClosingWarning_label;

    private RatingBar providerAppereance_ratingBar;
    private RatingBar providerCleanliness_ratingBar;
    private RatingBar providerSpeed_ratingBar;
    private RatingBar providerQualityScore_ratingBar;

    private EditText providerReview_value;
    private Button acceptWorkOrder_button;
    private Button rejectWorkOrder_button;

    public DoneState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_done_state,(ViewGroup) getRootView());

        mReviewWarrantyWarning_label=root.findViewById(R.id.reviewWarning3_label);
        mReviewClosingWarning_label=root.findViewById(R.id.reviewWarning4_label);

        providerAppereance_ratingBar=root.findViewById(R.id.providerAppereance_ratingBar);
        providerCleanliness_ratingBar=root.findViewById(R.id.providerCleanliness_ratingBar);
        providerSpeed_ratingBar=root.findViewById(R.id.providerSpeed_ratingBar);
        providerQualityScore_ratingBar=root.findViewById(R.id.providerQualityScore_ratingBar);

        providerReview_value=root.findViewById(R.id.providerReview_value);
        acceptWorkOrder_button=root.findViewById(R.id.acceptWorkOrder_button);
        rejectWorkOrder_button=root.findViewById(R.id.rejectWorkOrder_button);

    }

    public void setReviewWarrantyWarning (String pReviewWarrantyWarning) {mReviewWarrantyWarning_label.setText(pReviewWarrantyWarning);}
    public void setReviewClosingWarning (String pReviewClosingWarning) {mReviewClosingWarning_label.setText(pReviewClosingWarning);}

    public int getProviderAppereanceScore () { return (int) providerAppereance_ratingBar.getRating(); }
    public int getProviderCleanlinessScore () { return (int) providerCleanliness_ratingBar.getRating(); }
    public int getProviderSpeedScore () { return (int) providerSpeed_ratingBar.getRating(); }
    public int getProviderQualityScoreScore () { return (int) providerQualityScore_ratingBar.getRating(); }

    public String getProviderReview () { return providerReview_value.getText().toString(); }

    public void setAcceptButtonOCL(OnClickListener OCL) {
        acceptWorkOrder_button.setOnClickListener(OCL);
    }
    public void setRejectButtonOCL(OnClickListener OCL) {
        rejectWorkOrder_button.setOnClickListener(OCL);
    }
    public void disableEdition(){
        providerAppereance_ratingBar.setEnabled(false);
        providerCleanliness_ratingBar.setEnabled(false);
        providerSpeed_ratingBar.setEnabled(false);
        providerQualityScore_ratingBar.setEnabled(false);

        providerReview_value.setEnabled(false);
        acceptWorkOrder_button.setEnabled(false);
        rejectWorkOrder_button.setEnabled(false);
    }

}
