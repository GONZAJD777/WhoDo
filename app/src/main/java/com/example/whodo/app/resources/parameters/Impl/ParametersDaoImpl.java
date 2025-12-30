package com.example.whodo.app.resources.parameters.Impl;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.whodo.BuildConfig;
import com.example.whodo.app.Callback;
import com.example.whodo.app.network.ApiResponse;
import com.example.whodo.app.network.rest.RetrofitFactory;
import com.example.whodo.app.network.rest.api.ParameterApi;
import com.example.whodo.app.resources.parameters.Parameter;
import com.example.whodo.app.resources.parameters.ParametersDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ParametersDaoImpl implements ParametersDao<Parameter> {

    private final String TAG = "LOGGER-MONGODB-PARAMETER-DAO";
    private final ParameterApi mParameterApi;
    private final String mBaseUrl= BuildConfig.BASE_URL_WORKORDER_SERVICE;

    public ParametersDaoImpl(Context pContext) {
        Log.d(TAG, "mBaseUrl -->" + mBaseUrl);
        this.mParameterApi = RetrofitFactory.createService(ParameterApi.class, mBaseUrl, pContext);
    }


    @Override
    public void getParameters(Callback<ApiResponse<List<Parameter>>> callback) {
        Call<ApiResponse<List<Parameter>>> call = mParameterApi.getAllParametersByStatus("1");
        Log.d(TAG, "Endpoint Requested -->" + mParameterApi.getAllParametersByStatus("1").request().url());

        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Parameter>>> call, @NonNull Response<ApiResponse<List<Parameter>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Parameter>>> call, @NonNull Throwable t) {
                callback.onError(new Exception(t));
                Log.d(TAG, " getParameters() onFailure -->" + t);

            }
        });
    }


}
