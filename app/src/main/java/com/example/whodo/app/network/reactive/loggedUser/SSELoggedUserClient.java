package com.example.whodo.app.network.reactive.loggedUser;

import android.util.Log;
import com.example.whodo.app.ViewModelInterface;
import com.example.whodo.app.network.reactive.SSEClientInstanceProvider;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SSELoggedUserClient {
    private final OkHttpClient client;
    private static final String TAG = "LOGGER-SSE-LOGGED-USER-Client";
    private Call currentCall;
    public SSELoggedUserClient() {
        this.client = SSEClientInstanceProvider.getInstance();
    }
    public void startListening(ViewModelInterface pViewModel, String SSE_URL) {
        Log.d(TAG, "SSELoggedUserClient Iniciando conexión SSE con: " + SSE_URL);
        Request request = new Request.Builder().url(SSE_URL).build();
        currentCall = client.newCall(request);
        currentCall.enqueue(new SSELoggedUserCallback(pViewModel));
    }
    public void stopListening() {
        if (currentCall != null && !currentCall.isCanceled()) {
            currentCall.cancel();
            Log.d(TAG, "SSE connection cancelled");
        }
    }
}

