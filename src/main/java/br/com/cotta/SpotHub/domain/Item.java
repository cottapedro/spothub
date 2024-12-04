package br.com.cotta.SpotHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Item {
    private Album album;
    private List<Artist> artists;
    @JsonProperty("available_markets")
    private List<String> availableMarkets;
    @JsonProperty("disc_number")
    private int discNumber;
    @JsonProperty("duration_ms")
    private int durationMs;
    private boolean explicit;
    @JsonProperty("external_ids")
    private ExternalIds externalIds;
    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    @JsonProperty("is_local")
    private boolean isLocal;
    private String name;
    private int popularity;
    @JsonProperty("preview_url")
    private String previewUrl;
    @JsonProperty("track_number")
    private int trackNumber;
    private String type;
    private String uri;
}
