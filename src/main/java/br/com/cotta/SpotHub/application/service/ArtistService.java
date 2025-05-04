package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.infrastructure.client.ArtistSpotifyClient;
import br.com.cotta.SpotHub.domain.spotify.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    @Autowired
    private ArtistSpotifyClient artistSpotifyClient;
    @Autowired
    private SpotifyAuthService spotifyAuthService;

    public Artist getArtists(String id){
        return artistSpotifyClient.getArtist("Bearer " +
                spotifyAuthService.fetchClientAccessToken(), id);
    }
}
