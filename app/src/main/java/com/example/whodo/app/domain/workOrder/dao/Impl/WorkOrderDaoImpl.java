package com.example.whodo.app.domain.workOrder.dao.Impl;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whodo.BuildConfig;
import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.domain.workOrder.WorkOrderDTO;
import com.example.whodo.app.domain.workOrder.dao.WorkOrderDao;
import com.example.whodo.app.network.reactive.workOrder.SSEWorkOrderClient;
import com.example.whodo.app.network.rest.RetrofitClient;
import com.example.whodo.app.network.rest.api.WorkOrderApi;

import java.util.List;

public class WorkOrderDaoImpl implements WorkOrderDao<WorkOrder> {
    private final WorkOrderApi mWorkOrderApi;
    private final String mBaseUrl= BuildConfig.BASE_URL;

    public WorkOrderDaoImpl(Context pContext) {
        RetrofitClient retrofitClient = new RetrofitClient(mBaseUrl,pContext);
        this.mWorkOrderApi = retrofitClient.createService(WorkOrderApi.class);

    }


    @Override
    public LiveData<List<WorkOrder>> find(WorkOrder workOrder) {

        MutableLiveData<List<WorkOrder>> mWorkOrder = new MutableLiveData<>();
        SSEWorkOrderClient sseWorkOrderClient = new SSEWorkOrderClient(mBaseUrl + "work-orders/stream/");
        sseWorkOrderClient.startListening(mWorkOrder);

        return null;
    }

    @Override
    public void create(WorkOrder workOrder, Callback<WorkOrder> callback) {

    }

    @Override
    public void update(WorkOrder workOrder, Callback<WorkOrder> callback) {

    }
}
