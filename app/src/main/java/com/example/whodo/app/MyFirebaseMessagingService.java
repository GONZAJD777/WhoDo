package com.example.whodo.app;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.whodo.BuildConfig;
import com.example.whodo.R; // asegúrate de tener un ícono en res/drawable
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.user.UserFactory;
import com.example.whodo.app.network.ApiResponse;
import com.example.whodo.app.network.rest.RetrofitFactory;
import com.example.whodo.app.network.rest.api.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "LOGGER-FMC";
    private final String mBaseUrl = BuildConfig.BASE_URL_WORKORDER_SERVICE;
    private static final String CHANNEL_ID = "fcm_default_channel";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Nuevo token: " + token);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            sendTokenToServer(token, FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }

    // 👉 Método agregado: recepción de mensajes
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Mensaje recibido de: " + remoteMessage.getFrom());

        String title = null;
        String body = null;

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }

        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
            if (title == null) title = remoteMessage.getData().get("title");
            if (body == null) body = remoteMessage.getData().get("body");
        }

        if (title != null && body != null) {
            showNotification(title, body);
        }
        // 👉 NUEVO: Avisar a la App que debe actualizarse
        // Usamos un Intent con una acción personalizada
        android.content.Intent refreshIntent = new android.content.Intent("ACTION_REFRESH_WORK_ORDERS");
        sendBroadcast(refreshIntent);
    }

    // 👉 Método agregado: mostrar notificación
    private void showNotification(String title, String body) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.wrench_black)
                .setContentTitle(title)
                .setContentText(body) // texto corto (lo que se ve en la barra)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))   // texto completo al expandir
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }

    // 👉 Método agregado: crear canal de notificación (Android O+)
    private void createNotificationChannel() {
        CharSequence name = "FCM Channel";
        String description = "Canal para notificaciones FCM";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendTokenToServer(String token, String authId) {
        Context context = getApplicationContext();
        UserApi apiService = RetrofitFactory.createService(UserApi.class, mBaseUrl, context);

        Call<ApiResponse<User>> call = apiService.updateFcmToken(UserFactory.withAuthIdAndFcmToken(token, authId));
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
