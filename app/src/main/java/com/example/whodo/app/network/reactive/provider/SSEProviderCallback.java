package com.example.whodo.app.network.reactive.provider;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.user.User;
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

public class SSEProviderCallback implements Callback {
    private static final String TAG = "SSEProviderCallback";
    private final MutableLiveData<List<User>> liveData;
    private final Gson gson = new Gson();

    // Mapa para almacenar la última versión procesada de cada User (clave: user.id)
    private final Map<String, User> lastProcessedUsers = new HashMap<>();

    public SSEProviderCallback(MutableLiveData<List<User>> liveData) {
        this.liveData = liveData;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "Error en conexión SSE: " + e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) {
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
                        Type type = new TypeToken<ApiResponse<List<User>>>() {}.getType();
                        ApiResponse<List<User>> parsedResponse = gson.fromJson(jsonEvent, type);

                        // Si no contiene data se descarta el evento
                        if (parsedResponse == null || parsedResponse.getData() == null) {
                            Log.d(TAG, "Evento descartado por no tener data: " + jsonEvent);
                            continue;
                        }

                        List<User> userList = parsedResponse.getData();
                        boolean hasChanges = false;

                        // Recorremos cada User y comparamos con la última versión procesada
                        for (User user : userList) {
                            User previousUser = lastProcessedUsers.get(user.getId());

                            // Si es un usuario nuevo o ha cambiado, marcamos cambios
                            if (previousUser == null || !previousUser.equals(user)) {
                                hasChanges = true;
                                lastProcessedUsers.put(user.getId(), user);
                            }
                        }

                        // Sólo notificamos si al menos uno de los usuarios presenta un cambio
                        if (hasChanges) {
                            Log.d(TAG, "Evento procesado con cambios: " + userList);
                            liveData.postValue(userList);
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