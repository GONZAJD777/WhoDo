package com.example.whodo.app.domain.workOrder.dao;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.BuildConfig;
import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.workOrder.WorkOrderDTO;
import com.example.whodo.app.network.reactive.SSEWorkOrderClient;
import com.example.whodo.app.network.rest.RetrofitClient;
import com.example.whodo.app.network.rest.api.WorkOrderApi;

import java.util.List;

public class MongoDBWorkOrderDAO implements WorkOrderDao<WorkOrderDTO>{
    private final WorkOrderApi mWorkOrderApi;
    private final String mBaseUrl= BuildConfig.BASE_URL;

    public MongoDBWorkOrderDAO() {
        RetrofitClient retrofitClient = new RetrofitClient(mBaseUrl);
        this.mWorkOrderApi = retrofitClient.createService(WorkOrderApi.class);

    }


    @Override
    public LiveData<List<WorkOrderDTO>> find(WorkOrderDTO workOrderDTO) {

        MutableLiveData<List<WorkOrderDTO>> mWorkOrder = new MutableLiveData<>();
        SSEWorkOrderClient sseWorkOrderClient = new SSEWorkOrderClient(mBaseUrl + "work-orders/stream/");
        sseWorkOrderClient.startListening(mWorkOrder);

        return null;
    }

    @Override
    public void create(WorkOrderDTO workOrderDTO, Callback<WorkOrderDTO> callback) {

    }

    @Override
    public void update(WorkOrderDTO workOrderDTO, Callback<WorkOrderDTO> callback) {

    }
}
