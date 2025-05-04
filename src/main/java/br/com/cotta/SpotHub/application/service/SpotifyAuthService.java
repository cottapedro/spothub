package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.infrastructure.client.AuthSpotifyClient;
import br.com.cotta.SpotHub.domain.model.AuthToken;
import br.com.cotta.SpotHub.interfaces.dto.in.AcessTokenResponse;
import br.com.cotta.SpotHub.interfaces.dto.out.AcessTokenRequest;
import br.com.cotta.SpotHub.interfaces.dto.out.AuthTokenRequest;
import br.com.cotta.SpotHub.interfaces.dto.out.RefreshTokenRequest;
import br.com.cotta.SpotHub.infrastructure.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class SpotifyAuthService {

    @Autowired
    private AuthSpotifyClient authSpotifyClient;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Value("${spotify-client-id}")
    private String clientId;

    @Value("${spotify-client-secret}")
    private String clientSecret;

    @Value("${spotify-redirect-uri}")
    private String redirectUri;

    public String fetchClientAccessToken() {
        AcessTokenRequest acessTokenRequest =
                new AcessTokenRequest("client_credentials", clientId, clientSecret);

        return authSpotifyClient.login(acessTokenRequest).getAccessToken();
    }

    public String fetchUserAuthToken(String code) {
        if (code != null) {
            return generateAuthToken(code).getAccessToken();
        }
        return fetchOrRefreshAuthToken().getAccessToken();
    }

    private AuthToken fetchOrRefreshAuthToken() {
        AuthToken authToken = fetchAuthToken();

        if (authToken == null || isTokenExpired(authToken)) {
            if (authToken == null) {
                throw new IllegalStateException("AuthToken não encontrado. É necessário fazer autenticacao.");
            }
            refreshAuthToken(authToken);
        }

        return authToken;
    }

    private AuthToken generateAuthToken(String code) {
        AuthTokenRequest authTokenRequest =
                new AuthTokenRequest("authorization_code", code, redirectUri);

        AcessTokenResponse response = authSpotifyClient.authorize(createBasicAuthHeader(), authTokenRequest);

        AuthToken authToken = new AuthToken();
        updateAuthToken(authToken, response);
        return authToken;
    }

    private String createBasicAuthHeader() {
        return "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
    }

    private void refreshAuthToken(AuthToken authToken) {
        RefreshTokenRequest refreshTokenRequest =
                new RefreshTokenRequest("refresh_token", authToken.getRefreshToken(), clientId);

        AcessTokenResponse response = authSpotifyClient.refresh(createBasicAuthHeader(), refreshTokenRequest);
        updateAuthToken(authToken, response);
    }

    private void updateAuthToken(AuthToken authToken, AcessTokenResponse response) {
        if(response.getRefreshToken() != null)
            authToken.setRefreshToken(response.getRefreshToken());
        authToken.setAccessToken(response.getAccessToken());
        authToken.setDataAtualizacao(LocalDateTime.now());
        authToken.setScope(response.getScope());
        authToken.setExpiresIn(response.getExpiresIn());
        authToken.setTokenType(response.getTokenType());
        authTokenRepository.save(authToken);
    }

    private boolean isTokenExpired(AuthToken authToken) {
        LocalDateTime dataExpiracao = authToken.getDataAtualizacao().plusSeconds(authToken.getExpiresIn());
        return dataExpiracao.isBefore(LocalDateTime.now());
    }

    private AuthToken fetchAuthToken() {
        return authTokenRepository.findFirstByOrderByDataAtualizacaoDesc().orElse(null);
    }
}

