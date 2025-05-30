package com.example.whodo.app.resources.parameters;

import com.example.whodo.app.Callback;
import com.example.whodo.app.network.ApiResponse;

import java.util.List;

public interface ParametersDao<T> {
    void getParameters(Callback<ApiResponse<List<T>>> callback);
}
