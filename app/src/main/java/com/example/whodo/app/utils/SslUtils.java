package com.example.whodo.app.utils;

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
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            // Certificado del paymentorder
            InputStream certPayment = context.getResources().openRawResource(R.raw.whodo_paymentorder_serv_cert);
            Certificate caPayment = cf.generateCertificate(certPayment);
            certPayment.close();

            // Certificado del workorder
            InputStream certWork = context.getResources().openRawResource(R.raw.whodo_workorder_serv_cert);
            Certificate caWork = cf.generateCertificate(certWork);
            certWork.close();

            // Crear KeyStore y agregar ambos certificados
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("whodo_paymentorder_serv_cert", caPayment);
            keyStore.setCertificateEntry("whodo_workorder_serv_cert", caWork);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (javax.net.ssl.X509TrustManager) tmf.getTrustManagers()[0])
                    .hostnameVerifier((hostname, session) -> true) // ⚠ en dev, en prod validar CN
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new OkHttpClient();
        }
    }

}