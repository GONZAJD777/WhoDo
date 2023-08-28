package com.example.whodo.UiClasses;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.whodo.R;
import com.squareup.picasso.Picasso;


public class HireItem extends RelativeLayout {

    private TextView textView_Name;
    private TextView textView_Reviews;
    private TextView textView_PricePercent;
    private TextView textView_Speed;
    private TextView textView_Spec;
    private ImageView imageView_Hire;
    private Button button_ItemHire;

    private OnClickListener onClickListener;

    public HireItem(Context context) {
        super(context);

        View root=inflate(context, R.layout.item_hire,(ViewGroup) getRootView());
        textView_Name = root.findViewById(R.id.textView_Name);
        textView_Reviews = root.findViewById(R.id.textView_Reviews);
        textView_PricePercent = root.findViewById(R.id.textView_PricePercent);
        textView_Speed = root.findViewById(R.id.textView_Speed);
        textView_Spec = root.findViewById(R.id.textView_Spec);
        imageView_Hire = root.findViewById(R.id.imageView_Hire);
        button_ItemHire = root.findViewById(R.id.button_ItemHire);

        button_ItemHire.setOnClickListener(onClickListener);
    }

    public void setName (String Text)
    {
        this.textView_Name.setText(Text);
    }
    public void setReviews (String Text)
    {
        this.textView_Reviews.setText(Text);
    }
    public void PricePercent (String Text)
    {
        this.textView_PricePercent.setText(Text);
    }
    public void setSpeed (String Text)
    {
        this.textView_Speed.setText(Text);
    }
    public void setSpec (String Text)
    {
        this.textView_Spec.setText(Text);
    }
    public void setImage (String image)
    {
        Picasso.get().load(image).into(imageView_Hire);
    }

    // Metodo para asignar listener al objeto
    @Override
    public void setOnClickListener(OnClickListener OCL) {onClickListener=OCL;
    }



}
