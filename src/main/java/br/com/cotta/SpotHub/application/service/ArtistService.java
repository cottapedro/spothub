package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.domain.model.Artists;
import br.com.cotta.SpotHub.domain.spotify.PlayBackState;
import br.com.cotta.SpotHub.infrastructure.client.ArtistSpotifyClient;
import br.com.cotta.SpotHub.domain.spotify.Artist;
import br.com.cotta.SpotHub.infrastructure.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    @Autowired
    private ArtistSpotifyClient artistSpotifyClient;
    @Autowired
    private SpotifyAuthService spotifyAuthService;
    @Autowired
    private ArtistRepository artistRepository;

    public Artist getArtists(String id){
        return artistSpotifyClient.getArtist("Bearer " +
                spotifyAuthService.fetchClientAccessToken(), id);
    }

    public Set<Artists> findOrCreateAll(PlayBackState state) {
        return state.getItem().getArtists().stream()
                .map(spotifyArtist -> artistRepository.findByName(spotifyArtist.getName())
                        .orElseGet(() -> new Artists(spotifyArtist.getName())))
                .collect(Collectors.toSet());
    }
}
