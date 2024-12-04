package br.com.cotta.SpotHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExternalUrls {
    @JsonProperty("spotify")
    private String spotify;
}
