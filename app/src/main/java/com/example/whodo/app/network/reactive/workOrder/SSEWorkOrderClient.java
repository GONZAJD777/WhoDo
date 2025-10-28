package com.example.whodo.app.network.reactive.workOrder;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.whodo.app.domain.workOrder.WorkOrder;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class SSEWorkOrderClient {
    private final OkHttpClient client ;
    private final String SSE_URL ;
    private static final String TAG = "SSEWorkOrderClient";

    public SSEWorkOrderClient(String sseUrl) {
        try {
            // Creación de TrustManager que acepta cualquier certificado
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Inicializar contexto SSL con el TrustManager personalizado
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true) // Desactiva validación de hostname
                    .readTimeout(0, TimeUnit.MILLISECONDS)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error inicializando TrustManager", e);
        }

        SSE_URL = sseUrl;
    }

    public void startListening(MutableLiveData<List<WorkOrder>> mWorkOrders) {
        Log.d(TAG, "Iniciando conexión SSE con: " + SSE_URL);
        Request request = new Request.Builder().url(SSE_URL).build();
        client.newCall(request).enqueue(new SEEWorkOrderCallback(mWorkOrders)); // Ahora SSECallback detecta automáticamente si es lista o un solo objeto
    }
}

