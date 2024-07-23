package com.example.whodo.domain.workOrder.dao;

import androidx.lifecycle.LiveData;

import com.example.whodo.aplication.Callback;

import java.util.List;

public interface WorkOrderDao<T> {

    LiveData<List<T>> find(T t);
    void create(T t, Callback<T> callback);
    void update(T t, Callback<T> callback);

}

