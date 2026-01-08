package com.example.whodo.app;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.whodo.BuildConfig;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.user.UserFactory;
import com.example.whodo.app.network.ApiResponse;
import com.example.whodo.app.network.rest.RetrofitFactory;
import com.example.whodo.app.network.rest.api.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "LOGGER-FMC";
    private final String mBaseUrl= BuildConfig.BASE_URL_WORKORDER_SERVICE;
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Nuevo token: " + token);

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            sendTokenToServer(token,FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }

    private void sendTokenToServer(String token,String authId) {
        Context context = getApplicationContext();
        UserApi apiService = RetrofitFactory.createService(UserApi.class, mBaseUrl, context);

        Call<ApiResponse<User>> call = apiService.updateFcmToken(UserFactory.withAuthIdAndFcmToken(token,authId));
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<User>> call, @NonNull Response<ApiResponse<User>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Token actualizado en backend");
                } else {
                    Log.e(TAG, "Error al actualizar token: " + response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ApiResponse<User>> call, @NonNull Throwable t) {
                Log.e(TAG, "Fallo al enviar token", t);
            }
        });
    }
}
