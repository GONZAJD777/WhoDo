package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.domain.paymentOrder.PaymentRequest;
import com.example.whodo.app.domain.paymentOrder.PaymentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentOrderApi {
    @POST("/api/payment/createPayment")
    Call<PaymentResponse> createPayment(@Body PaymentRequest paymentRequest, @Query("paymentProvider") String paymentProvider);

}
