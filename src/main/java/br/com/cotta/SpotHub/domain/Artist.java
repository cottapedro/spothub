package br.com.cotta.SpotHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Artist {

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;

    private Followers followers;
    private List<String> genres;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private Integer popularity;
    private String type;
    private String uri;

    @Data
    public static class Followers {
        private String href;
        private Integer total;
    }


}
