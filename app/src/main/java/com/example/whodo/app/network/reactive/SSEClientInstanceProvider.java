package com.example.whodo.app.network.reactive;

import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

public class SSEClientInstanceProvider {
    private static OkHttpClient client;

    private SSEClientInstanceProvider() {}

    public static OkHttpClient getInstance() {
        if (client == null) {
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                            @Override public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                            @Override public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                        }
                };

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                client = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                        .hostnameVerifier((hostname, session) -> true)
                        .readTimeout(0, TimeUnit.MILLISECONDS)
                        .build();

            } catch (Exception e) {
                throw new RuntimeException("Error inicializando TrustManager", e);
            }
        }
        return client;
    }
}
