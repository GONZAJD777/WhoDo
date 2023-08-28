package com.example.whodo.UiClasses;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.SwitchCompat;
import com.example.whodo.R;



public class ProfileSwitchItem extends RelativeLayout {
    private final ImageView mImageView;
    private final SwitchCompat mSwitch;

    public ProfileSwitchItem(Context context) {
        super(context);
        View root=inflate(context, R.layout.provider_mode_activator,(ViewGroup) getRootView());
        mSwitch = root.findViewById(R.id.SwitchProviderMode);
        mImageView = root.findViewById(R.id.imageView);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    compoundButton.setText(R.string.ProviderModeFrag_SwitchCustomerModeText_Off);
                } else {
                    compoundButton.setText(R.string.ProviderModeFrag_SwitchCustomerModeText_On);
                }
            }
        });
    }

    public void setImage (int image)
    {
        this.mImageView.setImageResource(image);
    }

    public void setSwitchState (Boolean pState){
        mSwitch.setChecked(pState);
    }
    public boolean getSwitchState (){ return mSwitch.isChecked(); }



}
