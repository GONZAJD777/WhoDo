package com.example.whodo.app.domain.paymentOrder.dao.Impl;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.whodo.BuildConfig;
import com.example.whodo.app.Callback;
import com.example.whodo.app.domain.paymentOrder.PaymentOrder;
import com.example.whodo.app.domain.paymentOrder.PaymentRequest;
import com.example.whodo.app.domain.paymentOrder.PaymentResponse;
import com.example.whodo.app.domain.paymentOrder.dao.PaymentOrderDao;
import com.example.whodo.app.network.rest.RetrofitFactory;
import com.example.whodo.app.network.rest.api.PaymentOrderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PaymentOrderDaoImpl implements PaymentOrderDao< PaymentOrder > {

    private final String TAG = "LOGGER-MONGODB-PAYMENTORDER-DAO";
    private final PaymentOrderApi mPaymentOrderApi;
    private final String mBaseUrl= BuildConfig.BASE_URL_PAYMENT_SERVICE;
    public PaymentOrderDaoImpl(Context pContext) {
        this.mPaymentOrderApi = RetrofitFactory.createService(PaymentOrderApi.class, mBaseUrl, pContext);
    }
    @Override
    public LiveData<List<PaymentOrder>> find(PaymentOrder paymentOrder) {
        return null;
    }
    @Override
    public void createPayment(PaymentRequest pPaymentRequest, Callback<PaymentResponse> callback) {
        Call<PaymentResponse> call = mPaymentOrderApi.createPayment(pPaymentRequest,"mercadoPagoBridge");
        Log.d(TAG, "Endpoint Requested -->" + mPaymentOrderApi.createPayment(pPaymentRequest,"mercadoPagoBridge").request().url());
        Log.d(TAG, "createPayment() -->" + pPaymentRequest.toString() );

        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PaymentResponse> call, @NonNull Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en la respuesta: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<PaymentResponse> call, @NonNull Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }
    @Override
    public void createPaymentOrder(PaymentOrder paymentOrder, Callback<PaymentOrder> callback) {

    }
    @Override
    public void update(PaymentOrder paymentOrder, Callback<PaymentOrder> callback) {

    }
}
