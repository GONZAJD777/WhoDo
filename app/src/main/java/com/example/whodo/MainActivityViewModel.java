package com.example.whodo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.whodo.BusinessClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivityViewModel extends ViewModel {


    private MutableLiveData<User> MutableLiveDataLoggedUser;
    private MutableLiveData<ArrayList<String>> MutableLiveDataLanguages;
    private MutableLiveData<ArrayList<String>> MutableLiveDataServices;
    private final User LoggedUser=new User();
    private final ArrayList<String> Services= new ArrayList<>();
    private final ArrayList<String> Languages= new ArrayList<>();
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


            MutableLiveDataLoggedUser = new MutableLiveData<>(new User());
            if(currentUser != null) {
                loadUser( currentUser.getUid()); ;
            }

        }

        return MutableLiveDataLoggedUser;
    }

    public LiveData<ArrayList<String>> getServices() {
        if (MutableLiveDataServices == null) {
            MutableLiveDataServices = new MutableLiveData<>();
            if(Services.isEmpty()) {
                loadServices(); ;
            }
        }
        return MutableLiveDataServices;
    }

    public LiveData<ArrayList<String>> getLanguages() {
        if (MutableLiveDataLanguages == null) {
            MutableLiveDataLanguages = new MutableLiveData<>();
            if(Languages.isEmpty()) {
                loadLanguages( );
            }
        }
        return MutableLiveDataLanguages;
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
                    LoggedUser.setPhone_ccn(UserSS.getPhone_ccn());
                    LoggedUser.setType(UserSS.getType());
                    LoggedUser.setPassword(UserSS.getPassword());
                    LoggedUser.setCreateDate(UserSS.getCreateDate());
                    LoggedUser.setDeleteDate(UserSS.getDeleteDate());
                    LoggedUser.setState(UserSS.getState());
                    LoggedUser.setIsValidated(UserSS.getIsValidated());
                    LoggedUser.setProfilePicture(UserSS.getProfilePicture());
                    LoggedUser.setLanguages(UserSS.getLanguages());
                    LoggedUser.setDescription(UserSS.getDescription());
                    Log.i("Operacion MainActivityViewModel.onDataChange() -->> URI:",UserSS.getProfilePicture());

                    MutableLiveDataLoggedUser.setValue(LoggedUser);
                    Log.i("Operacion MainActivityViewModel.onDataChange()",  "La informacion del usuario a cambiado");

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.i("Operacion MainActivityViewModel.loadUser",  "ERROR CONECTAnDO A LA BASE");


            }
        });


    }

    @SuppressLint("LongLogTag")
    private void loadLanguages() {
        Log.i("Operacion MainActivityViewModel.loadLanguages()",  "Consultando idiomas");
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("LANGUAGES");
        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Languages.add(ds.getValue(String.class));
                    //User UserSS = ds.getValue(User.class);
                    Log.i("Operacion MainActivityViewModel.loadLanguages() -->> Idioma:",ds.getValue(String.class));

                }
                MutableLiveDataLanguages.setValue(Languages);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Operacion MainActivityViewModel.loadLanguages()",  "Operacion devolvio error " + error );
            }
        });

    }
    @SuppressLint("LongLogTag")
    private void loadServices() {
        Log.i("Operacion MainActivityViewModel.loadServices()",  "Consultando Servicios");
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("SERVICES");
        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    Services.add(ds.getValue(String.class));
                    //User UserSS = ds.getValue(User.class);
                    Log.i("Operacion MainActivityViewModel.loadServices() -->> Servicios:",Services.get(Integer.parseInt(Objects.requireNonNull(ds.getKey()))));

                }
                MutableLiveDataServices.setValue(Services);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Operacion MainActivityViewModel.loadServices()",  "Operacion devolvio error " + error );
            }
        });
    }




}
