package com.example.whodo.app.network.reactive.workOrder;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.domain.workOrder.WorkOrderDTO;
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

    private static final String TAG = "SSEWorkOrderCallback";
    private final MutableLiveData<List<WorkOrder>> liveData; // LiveData del tipo User o List<User>
    private final Gson gson = new Gson();

    // Mapa para almacenar la última versión procesada de cada User (clave: user.id)
    private final Map<String, WorkOrder> lastProcessedWorkOrders = new HashMap<>();

    public SEEWorkOrderCallback(MutableLiveData<List<WorkOrder>> liveData) {
        this.liveData = liveData;
    }
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {

    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Log.d(TAG, "Conexión SSE establecida correctamente.");
        try {
            if (response.body() != null) {
                BufferedSource source = response.body().source();
                while (!source.exhausted()) {
                    String line = source.readUtf8Line();

                    // Descarta líneas nulas o vacías
                    if (line == null || line.trim().isEmpty()) {
                        continue;
                    }

                    // Procesa solo líneas que comiencen con "data:"
                    if (!line.startsWith("data:")) {
                        continue; // Podrían llegar comentarios o keep-alives
                    }

                    // Remueve el prefijo "data:" y recorta espacios
                    String jsonEvent = line.substring("data:".length()).trim();

                    // Si el contenido es vacío, lo descartamos
                    if (jsonEvent.isEmpty()) {
                        continue;
                    }

                    Log.d(TAG, "Evento SSE válido recibido: " + jsonEvent);

                    try {
                        Type type = new TypeToken<ApiResponse<List<WorkOrder>>>() {}.getType();
                        ApiResponse<List<WorkOrder>> parsedResponse = gson.fromJson(jsonEvent, type);

                        // Si no contiene data se descarta el evento
                        if (parsedResponse == null || parsedResponse.getData() == null) {
                            Log.d(TAG, "Evento descartado por no tener data: " + jsonEvent);
                            continue;
                        }

                        List<WorkOrder> workOrderList = parsedResponse.getData();
                        boolean hasChanges = false;

                        // Recorremos cada User y comparamos con la última versión procesada
                        for (WorkOrder workOrder : workOrderList) {
                            WorkOrder previousWorkOrder = lastProcessedWorkOrders.get(workOrder.getOrderId());

                            // Si es un usuario nuevo o ha cambiado, marcamos cambios
                            if (previousWorkOrder == null || !previousWorkOrder.equals(workOrder)) {
                                hasChanges = true;
                                lastProcessedWorkOrders.put(workOrder.getOrderId(), workOrder);
                            }
                        }

                        // Sólo notificamos si al menos uno de los usuarios presenta un cambio
                        if (hasChanges) {
                            Log.d(TAG, "Evento procesado con cambios: " + workOrderList);
                            liveData.postValue(workOrderList);
                        } else {
                            Log.d(TAG, "Evento descartado; no contiene cambios: " + jsonEvent);
                        }

                    } catch (Exception e) {
                        Log.e(TAG, "Error al parsear el evento: " + jsonEvent + " Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al procesar eventos SSE: " + e.getMessage());
        }
    }
}
