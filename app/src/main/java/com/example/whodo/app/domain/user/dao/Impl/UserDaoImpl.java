package com.example.whodo.app.domain.user.dao.Impl;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.whodo.BuildConfig;
import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.user.UserApiRestRequestDTO;
import com.example.whodo.app.domain.user.UserMapper;
import com.example.whodo.app.domain.user.dao.UserDao;
import com.example.whodo.app.network.reactive.loggedUser.SSELoggedUserClient;
import com.example.whodo.app.network.reactive.provider.SSEProviderClient;
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

public class UserDaoImpl implements UserDao<User> {

    private final String TAG = "MONGODB-USER-DAO";
    private final UserApi mUserApi;
    private final String mBaseUrl= BuildConfig.BASE_URL;
    //IMAGES STORAGE REF
    private final FirebaseStorage mStorageInstance = FirebaseStorage.getInstance();
    private final StorageReference mStorageReference = mStorageInstance.getReference();
    private final StorageReference mImageStorageRef = mStorageReference.child("WHODO-IMAGES");




    public UserDaoImpl(Context pContext) {
        Log.d(TAG, "mBaseUrl -->" + mBaseUrl);

        RetrofitClient retrofitClient = new RetrofitClient(mBaseUrl,pContext.getApplicationContext());
        this.mUserApi = retrofitClient.createService(UserApi.class);
    }

    @Override
    public LiveData<User> findUser(User pUser) {

        MutableLiveData<User> mUser = new MutableLiveData<>();
        SSELoggedUserClient SSELoggedUserClient = new SSELoggedUserClient(mBaseUrl + "users/stream/userByAuth/" + pUser.getAuthId());
        SSELoggedUserClient.startListening(mUser);
        Log.d(TAG, "sseUserClient -->" + mBaseUrl + "users/stream/userByAuth/" + pUser.getAuthId());

        return mUser;
    }

    @Override
    public void findCustomer(User pUser, Callback<User> callback) {

    }

    @Override
    public void create(User pUser, Callback<User> callback) {
        UserApiRestRequestDTO mUserApiRestRequestDTO = UserMapper.toApiRestRequestDTO(pUser);
        Call<User> call = mUserApi.createUser(mUserApiRestRequestDTO);
        Log.d(TAG, "Endpoint Requested -->" + mUserApi.createUser(mUserApiRestRequestDTO).request().url());
        Log.d(TAG, "create() -->" + mUserApiRestRequestDTO.toString() );

        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }

    @Override
    public void update(User pUser) {

        if (!validateUpdate(pUser).isEmpty()){
            if (pUser.getProfilePicture() != null) {
                OnSuccessListener<Uri> downloadUrlListener = uri -> {
                    String profilePictureUri = uri.toString();
                    pUser.setProfilePicture(profilePictureUri);

                    UserApiRestRequestDTO mUserApiRestRequestDTO = UserMapper.toApiRestRequestDTO(pUser);
                    Call<User> call = mUserApi.updateUser(mUserApiRestRequestDTO);
                    Log.d(TAG, "Endpoint Requested -->" + mUserApi.updateUser(mUserApiRestRequestDTO).request().url());
                    Log.d(TAG, "Update -->" + pUser);
                    call.enqueue(new retrofit2.Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) { }
                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Log.d(TAG, "mUserApi.updateUser() - OnFailure --> " + new Exception(t));
                        }
                    });

                };
                uploadProfileImage(pUser, downloadUrlListener);
            } else {
                UserApiRestRequestDTO mUserApiRestRequestDTO = UserMapper.toApiRestRequestDTO(pUser);
                Call<User> call = mUserApi.updateUser(mUserApiRestRequestDTO);
                Log.d(TAG, "Endpoint Requested -->" + mUserApi.updateUser(mUserApiRestRequestDTO).request().url());
                Log.d(TAG, "Update -->" + pUser);
                call.enqueue(new retrofit2.Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                    }
                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Log.d(TAG, "mUserApi.updateUser() - OnFailure --> " + new Exception(t));
                    }
                });
            }
        } else {
            Log.d(TAG, "Update Canceled --> Nothing to Update" );
        }
    }

    @Override
    public LiveData<List<User>> findProviders(User pUser, Double distance ) {
        MutableLiveData<List<User>> mUser = new MutableLiveData<>();
        LatLonBounds mUserBounds= getBoundsForDistance(pUser.getLocation().getLatitude(), pUser.getLocation().getLongitude(), distance);
        double Lat1=mUserBounds.latMin;
        double Lat2=mUserBounds.latMax;
        double Lon1=mUserBounds.lonMin;
        double Lon2=mUserBounds.lonMax;
        SSEProviderClient SSEProviderClient = new SSEProviderClient(mBaseUrl + "users/stream/getAllUsersInBound?UserType=2&lat1="+ Lat1 +"&lon1="+ Lon1 +"&lat2="+ Lat2 +"&lon2="+Lon2);
        SSEProviderClient.startListening(mUser);
        Log.d(TAG, "sseProviderClient -->" + mBaseUrl + "users/stream/getAllUsersInBound?UserType=2&lat1="+ Lat1 +"&lon1="+ Lon1 +"&lat2="+ Lat2 +"&lon2="+Lon2);

        return mUser;
    }

    public static LatLonBounds getBoundsForDistance(double lat, double lon, double distanceKm) {
        // Aproximación: 1 grado de latitud ≈ 111 km
        double LATITUDE_KM = 111.0;

        // Factor para convertir la distancia en grados de longitud según la latitud
        double longitudeKm = LATITUDE_KM * Math.cos(Math.toRadians(lat));

        // Cálculo de los límites del rectángulo
        double latMin = lat - (distanceKm / LATITUDE_KM);
        double latMax = lat + (distanceKm / LATITUDE_KM);
        double lonMin = lon - (distanceKm / longitudeKm);
        double lonMax = lon + (distanceKm / longitudeKm);

        return new LatLonBounds(latMin, lonMin, latMax, lonMax);
    }
    public record LatLonBounds(double latMin, double lonMin, double latMax, double lonMax) {
        @NonNull
        @Override
            public String toString() {
                return "LatLonBounds{" +
                        "latMin=" + latMin +
                        ", lonMin=" + lonMin +
                        ", latMax=" + latMax +
                        ", lonMax=" + lonMax +
                        '}';
            }
        }


    private Map<String, Object> validateUpdate(User pUser){
        if (pUser == null) {
            Log.e(TAG, "buildUpdate: UserDTO es null");
            return new HashMap<>();
        }
        // Agregar chequeos para props compuestas:
        if (pUser.getLocation() == null) {
            Log.e(TAG, "buildUpdate: Location es null");
        }
        if (pUser.getPhone() == null) {
            Log.e(TAG, "buildUpdate: Phone es null");
        }

        Map<String, Object> updates = new HashMap<>();

        if (pUser.getAddress() != null) {
            updates.put("address", pUser.getAddress());
        }
        if (pUser.getBirthday() != null) {
            updates.put("birthday", pUser.getBirthday());
        }
        if (pUser.getDescription() != null) {
            updates.put("description", pUser.getDescription());
        }
        if (pUser.getEmail() != null) {
            updates.put("email", pUser.getEmail());
        }
        if (pUser.getName() != null) {
            updates.put("name", pUser.getName());
        }
        if (pUser.getLanguages() != null ) {
            updates.put("languages", pUser.getLanguages());
        }
        if (pUser.getLocation() != null) {
            Double latitude = pUser.getLocation().getLatitude();
            Double longitude = pUser.getLocation().getLongitude();
            if (latitude != null && longitude != null ) {
                String newGeoHash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(latitude, longitude));
                updates.put("latitude", latitude);
                updates.put("longitude", longitude);
                updates.put("geohash", newGeoHash);
            }
        }
        if (pUser.getPassword() != null) {
            updates.put("password", pUser.getPassword());
        }
        if (pUser.getPhone() != null) {
            if (pUser.getPhone().getNumber() != null) {
                updates.put("number", pUser.getPhone().getNumber());
            }
            if (pUser.getPhone().getCcn() != null) {
                updates.put("ccn", pUser.getPhone().getCcn());
            }
        }
        if (pUser.getType() != null) {
            updates.put("type", pUser.getType());
        }
        if (pUser.getSpecialization() != null) {
            updates.put("specialization", pUser.getSpecialization());
        }

        if (pUser.getProfilePicture() != null) {
            updates.put("profilePicture", pUser.getProfilePicture());
        }
        return updates;
    }
    private void uploadProfileImage(User pUser, OnSuccessListener<Uri> downloadUrlListener){
        StorageReference mImagesRef = mImageStorageRef.child("PROFILE-PICTURE/" + pUser.getId());
        Uri mUri = Uri.parse(pUser.getProfilePicture());
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
