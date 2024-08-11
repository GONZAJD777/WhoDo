package com.example.whodo.app.features.activity.workOrder.workOrderState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;

public class ClosedState extends RelativeLayout {

    private TextView customerName_label;
    private TextView customerAddress_label;
    private TextView customerPhone_label;

    private TextView providerName_label;
    private TextView providerAddress_label;
    private TextView providerPhone_label;

    private TextView category_label;
    private TextView description_label;

    private TextView meetDate_label;
    private TextView meetTime_label;
    private TextView meetTariff_label;
    private TextView meetFee_label;

    private TextView workStartDate_label;
    private TextView workEndDate_label;
    private TextView materialCost_label;
    private TextView jobCost_label;
    private TextView jobFee_label;
    private TextView workDetail_label;

    private RatingBar providerAppereance_ratingBar;
    private RatingBar providerCleanliness_ratingBar;
    private RatingBar providerSpeed_ratingBar;
    private RatingBar providerQualityScore_ratingBar;
    private TextView providerReview_label;

    private TextView warrantyEndDate_label;




    public ClosedState(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_work_order_closed_state,(ViewGroup) getRootView());

        customerName_label=root.findViewById(R.id.customerName_label);
        customerAddress_label=root.findViewById(R.id.customerAddress_label);
        customerPhone_label=root.findViewById(R.id.customerPhone_label);

        providerName_label=root.findViewById(R.id.providerName_label);
        providerAddress_label=root.findViewById(R.id.providerAddress_label);
        providerPhone_label=root.findViewById(R.id.providerPhone_label);

        category_label=root.findViewById(R.id.category_label);
        description_label=root.findViewById(R.id.description_label);

        meetDate_label=root.findViewById(R.id.meetDate_label);
        meetTime_label=root.findViewById(R.id.meetTime_label);
        meetTariff_label=root.findViewById(R.id.meetTariff_label);
        meetFee_label=root.findViewById(R.id.meetFee_label);

        workStartDate_label=root.findViewById(R.id.workStartDate_label);
        workEndDate_label=root.findViewById(R.id.workEndDate_label);
        materialCost_label=root.findViewById(R.id.materialCost_label);
        jobCost_label=root.findViewById(R.id.jobCost_label);
        jobFee_label=root.findViewById(R.id.jobFee_label);
        workDetail_label=root.findViewById(R.id.workDetail_label);

        providerAppereance_ratingBar=root.findViewById(R.id.providerAppereance_ratingBar);
        providerCleanliness_ratingBar=root.findViewById(R.id.providerCleanliness_ratingBar);
        providerSpeed_ratingBar=root.findViewById(R.id.providerSpeed_ratingBar);
        providerQualityScore_ratingBar=root.findViewById(R.id.providerQualityScore_ratingBar);
        providerReview_label=root.findViewById(R.id.providerReview_label);

        warrantyEndDate_label=root.findViewById(R.id.warrantyEndDate_label);
    }

    public void setCustomerName(String pCustomerName){this.customerName_label.setText(pCustomerName);}
    public void setCustomerAddress(String pCustomerAddress){this.customerAddress_label.setText(pCustomerAddress);}
    public void setCustomerPhone(String pCustomerPhone){this.customerPhone_label.setText(pCustomerPhone);}

    public void setProviderName(String providerValue) { this.providerName_label.setText(providerValue); }
    public void setProviderAddress(String providerValue) { this.providerAddress_label.setText(providerValue); }
    public void setProviderPhone(String providerValue) { this.providerPhone_label.setText(providerValue); }

    public void setCategory(String pCategory){this.category_label.setText(pCategory);}
    public void setDescription(String pDescription){this.description_label.setText(pDescription);}

    public void setMeetDate(String pMeetDate) { this.meetDate_label.setText(pMeetDate); }
    public void setMeetTime(String pMeetTime) { this.meetTime_label.setText(pMeetTime); }
    public void setMeetTariff(String pMeetTariff) { this.meetTariff_label.setText(pMeetTariff); }
    public void setMeetFee(String pMeetFee) { this.meetFee_label.setText(pMeetFee); }

    public void setWorkStartDate(String pWorkStartDate) { this.workStartDate_label.setText(pWorkStartDate); }
    public void setWorkEndDate(String pWorkEndDate) { this.workEndDate_label.setText(pWorkEndDate); }
    public void setMaterialCost(String pMaterialCost) { this.materialCost_label.setText(pMaterialCost); }
    public void setJobCost(String pJobCost) { this.jobCost_label.setText(pJobCost); }
    public void setJobFee(String pJobFee) { this.jobFee_label.setText(pJobFee); }

    public void setWorkDetail(String pWorkDetail) { this.workDetail_label.setText(pWorkDetail); }


    public void setProviderReview(String pProviderReview) { this.providerReview_label.setText(pProviderReview); }
    public void setWarrantyEndDate(String pWarrantyEndDate) { this.warrantyEndDate_label.setText(pWarrantyEndDate); }

}
