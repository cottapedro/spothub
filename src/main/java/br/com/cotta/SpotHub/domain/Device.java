package br.com.cotta.SpotHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Device {
    private String id;
    @JsonProperty("is_active")
    private boolean isActive;
    @JsonProperty("is_private_session")
    private boolean isPrivateSession;
    @JsonProperty("is_restricted")
    private boolean isRestricted;
    private String name;
    @JsonProperty("supports_volume")
    private boolean supportsVolume;
    private String type;
    @JsonProperty("volume_percent")
    private int volumePercent;
}

