package br.com.cotta.SpotHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class HttpRequestService {

    private final RestTemplate restTemplate;
    @Autowired
    @Lazy
    private SpotifyAutheticationService spotifyAutheticationService;

    public HttpRequestService() {
        this.restTemplate = new RestTemplate();
    }

    public <T> ResponseEntity<T> sendAuthenticatedRequest(
            String url,
            HttpMethod method,
            Map<String, String> headers,
            Object body,
            Class<T> responseType) {

        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", "Bearer " + spotifyAutheticationService.getAccessToken());

        try {
            return sendRequest(url, method, headers, body, responseType);
        }catch (HttpClientErrorException.Unauthorized e){
            spotifyAutheticationService.setAccessToken();
            headers.put("Authorization", "Bearer " + spotifyAutheticationService.getAccessToken());
            return sendRequest(url, method, headers, body, responseType);
        }
    }

    public <T> ResponseEntity<T> sendRequest(
            String url,
            HttpMethod method,
            Map<String, String> headers,
            Object body,
            Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);

        return restTemplate.exchange(url, method, requestEntity, responseType);
    }
}
