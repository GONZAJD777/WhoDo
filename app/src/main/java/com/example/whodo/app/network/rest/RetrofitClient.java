package com.example.whodo.app.network.rest;

import android.content.Context;

import com.example.whodo.app.network.SslUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;

    public RetrofitClient(String baseUrl, Context context) {
        // Usar el cliente seguro con SSL
        OkHttpClient secureClient = SslUtils.getSecureClient(context.getApplicationContext());

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(secureClient) // Aquí se usa el cliente con SSL
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}