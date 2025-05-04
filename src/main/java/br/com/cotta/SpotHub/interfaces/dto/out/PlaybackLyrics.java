package br.com.cotta.SpotHub.interfaces.dto.out;

import br.com.cotta.SpotHub.domain.model.Artists;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PlaybackLyrics {
    private Long trackId;
    private String trackName;
    private Set<Artists> artistName;
    private List<LyricLine> lyrics;
}

