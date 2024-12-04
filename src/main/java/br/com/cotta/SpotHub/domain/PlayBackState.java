package br.com.cotta.SpotHub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlayBackState {
    private Device device;
    @JsonProperty("shuffle_state")
    private boolean shuffleState;
    @JsonProperty("smart_shuffle")
    private boolean smartShuffle;
    @JsonProperty("repeat_state")
    private String repeatState;
    private long timestamp;
    private Context context;
    @JsonProperty("progress_ms")
    private int progressMs;
    private Item item;
    @JsonProperty("currently_playing_type")
    private String currentlyPlayingType;
    private Actions actions;
    @JsonProperty("is_playing")
    private boolean isPlaying;
}
