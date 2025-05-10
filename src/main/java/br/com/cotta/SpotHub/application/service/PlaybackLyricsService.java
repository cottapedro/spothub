package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.domain.model.Track;
import br.com.cotta.SpotHub.domain.spotify.PlayBackState;
import br.com.cotta.SpotHub.interfaces.dto.out.PlaybackLyrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaybackLyricsService {

    @Autowired
    PlayerService playerService;

    @Autowired
    TrackService trackService;

    @Autowired
    PlaybackLyricsMapper playbackLyricsMapper;

    public PlaybackLyrics getPlaybackLyrics() {
        PlayBackState state = playerService.getCurrentPlayBackStates();
        if(state == null) throw new IllegalStateException("Nenhuma Track em execução.");

        Track track = trackService.resolveTrack(state);
        return playbackLyricsMapper.mapFromTrack(track);
    }
}
