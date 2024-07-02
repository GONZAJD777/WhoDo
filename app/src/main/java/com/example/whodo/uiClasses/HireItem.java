package com.example.whodo.uiClasses;


import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.domain.user.User;
import com.example.whodo.R;
import com.squareup.picasso.Picasso;


public class HireItem extends RelativeLayout {

    private TextView textView_Name;
    private TextView textView_Reviews;
    private TextView textView_PricePercent;
    private TextView textView_Speed;
    private LinearLayout LinearLayout_Spec;
    private ImageView imageView_Hire;
    private Button button_ItemHire;
    private User Provider;
    private OnClickListener onClickListener;

    public HireItem(Context context) {
        super(context);

        View root=inflate(context, R.layout.item_hire,(ViewGroup) getRootView());
        textView_Name = root.findViewById(R.id.textView_Name);
        textView_Reviews = root.findViewById(R.id.textView_Reviews);
        textView_PricePercent = root.findViewById(R.id.textView_PricePercent);
        textView_Speed = root.findViewById(R.id.textView_Speed);
        imageView_Hire = root.findViewById(R.id.imageView_Hire);
        button_ItemHire = root.findViewById(R.id.button_ItemHire);
        LinearLayout_Spec = root.findViewById(R.id.LinearLayout_Specs);


        button_ItemHire.setOnClickListener(onClickListener);
    }
    public void setProvider (User pProvider)
    {
        this.Provider=pProvider;
    }
    public User getProvider () { return Provider; }
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
    public void setImage (String image)
    {
        Picasso.get().load(image).into(imageView_Hire);
    }
    public void addSpecItem (View pItemSpec) {
        this.LinearLayout_Spec.addView(pItemSpec);
    }
    // Metodo para asignar listener al objeto
    @Override
    public void setOnClickListener(OnClickListener OCL) {
        this.onClickListener=OCL;
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP &&
                (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(onClickListener != null) onClickListener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setPressed(true);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            if(onClickListener != null) onClickListener.onClick(this);
            setPressed(false);
        }
        else {
            setPressed(false);
        }
        return super.dispatchTouchEvent(event);
    }

}
