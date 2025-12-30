package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.domain.workOrder.WorkOrder;
import com.example.whodo.app.network.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface WorkOrderApi {
    @GET("work-orders/{id}")
    Call<WorkOrder> find(@Path("id") String workOrderId);
    @POST("/api/work-orders/createWorkOrder")
    Call<WorkOrder> createWorkOrder(@Body WorkOrder workOrder);
    @PUT("/api/work-orders/updateWorkOrder")
    Call<WorkOrder> updateWorkOrder(@Body WorkOrder workOrder);
    @GET("/api/work-orders/getAllWorkOrdersByUserId")
    Call<ApiResponse<List<WorkOrder>>> getAllWorkOrdersByUserId(@Query("pUserId") String userId);
}