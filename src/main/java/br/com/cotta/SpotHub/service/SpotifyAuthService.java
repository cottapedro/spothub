package br.com.cotta.SpotHub.service;

import br.com.cotta.SpotHub.client.AuthSpotifyClient;
import br.com.cotta.SpotHub.dto.in.AcessTokenResponse;
import br.com.cotta.SpotHub.dto.out.AcessTokenRequest;
import br.com.cotta.SpotHub.dto.out.AuthTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SpotifyAuthService {

    @Autowired
    private AuthSpotifyClient authSpotifyClient;

    @Value("${spotfy-client-id}")
    private String clientId;
    @Value("${spotfy-client-secret}")
    private String clientSecret;

    @Value("${spotfy-redirect-uri}")
    private String redirectUri;
    private String ACCESSTOKEN;
    private String AUTHTOKEN;


    public void setAccessToken() {
      AcessTokenRequest acessTokenRequest =
              new AcessTokenRequest("client_credentials",clientId,clientSecret);

        ACCESSTOKEN = authSpotifyClient.login(acessTokenRequest).getAccessToken();
    }

    public String getAccessToken() {
        if(ACCESSTOKEN == null){
            setAccessToken();
        }
        return this.ACCESSTOKEN;
    }

    public void setAuthToken(String code){
        AuthTokenRequest authTokenRequest =
                new AuthTokenRequest("authorization_code",code,redirectUri);

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        AcessTokenResponse response = authSpotifyClient.authorize(basicAuth,authTokenRequest);
        AUTHTOKEN = response.getAccessToken();
        }

    public String getAuthToken() {
        if(AUTHTOKEN == null){
            //verificatokennobanco();
        }
        return this.AUTHTOKEN;
    }

}
