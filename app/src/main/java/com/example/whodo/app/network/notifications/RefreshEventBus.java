package com.example.whodo.app.network.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RefreshEventBus {
    private static RefreshEventBus instance;
    private final MutableLiveData<Boolean> refreshSignal = new MutableLiveData<>();

    private RefreshEventBus() {}

    public static synchronized RefreshEventBus getInstance() {
        if (instance == null) {
            instance = new RefreshEventBus();
        }
        return instance;
    }

    // Método para emitir la señal
    public void postRefreshSignal() {
        refreshSignal.postValue(true);
    }

    // Método para observar la señal
    public LiveData<Boolean> getSignals() {
        return refreshSignal;
    }
}

