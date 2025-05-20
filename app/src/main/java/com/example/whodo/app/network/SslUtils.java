package com.example.whodo.app.network;

import android.content.Context;

import com.example.whodo.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import okhttp3.OkHttpClient;

public class SslUtils {
    public static OkHttpClient getSecureClient(Context context) {
        try {
            // Cargar el certificado desde res/raw/
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(context.getResources().openRawResource(R.raw.keystore));
            Certificate ca = cf.generateCertificate(caInput);
            caInput.close();

            // Crear KeyStore y agregar el certificado
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Configurar TrustManager con el certificado
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // Configurar SSLContext con el TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

            // 🔥 Aquí eliminamos la validación de hostname
            return new OkHttpClient.Builder()
                    .hostnameVerifier((hostname, session) -> true) // ⚠ ¡Desactiva la validación del hostname!
                    .sslSocketFactory(sslContext.getSocketFactory(), (javax.net.ssl.X509TrustManager) tmf.getTrustManagers()[0])
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new OkHttpClient();
        }
    }
}