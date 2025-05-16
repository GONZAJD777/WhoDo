package com.example.whodo.app.network.reactive;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.user.UserDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.BufferedSource;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SSELoggedUserCallback implements Callback {
    private static final String TAG = "SSELoggedUserCallback";
    private final MutableLiveData<UserDTO> liveData; // LiveData del tipo User o List<User>
    private final Gson gson = new Gson();
    // Se crea el tipo para deserializar ApiResponse<T> o ApiResponse<List<T>>

    public SSELoggedUserCallback(MutableLiveData<UserDTO> liveData) {
        this.liveData = liveData;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "Error en conexión SSE: " + e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.d(TAG, "Conexión SSE establecida correctamente.");
        // Conjunto para registrar los eventos ya procesados
        Set<String> processedEvents = new HashSet<>();
        try {
            if (response.body() != null) {
                BufferedSource source = response.body().source();
                while (!source.exhausted()) {
                    String line = source.readUtf8Line();

                    // Descarta líneas nulas o vacías
                    if (line == null || line.trim().isEmpty()) {
                        continue;
                    }

                    // Solo procesamos líneas que comiencen con "data:"
                    if (!line.startsWith("data:")) {
                        continue; // Podrían llegar comentarios o keep-alives
                    }

                    // Removemos el prefijo "data:" y recortamos espacios
                    String jsonEvent = line.substring("data:".length()).trim();

                    // Si el contenido es vacío, lo descartamos
                    if (jsonEvent.isEmpty()) {
                        continue;
                    }

                    // Si este evento ya se procesó (mismo contenido), lo saltamos
                    if (processedEvents.contains(jsonEvent)) {
                        Log.d(TAG, "Evento duplicado descartado: " + jsonEvent);
                        continue;
                    }

                    // Registramos el evento para no procesarlo nuevamente
                    processedEvents.add(jsonEvent);
                    Log.d(TAG, "Evento SSE válido recibido: " + jsonEvent);

                    // Intentamos deserializar el JSON
                    try {
                        Type type = new TypeToken<ApiResponse<UserDTO>>() {}.getType();
                        ApiResponse<UserDTO> parsedResponse = gson.fromJson(jsonEvent, type);

                        // Si no contiene data, lo descartamos
                        if (parsedResponse == null || parsedResponse.getData() == null) {
                            Log.d(TAG, "Evento descartado por no tener data: " + jsonEvent);
                            continue;
                        }

                        UserDTO userDTO = parsedResponse.getData();
                        Log.d(TAG, "Evento procesado: " + userDTO);
                        liveData.postValue(userDTO);

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