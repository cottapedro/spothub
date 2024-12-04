package br.com.cotta.SpotHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Context {
    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
    private String href;
    private String type;
    private String uri;
}
