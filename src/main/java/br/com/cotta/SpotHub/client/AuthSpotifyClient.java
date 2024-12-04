package br.com.cotta.SpotHub.client;

import br.com.cotta.SpotHub.dto.in.AcessTokenResponse;
import br.com.cotta.SpotHub.dto.out.AcessTokenRequest;
import br.com.cotta.SpotHub.dto.out.AuthTokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "AuthSpotifyClient",
        url = "https://accounts.spotify.com"
)
public interface AuthSpotifyClient {

    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AcessTokenResponse login(@RequestBody AcessTokenRequest request);

    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AcessTokenResponse authorize(@RequestHeader("Authorization") String authorization,
                                 @RequestBody AuthTokenRequest request);
}
