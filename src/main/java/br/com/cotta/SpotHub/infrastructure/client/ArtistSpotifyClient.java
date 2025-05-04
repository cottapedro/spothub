package br.com.cotta.SpotHub.infrastructure.client;


import br.com.cotta.SpotHub.domain.spotify.Artist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "ArtistSpotifyClient",
        url = "https://api.spotify.com"
)
public interface ArtistSpotifyClient {
    @GetMapping(value = "/v1/artists/{id}")
    Artist getArtist(@RequestHeader("Authorization") String authorization,
                     @PathVariable("id") String id);
}
