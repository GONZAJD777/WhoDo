package com.example.whodo.UiClasses;

import static android.view.View.inflate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.whodo.R;

public class RatingStars extends RelativeLayout {

    private final ImageView star1;
    private final ImageView star2;
    private final ImageView star3;
    private final ImageView star4;
    private final ImageView star5;
    private final TextView LeftTextView;
    private final TextView RightTextView;


    public RatingStars(Context context) {
        super(context);
        View root=inflate(context, R.layout.rating_stars,(ViewGroup) getRootView());
        star1 = root.findViewById(R.id.star1_imageView);
        star2 = root.findViewById(R.id.star2_imageView);
        star3 = root.findViewById(R.id.star3_imageView);
        star4 = root.findViewById(R.id.star4_imageView);
        star5 = root.findViewById(R.id.star5_imageView);
        LeftTextView = root.findViewById(R.id.LeftTextView);
        RightTextView = root.findViewById(R.id.RigthTextView);
    }
    public void setLeftTextView (String pText){
        this.LeftTextView.setText(pText);
    }
    public void setRightTextView(String pText){
        this.RightTextView.setText(pText);
    }
    public void setStars (double pStars){
        if (pStars > 0 ){   //0.5
            if (pStars < 1){
                this.setStar1("HALF");
            } else {
                this.setStar1("FULL");
            }
        }else {
            this.setStar1("EMPTY");
        }

        if (pStars > 1 ){ //1.5
            if (pStars < 2){
                this.setStar2("HALF");
            } else {
                this.setStar2("FULL");
            }
        }else {
            this.setStar2("EMPTY");
        }

        if (pStars > 2 ){ //2.5
            if (pStars < 3){
                this.setStar3("HALF");
            } else {
                this.setStar3("FULL");
            }
        }else {
            this.setStar3("EMPTY");
        }

        if (pStars > 3 ){
            if (pStars < 4){
                this.setStar4("HALF");
            } else {
                this.setStar4("FULL");
            }
        }else {
            this.setStar4("EMPTY");
        }

        if (pStars > 4 ){
            if (pStars < 5){
                this.setStar5("HALF");
            } else {
                this.setStar5("FULL");
            }
        }else {
            this.setStar5("EMPTY");
        }

    }

    private void setStar1(String pState){
        switch (pState){
        case "FULL": star1.setImageResource(R.drawable.full_star_16);
            break;
        case "HALF": star1.setImageResource(R.drawable.half_star_16);
            break;
        case "EMPTY": star1.setImageResource(R.drawable.empty_star_16);
            break;
        }

    }
    private void setStar2(String pState){  switch (pState){
        case "FULL": star2.setImageResource(R.drawable.full_star_16);
            break;
        case "HALF": star2.setImageResource(R.drawable.half_star_16);
            break;
        case "EMPTY": star2.setImageResource(R.drawable.empty_star_16);
            break;
    }}
    private void setStar3(String pState){  switch (pState){
        case "FULL": star3.setImageResource(R.drawable.full_star_16);
            break;
        case "HALF": star3.setImageResource(R.drawable.half_star_16);
            break;
        case "EMPTY": star3.setImageResource(R.drawable.empty_star_16);
            break;
    }}
    private void setStar4(String pState){  switch (pState){
        case "FULL": star4.setImageResource(R.drawable.full_star_16);
            break;
        case "HALF": star4.setImageResource(R.drawable.half_star_16);
            break;
        case "EMPTY": star4.setImageResource(R.drawable.empty_star_16);
            break;
    }}
    private void setStar5(String pState){  switch (pState){
        case "FULL": star5.setImageResource(R.drawable.full_star_16);
            break;
        case "HALF": star5.setImageResource(R.drawable.half_star_16);
            break;
        case "EMPTY": star5.setImageResource(R.drawable.empty_star_16);
            break;
    }}

}
