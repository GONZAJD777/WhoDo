package com.example.whodo.domain.user.dao;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.aplication.Callback;
import com.example.whodo.domain.user.UserDTO;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseUserDAO implements UserDao<UserDTO> {
    private final String TAG1="FirebaseUserDao";
    //DATABASE REF
    private final FirebaseDatabase mDatabaseInstance = FirebaseDatabase.getInstance();
    private final DatabaseReference mDatabaseReference = mDatabaseInstance.getReference();
    private final DatabaseReference UserDBRef = mDatabaseReference.child("USERS");
    private final DatabaseReference LanguagesDBRef = mDatabaseReference.child("LANGUAGES");
    private final DatabaseReference ServicesDBRef = mDatabaseReference.child("SERVICES");
    //IMAGES STORAGE REF
    private final FirebaseStorage mStorageInstance = FirebaseStorage.getInstance();
    private final StorageReference mStorageReference = mStorageInstance.getReference();
    private final StorageReference mImageStorageRef = mStorageReference.child("WHODO-IMAGES");

    public FirebaseUserDAO(){ }
    @Override
    public LiveData<UserDTO> findUser(UserDTO pUserDTO) {
        MutableLiveData<UserDTO> mUser = new MutableLiveData<>();

        Log.i(TAG1,  "findUSer --> String recibido como parametro: " + pUserDTO);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        UserDTO auxUser=ds.getValue(UserDTO.class);
                        mUser.setValue(auxUser);
                        assert auxUser != null;
                        Log.i(TAG1,  "findUser:onDataChange -->" + auxUser.getEmail());
                    } catch (Exception e) {
                        Log.i(TAG1,  "Exception Mapping Snapshot to UserDTO: " + e);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG1, "findUser:onCancelled -->" + databaseError.toException());
            }
        };
        UserDBRef.orderByChild("uid").equalTo(pUserDTO.getUid()).addValueEventListener(postListener);
        return mUser;
    }
    @Override
    public void create(UserDTO pUserDTO,Callback<UserDTO> callback) {
        UserDBRef.child(pUserDTO.getUid()).setValue(pUserDTO).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // La operación se completó con éxito
                System.out.println("Operación exitosa");
                callback.onSuccess(pUserDTO);
            } else {
                // Hubo un error al establecer el valor
                System.out.println("Error al establecer el valor: " + task.getException());
                callback.onError(task.getException());
            }
        });
    }
    @Override
    public void findCustomer(UserDTO pUserDTO,Callback<UserDTO> callback) {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        UserDTO auxUser=ds.getValue(UserDTO.class);
                        assert auxUser != null;
                        Log.i(TAG1,  "findCustomer:onDataChange -->" + auxUser.getEmail());
                        callback.onSuccess(auxUser);
                    } catch (Exception e) {
                        Log.i(TAG1,  "Exception Mapping Snapshot to UserDTO: " + e);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG1,  "findCustomer:onCancelled -->" + error);
            }
        };

        UserDBRef.orderByChild("uid").equalTo(pUserDTO.getUid()).addListenerForSingleValueEvent(postListener);

    }

    @Override
    public void update(UserDTO pUserDTO) {
        Map<String, Object> updates = new HashMap<>();

        if (pUserDTO.getAddress() != null) {
            updates.put("address", pUserDTO.getAddress());
        }
        if (pUserDTO.getBirthday() != 0) {
            updates.put("birthday", pUserDTO.getBirthday());
        }
        if (pUserDTO.getDescription() != null) {
            updates.put("description", pUserDTO.getDescription());
        }
        if (pUserDTO.getEmail() != null) {
            updates.put("email", pUserDTO.getEmail());
        }
        if (pUserDTO.getName() != null) {
            updates.put("name", pUserDTO.getName());
        }
        if (pUserDTO.getLanguages() != null) {
            updates.put("languages", pUserDTO.getLanguages());
        }
        if (pUserDTO.getLatitude() != 0.0 || pUserDTO.getLongitude() != 0.0 ) {
            String newGeoHash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(pUserDTO.getLatitude(),pUserDTO.getLongitude()));
            updates.put("latitude", pUserDTO.getLatitude());
            updates.put("longitude", pUserDTO.getLongitude());
            updates.put("geohash",newGeoHash );
        }
        if (pUserDTO.getIsValidated() != 0) {
            updates.put("isValidated", pUserDTO.getIsValidated());
        }
        if (pUserDTO.getPassword() != null) {
            updates.put("password", pUserDTO.getPassword());
        }
        if (pUserDTO.getPhone() != null) {
            updates.put("phone", pUserDTO.getPhone());
        }
        if (pUserDTO.getPhone_ccn() != null) {
            updates.put("phone_ccn", pUserDTO.getPhone_ccn());
        }
        if (pUserDTO.getType() != null) {
            updates.put("type", pUserDTO.getType());
        }
        if (pUserDTO.getSpecialization() != null) {
            updates.put("specialization", pUserDTO.getSpecialization());
        }
        if (pUserDTO.getProfilePicture() != null) {

            OnSuccessListener<Uri> downloadUrlListener = uri -> {
                String profilePictureUri = uri.toString();
                updates.put("profilePicture", profilePictureUri);

                UserDBRef.child(pUserDTO.getUid()).updateChildren(updates).addOnSuccessListener(aVoid -> {
                            Log.i(TAG1, " update() operation --> User Update Success");
                        })
                        .addOnFailureListener(e -> {
                            Log.i(TAG1, " update() operation --> User Update Failed: " + e);
                        });
            };
            uploadProfileImage(pUserDTO, downloadUrlListener);
        }
        else {
            if(!updates.isEmpty()){
                UserDBRef.child(pUserDTO.getUid()).updateChildren(updates).addOnSuccessListener(aVoid -> {
                            Log.i(TAG1, " update() operation --> User Update Success");
                        })
                        .addOnFailureListener(e -> {
                            Log.i(TAG1, " update() operation --> User Update Failed: " + e);
                        });
            }
        }
    }
    @Override
    public void findProviders(UserDTO pUserDTO, Callback<List<UserDTO>> callback) {
        GeoLocation center = new GeoLocation(pUserDTO.getLatitude(), pUserDTO.getLongitude());
        double radiusInM = 100 * 1000;
        List<Task<DataSnapshot>> tasks = getHashBounds(pUserDTO.getLatitude(), pUserDTO.getLongitude());

        Tasks.whenAllComplete(tasks).addOnSuccessListener(new OnSuccessListener<List<Task<?>>>() {
            @Override
            public void onSuccess(List<Task<?>> tasks) {
                List<UserDTO> users = new ArrayList<>();
                for (Task<?> task : tasks) {
                    if (task.isComplete()) {
                        DataSnapshot snap = (DataSnapshot) task.getResult();
                        for (DataSnapshot doc : snap.getChildren()) {
                            UserDTO user = doc.getValue(UserDTO.class);
                            if (user != null) {
                                GeoLocation docLocation = new GeoLocation(user.getLatitude(), user.getLongitude());
                                double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                                if (distanceInM <= radiusInM
                                        && Objects.equals(user.getType(), "2")
                                        || (Objects.equals(user.getUid(), pUserDTO.getUid()))
                                ) {
                                    users.add(user);
                                }
                            }
                            Log.i(TAG1,  "findProviders --> Se finalizo la tarea");
                        }
                    }
                }
                callback.onSuccess(users);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Manejar errores aquí (si es necesario)
                Log.i(TAG1,  "findProviders --> Exception Task could not be completed: " + e);
                callback.onError(e);
            }
        });
    }
    @Override
    public void findLanguages(Callback<List<String>> callback) {
        Task<DataSnapshot> mQuery = LanguagesDBRef.get();
        List<String> mLanguages = new ArrayList<>();
        mQuery.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mLanguages.add(ds.getValue(String.class));
                    }
                    callback.onSuccess(mLanguages);
                } else {
                    callback.onError(task.getException());
                }
            }
        });
    }
    @Override
    public void findServices(Callback<List<String>> callback) {
        Task<DataSnapshot> mQuery = ServicesDBRef.get();
        List<String> mServices = new ArrayList<>();
        mQuery.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mServices.add(ds.getValue(String.class));
                    }
                    callback.onSuccess(mServices);
                } else {
                    callback.onError(task.getException());
                }
            }
        });

    }
    private void uploadProfileImage(UserDTO pUserDTO,OnSuccessListener<Uri> downloadUrlListener){
        StorageReference mImagesRef = mImageStorageRef.child("PROFILE-PICTURE/" + pUserDTO.getUid());
        Uri mUri = Uri.parse(pUserDTO.getProfilePicture());
        UploadTask mUploadTask = mImagesRef.putFile(mUri);
        mUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i(TAG1, "uploadProfileImage() operation --> Image Upload to Storage Failed: " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getImageUri(mImagesRef,downloadUrlListener);
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i(TAG1, "uploadProfileImage()  --> Image Upload to Storage Success" );
            }
        });
    }
    private void getImageUri(StorageReference pImagesRef,OnSuccessListener<Uri> downloadUrlListener){
        pImagesRef.getDownloadUrl().addOnSuccessListener(downloadUrlListener).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i(TAG1, "getImageUri() operation --> image uri couldn't be retrived: " + exception );
            }
        });
    }
    private List<Task<DataSnapshot>> getHashBounds(Double Lat, Double Lon) {
        List<Task<DataSnapshot>> tasks = new ArrayList<>();
        GeoLocation center = new GeoLocation(Lat, Lon);
        double radiusInM = 100 * 1000;
        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);
        Query mQuery = null;
        for (GeoQueryBounds b : bounds) {
            mQuery = UserDBRef.orderByChild("geohash").startAt(b.startHash).endAt(b.endHash);
            tasks.add(mQuery.get());
        }
        return tasks;
    }
}
