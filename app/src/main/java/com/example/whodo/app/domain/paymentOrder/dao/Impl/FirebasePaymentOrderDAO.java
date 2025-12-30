package com.example.whodo.app.domain.paymentOrder.dao.Impl;

import androidx.lifecycle.LiveData;

import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.paymentOrder.PaymentOrder;
import com.example.whodo.app.domain.paymentOrder.PaymentRequest;
import com.example.whodo.app.domain.paymentOrder.PaymentResponse;
import com.example.whodo.app.domain.paymentOrder.dao.PaymentOrderDao;

import java.util.List;

public class FirebasePaymentOrderDAO implements PaymentOrderDao<PaymentOrder> {
    @Override
    public LiveData<List<PaymentOrder>> find(PaymentOrder paymentOrderDTO) {


        return null;
    }

    @Override
    public void createPayment(PaymentRequest t, Callback<PaymentResponse> callback) {

    }

    @Override
    public void createPaymentOrder(PaymentOrder paymentOrder, Callback<PaymentOrder> callback) {

    }

    @Override
    public void update(PaymentOrder paymentOrderDTO, Callback<PaymentOrder> callback) {

    }
}
