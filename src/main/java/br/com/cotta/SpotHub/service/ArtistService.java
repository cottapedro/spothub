package br.com.cotta.SpotHub.service;

import br.com.cotta.SpotHub.client.ArtistSpotifyClient;
import br.com.cotta.SpotHub.domain.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    @Autowired
    private ArtistSpotifyClient artistSpotifyClient;
    @Autowired
    private SpotifyAuthService spotifyAuthService;
    @Autowired
    private HttpRequestService httpRequestService;
    private  String url = "https://api.spotify.com/v1/artists";

    public Artist getArtist(String id) {
        url = url+"/"+id;
        return httpRequestService
                .sendAuthenticatedRequest(url, HttpMethod.GET,null,null,Artist.class)
                .getBody();
    }

    public Artist getArtists(String id){
        return artistSpotifyClient.getArtist("Bearer " +
                spotifyAuthService.getAccessToken(), id);
    }
}
