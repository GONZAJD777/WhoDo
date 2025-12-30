package com.example.whodo.app.domain.workOrder.dao;

import androidx.lifecycle.LiveData;

import com.example.whodo.app.Callback;
import com.example.whodo.app.MainActivityViewModel;
import com.example.whodo.app.ViewModelInterface;
import com.example.whodo.app.domain.workOrder.WorkOrder;

import java.util.List;

public interface WorkOrderDao<T> {
    void find(ViewModelInterface pViewModel, T t);
    void findByUserID(T t, Callback<List<T>> callback);
    void create(T t, Callback<T> callback);
    void update(T t, Callback<T> callback);
    void closeConnection();
}

