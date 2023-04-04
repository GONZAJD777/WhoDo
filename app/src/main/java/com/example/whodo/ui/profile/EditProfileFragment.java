package com.example.whodo.ui.profile;

import static com.example.whodo.MainActivity.getLoggedUser;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.whodo.R;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ImageView imagePicker;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.act_profile_frag_edit_profile, container, false);

        EditText DescriptionSimpleEditText = root.findViewById(R.id.DescriptionSimpleEditText);

        DescriptionSimpleEditText.setOnTouchListener((view, motionEvent) -> {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });

        imagePicker = root.findViewById(R.id.imagePicker);
        Picasso.get().load(getLoggedUser().getProfilePicture()).into(imagePicker);

        imagePicker.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG).show();
            SelectImage();
        });

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                Picasso.get().load(uri).into(imagePicker);
                //uploadProfileImage();
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });




        return root;
    }

    // Select Image method
    private void SelectImage() {
        ActivityResultContracts.PickVisualMedia.ImageOnly VMtype = ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE;
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(VMtype)
                .build());
    }


}

