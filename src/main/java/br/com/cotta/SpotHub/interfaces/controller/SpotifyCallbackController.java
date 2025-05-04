package br.com.cotta.SpotHub.interfaces.controller;

import br.com.cotta.SpotHub.application.service.SpotifyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyCallbackController {

    @Autowired
    private SpotifyAuthService spotifyAuthService;
    @GetMapping("/callback")
    public ResponseEntity<String> handleSpotifyCallback(
            @RequestParam String code,
            @RequestParam String state) {
        spotifyAuthService.fetchUserAuthToken(code);
        return ResponseEntity.ok("Código recebido: " + code);
    }
}
