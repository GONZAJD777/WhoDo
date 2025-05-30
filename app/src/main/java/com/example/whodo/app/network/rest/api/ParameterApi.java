package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.network.ApiResponse;
import com.example.whodo.app.resources.parameters.Parameter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParameterApi {

    @GET("/api/parameters/getAllParametersByStatus/{status}")
    Call<ApiResponse<List<Parameter>>> getAllParametersByStatus(@Path("status") String status);

}
