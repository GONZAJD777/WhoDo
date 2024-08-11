package com.example.whodo.app.domain.workOrder.dao;

import androidx.lifecycle.LiveData;

import com.example.whodo.app.Callback;

import java.util.List;

public interface WorkOrderDao<T> {

    LiveData<List<T>> find(T t);
    void create(T t, Callback<T> callback);
    void update(T t, Callback<T> callback);

}

