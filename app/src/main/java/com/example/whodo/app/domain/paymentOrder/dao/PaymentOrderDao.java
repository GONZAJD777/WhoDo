package com.example.whodo.app.domain.paymentOrder.dao;

import androidx.lifecycle.LiveData;

import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.paymentOrder.PaymentOrder;
import com.example.whodo.app.domain.paymentOrder.PaymentRequest;
import com.example.whodo.app.domain.paymentOrder.PaymentResponse;

import java.util.List;

public interface PaymentOrderDao<T> {
    void find(String paymentOrderId, Callback<PaymentOrder> callback);
    void createPayment(PaymentRequest t, Callback<PaymentResponse> callback);
    void createPaymentOrder(T t, Callback<T> callback);
    void update(T t, Callback<T> callback);
}
