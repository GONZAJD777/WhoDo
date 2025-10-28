package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.domain.workOrder.WorkOrderApiRestRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;

public interface WorkOrderApi {

    @GET("work-orders/{id}")
    Call<WorkOrder> find(@Path("id") String workOrderId);

    @POST("/api/work-orders/createWorkOrder")
    Call<WorkOrder> createWorkOrder(@Body WorkOrderApiRestRequestDTO workOrderDTO);

    @PUT("/api/work-orders/updateWorkOrder")
    Call<WorkOrder> updateWorkOrder(@Body WorkOrderApiRestRequestDTO workOrderDTO);

    @GET("work-orders")
    Call<List<WorkOrder>> findAll();
}