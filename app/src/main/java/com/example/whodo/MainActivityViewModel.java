package com.example.whodo;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.whodo.BusinessClasses.User;
import com.example.whodo.BusinessClasses.UserSpecRating;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivityViewModel extends ViewModel {

    private final String TAG1="MAIN-ACTIVITY-VIEW-MODEL";
    private MutableLiveData<User> MutableLiveDataLoggedUser;
    private MutableLiveData<User> MutableLiveDataLoggedUserOnce;
    private MutableLiveData<ArrayList<String>> MutableLiveDataLanguages;
    private MutableLiveData<ArrayList<String>> MutableLiveDataServices;
    private MutableLiveData<ArrayList<User>> MutableLiveDataProviders;
    private final ArrayList<String> Services= new ArrayList<>();
    private final ArrayList<String> Languages= new ArrayList<>();
    private final ArrayList<User> Providers= new ArrayList<>();
    // Initialize Firebase Auth
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    // Check if user is signed in (non-null) and update UI accordingly.
    private final FirebaseUser currentUser = mAuth.getCurrentUser();
    private final FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
    private GeoLocation center;
    private double radiusInM;
    private List<GeoQueryBounds> bounds;
    List<Task<DataSnapshot>> tasks = new ArrayList<>();


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
    public LiveData<User> getLoggedUserOnce() {
        if (MutableLiveDataLoggedUserOnce == null) {
            MutableLiveDataLoggedUserOnce = new MutableLiveData<>(new User());
            if(currentUser != null) {
                loadUserOnce( currentUser.getUid()); ;
            }
        }
        return MutableLiveDataLoggedUserOnce;
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
    //**************************************************************
    //PROVIDERS
     public LiveData<ArrayList<User>> getProviders(Double Lat,Double Lon) {
        if (MutableLiveDataProviders == null) {
            MutableLiveDataProviders = new MutableLiveData<>();
        }
        setHashbounds(Lat,Lon);
        return MutableLiveDataProviders;
    }
    //**************************************************************

    @SuppressLint("LongLogTag")
    private void loadUser(String uid) {
        Log.i(TAG1,  "loadUser --> String recibido como parametro: " + uid);
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("USERS");
        DBRef.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User UserSS = ds.getValue(User.class);
                    assert UserSS != null;
                    UserSpecRating USR = ds.child("userScore").child(UserSS.getSpecialization().replaceAll(",$", "")).getValue(UserSpecRating.class);
                    UserSS.setUserScore(USR);
                    MutableLiveDataLoggedUser.setValue(UserSS);
                }
            }
            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG1,  "ERROR CONECTAnDO A LA BASE");
            }
        });
    }
    @SuppressLint("LongLogTag")
    private void loadUserOnce(String uid) {
        Log.i(TAG1,  "loadUserOnce --> String recibido como parametro: " + uid);
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("USERS");
        DBRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User UserS = ds.getValue(User.class);
                    assert UserS != null;
                    UserSpecRating USR = ds.child("userScore").child(UserS.getSpecialization().replaceAll(",$", "")).getValue(UserSpecRating.class);
                    UserS.setUserScore(USR);
                    MutableLiveDataLoggedUserOnce.setValue(UserS);
                }
            }
            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG1,  "ERROR CONECTAnDO A LA BASE");
            }
        });
    }
    @SuppressLint("LongLogTag")
    private void loadLanguages() {
        Log.i(TAG1,  "loadLanguages --> Consultando idiomas");
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("LANGUAGES");
        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Languages.add(ds.getValue(String.class));
                    //User UserSS = ds.getValue(User.class);
                    Log.i(TAG1,"loadLanguages --> "+ds.getValue(String.class));
                }
                MutableLiveDataLanguages.setValue(Languages);
            }
            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG1,  "Operacion devolvio error " + error );
            }
        });

    }
    @SuppressLint("LongLogTag")
    private void loadServices() {
        Log.i(TAG1,  "loadServices --> Consultando Servicios");
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("SERVICES");
        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    Services.add(ds.getValue(String.class));
                    //User UserSS = ds.getValue(User.class);
                    Log.i(TAG1,"loadServices --> "+Services.get(Integer.parseInt(Objects.requireNonNull(ds.getKey()))));

                }
                MutableLiveDataServices.setValue(Services);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG1,  "Operacion devolvio error " + error );
            }
        });
    }
    private void setHashbounds(Double Lat,Double Lon){
        tasks.clear();
        DatabaseReference DBRef= mDatabase.getReference("USERS");
        center = new GeoLocation(Lat, Lon);
        radiusInM = 150 * 1000;
        bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);

        for (GeoQueryBounds b : bounds) {
            Query mQuery=DBRef.orderByChild("geohash").startAt(b.startHash).endAt(b.endHash);
            tasks.add(mQuery.get());
        }

        loadProviders();
    }
    @SuppressLint("LongLogTag")
    private void loadProviders() {
        Providers.clear();
        Tasks.whenAllComplete(tasks).addOnSuccessListener(new OnSuccessListener<List<Task<?>>>() {
            @Override
            public void onSuccess(List<Task<?>> tasks) {
                for (Task<?> task : tasks) {
                    if (task.isComplete()){
                        DataSnapshot snap = (DataSnapshot) task.getResult();
                        for (DataSnapshot doc : snap.getChildren()) {
                            // We have to filter out a few false positives due to GeoHash
                            // accuracy, but most will match
                            //Providers.add(doc.getValue(User.class));

                            GeoLocation docLocation = new GeoLocation(Objects.requireNonNull(doc.getValue(User.class)).getLatitude(), Objects.requireNonNull(doc.getValue(User.class)).getLongitude());
                            double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                            if (distanceInM <= radiusInM && Objects.equals(Objects.requireNonNull(doc.getValue(User.class)).getType(), "2") && !Objects.equals(Objects.requireNonNull(doc.getValue(User.class)).getUid(), SingletonUser.getInstance().getUid())) {
                                Providers.add(doc.getValue(User.class));
                                    Providers.get(Providers.size()-1).setUserScore(doc.child("userScore").child(Providers.get(Providers.size()-1).getSpecialization().replaceAll(",$", "")).getValue(UserSpecRating.class));


                                Log.i(TAG1,  "GEOHASHE: " +Providers.get(Providers.size()-1).getGeohash());
                            }
                        }
                    }
                }
                MutableLiveDataProviders.setValue(Providers);
            }
        });
    }


}
