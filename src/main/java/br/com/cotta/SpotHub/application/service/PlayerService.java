package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.infrastructure.client.PlayerSpotifyClient;
import br.com.cotta.SpotHub.domain.spotify.PlayBackState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private SpotifyAuthService spotifyAuthService;
    @Autowired
    private PlayerSpotifyClient playerSpotifyClient;

    public PlayBackState getCurrentPlayBackStates() {
        return playerSpotifyClient.getPlayBackState("Bearer " +
                spotifyAuthService.fetchUserAuthToken(null));
    }
}
