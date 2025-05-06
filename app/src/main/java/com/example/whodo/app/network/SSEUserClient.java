package com.example.whodo.app.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.user.User;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SSEUserClient {
    private final OkHttpClient client = new OkHttpClient();
    private final String SSE_URL;
    private static final String TAG = "SSEUserClient";

    public SSEUserClient(String sseUrl) {
        SSE_URL = sseUrl;
    }

    public void startListening(MutableLiveData<User> mUser) {
        Log.d(TAG, "Iniciando conexión SSE con: " + SSE_URL);

        Request request = new Request.Builder().url(SSE_URL).build();
        client.newCall(request).enqueue(new SSECallback(mUser));
    }
}

