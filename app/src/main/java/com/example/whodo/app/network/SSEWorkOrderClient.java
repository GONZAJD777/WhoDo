package com.example.whodo.app.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class SSEWorkOrderClient {
    private final OkHttpClient client = new OkHttpClient();
    private final String SSE_URL ;
    private final Gson gson = new Gson(); // Instancia para convertir JSON a objeto
    private static final String TAG = "SSEWorkOrderClient";

    public SSEWorkOrderClient(String sseUrl) {
        SSE_URL = sseUrl;
    }

    public void startListening(MutableLiveData<List<WorkOrder>> mWorkOrders) {
        Log.d(TAG, "Iniciando conexión SSE con: " + SSE_URL);
        Request request = new Request.Builder().url(SSE_URL).build();
        client.newCall(request).enqueue(new SSECallback<>(mWorkOrders)); // Ahora SSECallback detecta automáticamente si es lista o un solo objeto
    }
}

