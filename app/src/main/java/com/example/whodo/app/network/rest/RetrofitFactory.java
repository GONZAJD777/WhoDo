package com.example.whodo.app.network.rest;

import android.content.Context;

import com.example.whodo.app.utils.SslUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    // Un único OkHttpClient global
    private static OkHttpClient globalClient;

    // Mapa de Retrofit por baseUrl
    private static final Map<String, Retrofit> retrofitInstances = new ConcurrentHashMap<>();

    private static OkHttpClient getGlobalClient(Context context) {
        if (globalClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            Cache cache = new Cache(new File(context.getCacheDir(), "http-cache"), 10 * 1024 * 1024);

            globalClient = SslUtils.getSecureClient(context.getApplicationContext())
                    .newBuilder()
                    // Timeouts explícitos
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    // Pool compartido pero con vida corta para evitar sockets muertos
                    .connectionPool(new ConnectionPool(10, 1, TimeUnit.MINUTES))
                    .addInterceptor(logging)
                    .cache(cache)
                    .build();
        }
        return globalClient;
    }


    public static Retrofit getRetrofit(String baseUrl, Context context) {
        return retrofitInstances.computeIfAbsent(baseUrl, url ->
                new Retrofit.Builder()
                        .baseUrl(url)
                        .client(getGlobalClient(context))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
        );
    }

    public static <T> T createService(Class<T> serviceClass, String baseUrl, Context context) {
        return getRetrofit(baseUrl, context).create(serviceClass);
    }
}
