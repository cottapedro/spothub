package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.domain.model.Track;
import br.com.cotta.SpotHub.interfaces.dto.out.LyricLine;
import br.com.cotta.SpotHub.interfaces.dto.out.PlaybackLyrics;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaybackLyricsMapper {
    public PlaybackLyrics mapFromTrack(Track track) {
        List<LyricLine> lyrics = track.getLyrics().stream()
                .map(item -> new LyricLine(item.getTimestamp(), item.getLine(), item.getTranslatedLine()))
                .toList();

        PlaybackLyrics playbackLyrics = new PlaybackLyrics();
        playbackLyrics.setTrackId(track.getId());
        playbackLyrics.setTrackName(track.getName());
        playbackLyrics.setArtistName(track.getArtists());
        playbackLyrics.setLyrics(lyrics);
        return playbackLyrics;
    }
}
