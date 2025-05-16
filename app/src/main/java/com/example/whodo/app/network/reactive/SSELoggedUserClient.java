package com.example.whodo.app.network.reactive;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.whodo.app.domain.user.UserDTO;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SSELoggedUserClient {
    private final OkHttpClient client;
    private final String SSE_URL;
    private static final String TAG = "SSEUserClient";

    public SSELoggedUserClient(String sseUrl) {
        // 0 significa sin límite de tiempo de lectura
        client = new OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build();

        SSE_URL = sseUrl;
    }

    public void startListening(MutableLiveData<UserDTO> mUser) {
        Log.d(TAG, "Iniciando conexión SSE con: " + SSE_URL);

        Request request = new Request.Builder().url(SSE_URL).build();
        client.newCall(request).enqueue(new SSELoggedUserCallback(mUser));
    }
}

