package com.example.whodo.features.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whodo.R;

public class ActivityWorkOrderItem extends RelativeLayout {
    private final ImageView mCategory_imageView;
    private final TextView mActionIndicator_label;
    private final TextView mOrderState_label;
    private final TextView mLimitDate_label;
    private final TextView mLastUpdate_label;
    private OnClickListener mOnClickListener;


    public ActivityWorkOrderItem(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_activity,(ViewGroup) getRootView());

        mCategory_imageView = root.findViewById(R.id.category_imageView);
        mActionIndicator_label = root.findViewById(R.id.actionIndicator_label);
        mOrderState_label = root.findViewById(R.id.orderState_label);
        mLimitDate_label = root.findViewById(R.id.limitDate_label);
        mLastUpdate_label = root.findViewById(R.id.lastUpdate_label);
        Button mOpenWorkOrderDetail_button = root.findViewById(R.id.openWorkOrderDetail_button);
        mOpenWorkOrderDetail_button.setOnClickListener(mOnClickListener);

    }

    public void setCategoryImage(Drawable pDrawableImage) { mCategory_imageView.setImageDrawable(pDrawableImage); }
    public void setActionIndicator(Drawable pDrawableImage) { this.mActionIndicator_label.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,pDrawableImage,null); }
    public void setOrderState(String pOrderState) { this.mOrderState_label.setText(pOrderState); }
    public void setLimitDate(String pLimitDate) { this.mLimitDate_label.setText(pLimitDate); }
    public void setLastUpdate(String pLastUpdate) { this.mLastUpdate_label.setText(pLastUpdate); }

    // Metodo para asignar listener al objeto
    @Override
    public void setOnClickListener(OnClickListener OCL) {mOnClickListener=OCL;
    }
    // sobreescribimos los metodos del listener para que tome los intrucciones
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP &&
                (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(mOnClickListener != null) mOnClickListener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setPressed(true);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            if(mOnClickListener != null) mOnClickListener.onClick(this);
            setPressed(false);
        }
        else {
            setPressed(false);
        }
        return super.dispatchTouchEvent(event);
    }
}
