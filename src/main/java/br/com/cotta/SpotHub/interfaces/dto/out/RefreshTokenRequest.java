package br.com.cotta.SpotHub.interfaces.dto.out;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenRequest {

    @FormProperty("grant_type")
    private String grantType;

    @FormProperty("refresh_token")
    private String refreshToken;

    @FormProperty("client_id")
    private String clientId;
}
