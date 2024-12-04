package br.com.cotta.SpotHub.service;

import br.com.cotta.SpotHub.dto.in.AcessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpotifyAutheticationService {

    @Autowired
    private HttpRequestService httpRequestService;
    @Value("${spotfy-client-id}")
    private String clientId;
    @Value("${spotfy-client-secret}")
    private String clientSecret;
    private String ACCESSTOKEN;


    public void setAccessToken() {
        String url = "https://accounts.spotify.com/api/token";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String body = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;

        ResponseEntity<AcessTokenResponse> response = httpRequestService.sendRequest(
                url,
                HttpMethod.POST,
                headers,
                body,
                AcessTokenResponse.class
        );

        ACCESSTOKEN = response.getBody().getAccessToken();
    }

    public String getAccessToken() {
        if(ACCESSTOKEN == null){
            setAccessToken();
        }
        return this.ACCESSTOKEN;
    }
}
