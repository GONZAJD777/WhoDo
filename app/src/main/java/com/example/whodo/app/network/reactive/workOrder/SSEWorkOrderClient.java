package com.example.whodo.app.network.reactive.workOrder;

import android.util.Log;
import com.example.whodo.app.ViewModelInterface;
import com.example.whodo.app.network.reactive.SSEClientInstanceProvider;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SSEWorkOrderClient {
    private static final String TAG = "LOGGER-SSE-WO-Client";
    private final OkHttpClient client;
    private Call currentCall;
    public SSEWorkOrderClient() {
        this.client = SSEClientInstanceProvider.getInstance();
    }
    public void startListening(ViewModelInterface pViewModel, String SSE_URL) {
        Log.d(TAG, "SSEWorkOrderClient Iniciando conexión SSE con: " + SSE_URL);
        Request request = new Request.Builder().url(SSE_URL).build();
        currentCall = client.newCall(request);
        currentCall.enqueue(new SEEWorkOrderCallback(pViewModel));
        client.newCall(request).enqueue(new SEEWorkOrderCallback(pViewModel));
    }

    public void stopListening() {
        if (currentCall != null && !currentCall.isCanceled()) {
            currentCall.cancel();
            Log.d(TAG, "SSE connection cancelled");
        }
    }
}
