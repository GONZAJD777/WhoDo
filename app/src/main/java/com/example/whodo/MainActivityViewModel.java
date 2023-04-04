package com.example.whodo;

import static com.example.whodo.MainActivity.getLoggedUser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.whodo.BusinessClasses.User;
import com.example.whodo.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class MainActivityViewModel extends ViewModel {


    private MutableLiveData<User> MutableLiveDataLoggedUser;
    private final User LoggedUser=new User();
    // Initialize Firebase Auth
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    // Check if user is signed in (non-null) and update UI accordingly.
    private final FirebaseUser currentUser = mAuth.getCurrentUser();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    // Create a child reference
    // imagesRef now points to "WHODO-IMAGES"



    public LiveData<User> getLoggedUser() {
        if (MutableLiveDataLoggedUser == null) {


            MutableLiveDataLoggedUser = new MutableLiveData(new User());
            if(currentUser != null) {
                loadUser( currentUser.getUid()); ;
            }

        }

        return MutableLiveDataLoggedUser;
    }


    @SuppressLint("LongLogTag")
    private void loadUser(String uid) {
        Log.i("Operacion MainActivityViewModel.loadUser()",  "String recibido como parametro " + uid);
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("USERS");
        DBRef.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    User UserSS = ds.getValue(User.class);
                    assert UserSS != null;
                    LoggedUser.setUid(UserSS.getUid());
                    LoggedUser.setName(UserSS.getName());
                    LoggedUser.setBirthday(UserSS.getBirthday());
                    LoggedUser.setEmail(UserSS.getEmail());
                    LoggedUser.setAddress(UserSS.getAddress());
                    LoggedUser.setLatitude(UserSS.getLatitude());
                    LoggedUser.setLongitude(UserSS.getLongitude());
                    LoggedUser.setPhone(UserSS.getPhone());
                    LoggedUser.setType(UserSS.getType());
                    LoggedUser.setPassword(UserSS.getPassword());
                    LoggedUser.setCreateDate(UserSS.getCreateDate());
                    LoggedUser.setDeleteDate(UserSS.getDeleteDate());
                    LoggedUser.setState(UserSS.getState());
                    LoggedUser.setIsValidated(UserSS.getIsValidated());
                    LoggedUser.setProfilePicture(UserSS.getProfilePicture());
                    Log.i("Operacion MainActivityViewModel.onDataChange() -->> URI:",UserSS.getProfilePicture());

                    MutableLiveDataLoggedUser.setValue(LoggedUser);
                    Log.i("Operacion MainActivityViewModel.onDataChange()",  "La informacion del usuario a cambiado");

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Operacion MainActivityViewModel.loadUser()",  "Operacion devolvio error " + error );
            }
        });


    }



}
