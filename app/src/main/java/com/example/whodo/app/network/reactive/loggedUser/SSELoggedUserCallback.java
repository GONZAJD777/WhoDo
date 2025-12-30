package com.example.whodo.app.network.reactive.loggedUser;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.whodo.app.ViewModelInterface;
import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.network.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.BufferedSource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class SSELoggedUserCallback implements Callback {
    private static final String TAG = "LOGGER-SSE-LOGGED-USER-Callback";
    private final ViewModelInterface mViewModelInterface;
    private final Gson gson = new Gson();
    public SSELoggedUserCallback(ViewModelInterface pViewModel) {
        this.mViewModelInterface = pViewModel;
    }
    @Override
    public void onResponse(@NonNull Call call, Response response) {
        Log.d(TAG, "Conexión SSE establecida correctamente.");
        Set<String> processedEvents = new HashSet<>();

        if (response.body() == null) {
            Log.e(TAG, "Respuesta SSE sin body, se descarta.");
            return;
        }

        try (BufferedSource source = response.body().source()) {
            while (!source.exhausted()) {
                String line;
                try {
                    line = source.readUtf8Line();
                } catch (IOException ioEx) {
                    Log.e(TAG, "Error leyendo línea SSE", ioEx);
                    break; // cortamos el loop si falla lectura
                }

                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                if (!line.startsWith("data:")) {
                    continue; // ignoramos comentarios/keep-alives
                }

                String jsonEvent = line.substring("data:".length()).trim();
                if (jsonEvent.isEmpty()) {
                    continue;
                }

                if (processedEvents.contains(jsonEvent)) {
                    Log.d(TAG, "Evento duplicado descartado: " + jsonEvent);
                    continue;
                }

                processedEvents.add(jsonEvent);
                Log.d(TAG, "Evento SSE válido recibido: " + jsonEvent);

                try {
                    Type type = new TypeToken<ApiResponse<User>>() {}.getType();
                    ApiResponse<User> parsedResponse = gson.fromJson(jsonEvent, type);

                    if (parsedResponse == null || parsedResponse.getData() == null) {
                        Log.d(TAG, "Evento descartado por no tener data: " + jsonEvent);
                        continue;
                    }

                    User mUser = parsedResponse.getData();
                    Log.d(TAG, "Evento procesado: " + mUser);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        try {
                            mViewModelInterface.setLoggedUser(mUser);
                        } catch (Exception uiEx) {
                            Log.e(TAG, "Error actualizando ViewModel con loggedUser", uiEx);
                        }
                    });

                } catch (Exception parseEx) {
                    Log.e(TAG, "Error al parsear evento SSE: " + jsonEvent, parseEx);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error procesando eventos SSE", e);
        }
    }
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.e(TAG, "Error en conexión SSE", e);
    }
}
