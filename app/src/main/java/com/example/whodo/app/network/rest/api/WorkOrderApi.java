package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.domain.workOrder.WorkOrderDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;

public interface WorkOrderApi {

    @GET("workorders/{id}")
    Call<WorkOrderDTO> find(@Path("id") String workOrderId);

    @POST("workorders")
    Call<WorkOrderDTO> create(@Body WorkOrderDTO workOrderDTO);

    @PUT("workorders/{id}")
    Call<WorkOrderDTO> update(@Path("id") String workOrderId, @Body WorkOrderDTO workOrderDTO);

    @GET("workorders")
    Call<List<WorkOrderDTO>> findAll();
}