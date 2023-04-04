package com.example.whodo.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.whodo.R;

public class ProfileItem extends RelativeLayout {

    private final ImageView imageView;
    private final TextView textView1;

    private OnClickListener onClickListener;
    private Button button;

    public ProfileItem(Context context) {
        super(context);
        View root=inflate(context, R.layout.item_profile,(ViewGroup) getRootView());
        textView1 = root.findViewById(R.id.textView);
        button = root.findViewById(R.id.button);
        imageView = root.findViewById(R.id.imageView);

        button.setOnClickListener(onClickListener);
        
    }




    public void setText (String Text)
    {
        this.textView1.setText(Text);
    }
    public void setImage (int image)
    {
        this.imageView.setImageResource(image);
    }


    // Metodo para asignar listener al objeto
    @Override
    public void setOnClickListener(OnClickListener OCL) {onClickListener=OCL;
    }
    // sobreescribimos los metodos del listener para que tome los intrucciones
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
