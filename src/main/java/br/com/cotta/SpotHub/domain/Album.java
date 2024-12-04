package br.com.cotta.SpotHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Album {
    @JsonProperty("album_type")
    private String albumType;
    private List<Artist> artists;
    @JsonProperty("available_markets")
    private List<String> availableMarkets;
    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("release_date_precision")
    private String releaseDatePrecision;
    @JsonProperty("total_tracks")
    private int totalTracks;
    private String type;
    private String uri;
}

