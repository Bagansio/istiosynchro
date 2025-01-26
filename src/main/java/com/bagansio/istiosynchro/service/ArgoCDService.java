package com.bagansio.istiosynchro.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

@Service
public class ArgoCDService {

    @Value("${argocd.token}") // Inject the Git token from application.properties or environment
    private String argoCDToken;


    public void triggerSync(String appName) {
        // Disable SSL verification (INSECURE!)
        disableSSLVerification();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(argoCDToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "https://localhost:8080/api/v1/applications/" + appName + "/sync";
        HttpEntity<String> request = new HttpEntity<>("{}", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println("Sync triggered. Response: " + response.getBody());
    }

    private void disableSSLVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
