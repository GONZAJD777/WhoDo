package com.example.whodo.app.network;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.BufferedSource;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class SSECallback<T> implements Callback {
    private static final String TAG = "SSECallback";
    private final MutableLiveData<?> liveData; // Acepta cualquier tipo de `LiveData`
    private final Gson gson = new Gson();
    private final Type dataType; // Tipo real del contenido dentro de LiveData

    public SSECallback(MutableLiveData<?> liveData) {
        this.liveData = liveData;
        this.dataType = getLiveDataType(liveData); // Detecta el tipo real del contenido
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
                    String jsonEvent = source.readUtf8Line(); // Recibe JSON como string
                    Log.d(TAG, "Evento SSE recibido en JSON: " + jsonEvent);

                    Object parsedEvent = gson.fromJson(jsonEvent, dataType); // Conversión dinámica según el tipo de `LiveData`

                    postToLiveData(liveData, parsedEvent); // Actualiza `LiveData` sin castings forzados
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al procesar eventos SSE: " + e.getMessage());
        }
    }

    // Detectar el tipo real del contenido en `LiveData`
    private Type getLiveDataType(MutableLiveData<?> liveData) {
        Type genericSuperclass = liveData.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];

            // Si es una lista, almacenar el tipo correctamente
            if (actualType instanceof ParameterizedType && ((ParameterizedType) actualType).getRawType() == List.class) {
                return new com.google.gson.reflect.TypeToken<List<T>>() {}.getType();
            } else {
                return actualType;
            }
        } else {
            throw new IllegalArgumentException("No se pudo determinar el tipo de `MutableLiveData`.");
        }
    }

    // Método seguro para actualizar `LiveData` sin castings forzados
    @SuppressWarnings("unchecked")
    private void postToLiveData(MutableLiveData<?> liveData, Object parsedEvent) {
        if (parsedEvent instanceof List) {
            ((MutableLiveData<List<T>>) liveData).postValue((List<T>) parsedEvent);
        } else {
            ((MutableLiveData<T>) liveData).postValue((T) parsedEvent);
        }
    }
}