package com.example.whodo.app.network.reactive.workOrder;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.ViewModelInterface;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.network.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.BufferedSource;

public class SEEWorkOrderCallback implements Callback {
    private static final String TAG = "LOGGER-SSE-WO-Callback";
    private final ViewModelInterface mViewModelInterface;
    private final Gson gson = new Gson();
    private final Map<String, WorkOrder> lastProcessedWorkOrders = new HashMap<>();
    public SEEWorkOrderCallback(ViewModelInterface pViewModel) {
        this.mViewModelInterface = pViewModel;
    }
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.e(TAG, "Falla en la conexión SSE: " + e.getMessage());
    }
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Log.d(TAG, "Conexión SSE establecida correctamente.");

        if (response.body() == null) {
            Log.e(TAG, "Respuesta SSE sin body");
            return;
        }

        BufferedSource source = response.body().source();

        while (!source.exhausted()) {
            String line;
            try {
                line = source.readUtf8Line();
            } catch (IOException io) {
                Log.e(TAG, "Error leyendo línea SSE: " + io.getMessage());
                continue;
            }

            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            if (!line.startsWith("data:")) {
                Log.d(TAG, "Línea SSE ignorada (no es data): " + line);
                continue;
            }

            String jsonEvent = line.substring("data:".length()).trim();
            if (jsonEvent.isEmpty()) {
                continue;
            }

            Log.d(TAG, "Evento SSE válido recibido: " + jsonEvent);

            ApiResponse<List<WorkOrder>> parsedResponse;
            try {
                Type type = new TypeToken<ApiResponse<List<WorkOrder>>>() {}.getType();
                parsedResponse = gson.fromJson(jsonEvent, type);
            } catch (Exception parseEx) {
                Log.e(TAG, "Error parseando JSON SSE: " + parseEx.getMessage() + " contenido: " + jsonEvent);
                continue;
            }

            if (parsedResponse == null || parsedResponse.getData() == null) {
                Log.d(TAG, "Evento descartado por no tener data: " + jsonEvent);
                continue;
            }

            List<WorkOrder> workOrderList = parsedResponse.getData();
            boolean hasChanges = false;

            try {
                for (WorkOrder workOrder : workOrderList) {
                    WorkOrder previousWorkOrder = lastProcessedWorkOrders.get(workOrder.getOrderId());

                    if (previousWorkOrder == null || !previousWorkOrder.equals(workOrder)) {
                        hasChanges = true;
                        lastProcessedWorkOrders.put(workOrder.getOrderId(), workOrder);
                    }
                }
            } catch (Exception compareEx) {
                Log.e(TAG, "Error comparando WorkOrders: " + compareEx.getMessage());
                continue;
            }

            if (hasChanges) {
                try {
                    Log.d(TAG, "Evento procesado con cambios: " + workOrderList);
                    new Handler(Looper.getMainLooper()).post(() -> {
                        mViewModelInterface.setWorkOrder(workOrderList);
                    });
                } catch (Exception updateEx) {
                    Log.e(TAG, "Error actualizando LiveData: " + updateEx.getMessage());
                }
            } else {
                Log.d(TAG, "Evento descartado; no contiene cambios: " + jsonEvent);
            }
        }
    }
}

