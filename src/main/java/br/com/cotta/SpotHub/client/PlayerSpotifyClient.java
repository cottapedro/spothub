package br.com.cotta.SpotHub.client;

import br.com.cotta.SpotHub.domain.Artist;
import br.com.cotta.SpotHub.domain.PlayBackState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "PlayerSpotifyClient",
        url = "https://api.spotify.com"
)
public interface PlayerSpotifyClient {
    @GetMapping(value = "/v1/me/player")
    PlayBackState getPlayBackState(@RequestHeader("Authorization") String authorization);
}
