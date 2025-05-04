package br.com.cotta.SpotHub.interfaces.dto.out;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthTokenRequest {
    @FormProperty("grant_type")
    private String grantType;

    private String code;

    @FormProperty("redirect_uri")
    private String redirectUri;

}
