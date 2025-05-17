package com.example.whodo.app.network.reactive;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.user.UserDTO;
import com.example.whodo.app.domain.workOrder.WorkOrderDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SEEWorkOrderCallback implements Callback {

    private static final String TAG = "SSELoggedUserCallback";
    private final MutableLiveData<List<WorkOrderDTO>> liveData; // LiveData del tipo User o List<User>
    private final Gson gson = new Gson();
    // Se crea el tipo para deserializar ApiResponse<T> o ApiResponse<List<T>>

    public SEEWorkOrderCallback(MutableLiveData<List<WorkOrderDTO>> liveData) {
        this.liveData = liveData;
    }
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {

    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

    }
}
