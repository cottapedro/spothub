package br.com.cotta.SpotHub.service;

import br.com.cotta.SpotHub.client.PlayerSpotifyClient;
import br.com.cotta.SpotHub.domain.PlayBackState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private SpotifyAuthService spotifyAuthService;
    @Autowired
    private PlayerSpotifyClient playerSpotifyClient;
    @Autowired
    private HttpRequestService httpRequestService;

    private  String url = "https://api.spotify.com/v1/me/player";

    public PlayBackState getCurrentPlayBackState() {
        return httpRequestService
                .sendAuthenticatedRequest(url, HttpMethod.GET,null,null,PlayBackState.class)
                .getBody();
    }

    public PlayBackState getCurrentPlayBackStates() {
        return playerSpotifyClient.getPlayBackState("Bearer " +
                spotifyAuthService.getAuthToken());
    }
}
