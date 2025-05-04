package br.com.cotta.SpotHub.interfaces.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@RestController
@RequestMapping("/spotify")
public class SpotifyAuthController {
    @Value("${spotify-client-id}")
    private String clientId;

    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize() throws UnsupportedEncodingException {
        String redirectUri = "http://localhost:8080/callback";
        String scopes = "user-read-private user-read-email user-read-playback-state";
        String state = "secure_random_state";

        String encodedScope = URLEncoder.encode(scopes, "UTF-8");

        String url = "https://accounts.spotify.com/authorize?client_id=" + clientId
                + "&response_type=code"
                + "&redirect_uri=" + redirectUri
                + "&scope=" + encodedScope
                + "&state=" + state;

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}