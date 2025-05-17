package com.example.whodo.app.domain.user.dao;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.whodo.BuildConfig;
import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.user.UserDTO;
import com.example.whodo.app.network.reactive.SSELoggedUserClient;
import com.example.whodo.app.network.rest.RetrofitClient;
import com.example.whodo.app.network.rest.api.UserApi;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class MongoDBUserDAO implements UserDao<UserDTO>{

    private final String TAG = "MONGODB-USER-DAO";
    private final UserApi mUserApi;
    private final String mBaseUrl= BuildConfig.BASE_URL;
    //IMAGES STORAGE REF
    private final FirebaseStorage mStorageInstance = FirebaseStorage.getInstance();
    private final StorageReference mStorageReference = mStorageInstance.getReference();
    private final StorageReference mImageStorageRef = mStorageReference.child("WHODO-IMAGES");




    public MongoDBUserDAO() {
        Log.d(TAG, "mBaseUrl -->" + mBaseUrl);

        RetrofitClient retrofitClient = new RetrofitClient(mBaseUrl);
        this.mUserApi = retrofitClient.createService(UserApi.class);
    }



    @Override
    public LiveData<UserDTO> findUser(UserDTO userDTO) {

        MutableLiveData<UserDTO> mUser = new MutableLiveData<>();
        SSELoggedUserClient SSELoggedUserClient = new SSELoggedUserClient(mBaseUrl + "users/stream/userByAuth/" + userDTO.getAuthId());
        SSELoggedUserClient.startListening(mUser);
        Log.d(TAG, "sseUserClient -->" + mBaseUrl + "users/stream/userByAuth/" + userDTO.getAuthId());

        return mUser;
    }

    @Override
    public void findCustomer(UserDTO userDTO, Callback<UserDTO> callback) {

    }

    @Override
    public void create(UserDTO userDTO, Callback<UserDTO> callback) {
        Call<UserDTO> call = mUserApi.createUser(userDTO);
        Log.d(TAG, "Endpoint Requested -->" + mUserApi.createUser(userDTO).request().url());

        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserDTO> call, @NonNull Response<UserDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserDTO> call, @NonNull Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }

    @Override
    public void update(UserDTO userDTO) {
        Map<String, Object> updates = buildUpdate(userDTO);
        if (!updates.isEmpty()){
            Call<UserDTO> call = mUserApi.updateUser(userDTO);
            Log.d(TAG, "Endpoint Requested -->" + mUserApi.updateUser(userDTO).request().url());
            Log.d(TAG, "Update -->" + updates);
            call.enqueue(new retrofit2.Callback<>() {
                @Override
                public void onResponse(@NonNull Call<UserDTO> call, @NonNull Response<UserDTO> response) {

                }
                @Override
                public void onFailure(@NonNull Call<UserDTO> call, @NonNull Throwable t) {
                    Log.d(TAG, "mUserApi.updateUser() - OnFailure --> " + new Exception(t));
                }
            });
        }else {
            Log.d(TAG, "Update Canceled --> Nothing to Update" );
        }
    }

    @Override
    public void findProviders(UserDTO userDTO, Callback<List<UserDTO>> callback) {

    }
    @Override
    public void findLanguages(Callback<List<String>> callback) {

    }
    @Override
    public void findServices(Callback<List<String>> callback) {

    }

    private Map<String, Object> buildUpdate(UserDTO pUserDTO){

        if (pUserDTO == null) {
            Log.e(TAG, "buildUpdate: UserDTO es null");
            return new HashMap<>();
        }

        // Agregar chequeos para props compuestas:
        if (pUserDTO.getLocation() == null) {
            Log.e(TAG, "buildUpdate: Location es null");
        }
        if (pUserDTO.getPhone() == null) {
            Log.e(TAG, "buildUpdate: Phone es null");
        }


        Map<String, Object> updates = new HashMap<>();

        if (pUserDTO.getAddress() != null) {
            updates.put("address", pUserDTO.getAddress());
        }
        if (pUserDTO.getBirthday() != null) {
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
        if (pUserDTO.getLanguages() != null ) {
            updates.put("languages", pUserDTO.getLanguages());
        }
        if (pUserDTO.getLocation() != null) {
            Double latitude = pUserDTO.getLocation().getLatitude();
            Double longitude = pUserDTO.getLocation().getLongitude();
            if (latitude != null && longitude != null ) {
                String newGeoHash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(latitude, longitude));
                updates.put("latitude", latitude);
                updates.put("longitude", longitude);
                updates.put("geohash", newGeoHash);
            }
        }

        if (pUserDTO.getPassword() != null) {
            updates.put("password", pUserDTO.getPassword());
        }
        if (pUserDTO.getPhone() != null) {
            if (pUserDTO.getPhone().getNumber() != null) {
                updates.put("number", pUserDTO.getPhone().getNumber());
            }
            if (pUserDTO.getPhone().getCcn() != null) {
                updates.put("ccn", pUserDTO.getPhone().getCcn());
            }
        }

        if (pUserDTO.getType() != null) {
            updates.put("type", pUserDTO.getType());
        }
        if (pUserDTO.getSpecialization() != null) {}
        if (pUserDTO.getProfilePicture() != null) {
            OnSuccessListener<Uri> downloadUrlListener = uri -> {
                String profilePictureUri = uri.toString();
                updates.put("profilePicture", profilePictureUri);
            };
            uploadProfileImage(pUserDTO, downloadUrlListener);
        }
        return updates;
    }
    private void uploadProfileImage(UserDTO pUserDTO, OnSuccessListener<Uri> downloadUrlListener){
        StorageReference mImagesRef = mImageStorageRef.child("PROFILE-PICTURE/" + pUserDTO.getAuthId());
        Uri mUri = Uri.parse(pUserDTO.getProfilePicture());
        UploadTask mUploadTask = mImagesRef.putFile(mUri);
        mUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i(TAG, "uploadProfileImage() operation --> Image Upload to Storage Failed: " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getImageUri(mImagesRef,downloadUrlListener);
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i(TAG, "uploadProfileImage()  --> Image Upload to Storage Success" );
            }
        });
    }
    private void getImageUri(StorageReference pImagesRef,OnSuccessListener<Uri> downloadUrlListener){
        pImagesRef.getDownloadUrl().addOnSuccessListener(downloadUrlListener).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i(TAG, "getImageUri() operation --> image uri couldn't be retrived: " + exception );
            }
        });
    }
}
