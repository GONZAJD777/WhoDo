package com.example.whodo.domain.paymentOrder.dao;

import androidx.lifecycle.LiveData;

import com.example.whodo.aplication.Callback;
import com.example.whodo.domain.paymentOrder.PaymentOrderDTO;

import java.util.List;

public class FirebasePaymentOrderDAO implements PaymentOrderDao<PaymentOrderDTO>{
    @Override
    public LiveData<List<PaymentOrderDTO>> find(PaymentOrderDTO paymentOrderDTO) {
        return null;
    }

    @Override
    public void create(PaymentOrderDTO paymentOrderDTO, Callback<PaymentOrderDTO> callback) {

    }

    @Override
    public void update(PaymentOrderDTO paymentOrderDTO, Callback<PaymentOrderDTO> callback) {

    }
}
