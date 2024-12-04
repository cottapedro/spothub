package br.com.cotta.SpotHub.dto.out;

import lombok.Data;

import java.util.List;

@Data
public class PlaybackLyrics {
    private Long trackId;
    private String trackName;
    private String artistName;
    private List<LyricLine> lyrics;
}

