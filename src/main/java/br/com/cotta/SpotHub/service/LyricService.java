package br.com.cotta.SpotHub.service;

import br.com.cotta.SpotHub.domain.Artist;
import br.com.cotta.SpotHub.domain.Lyrics;
import br.com.cotta.SpotHub.domain.PlayBackState;
import br.com.cotta.SpotHub.domain.Track;
import br.com.cotta.SpotHub.dto.out.LyricLine;
import br.com.cotta.SpotHub.dto.out.PlaybackLyrics;
import br.com.cotta.SpotHub.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LyricService {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GeniusApiService geniusApiService;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TranslationService translationService;
    public PlaybackLyrics getPlaybackLyrics() {
        PlayBackState currentPlayBackState = playerService.getCurrentPlayBackStates();

        String nameMusic = currentPlayBackState.getItem().getName();
        List<String> artist = extractArtistNames(currentPlayBackState);

        Track track = findOrFetchTrack(nameMusic, artist, currentPlayBackState);

        return buildPlaybackLyrics(track);
    }

    private Track findOrFetchTrack(String nameMusic, List<String> artist, PlayBackState state) {
        Track track = getTrackFromDb(nameMusic, artist);
        if (track == null) {
            dev.spoocy.genius.data.Lyrics lyrics = geniusApiService.findLyricsByArtistAndSong(nameMusic, artist);
            track = buildTrackFromStateAndLyrics(state, lyrics);
          //  trackRepository.save(track);
        }
        return track;
    }

    private Track buildTrackFromStateAndLyrics(PlayBackState state, dev.spoocy.genius.data.Lyrics lyrics) {
        Track track = new Track();
        track.setAlbum(state.getItem().getAlbum().getName());
        track.setName(state.getItem().getName());
        track.setDuration(state.getItem().getDurationMs());
        track.setLyrics(new ArrayList<>());

        Arrays.stream(lyrics.getAsPlain().split("\\n\\n"))
                .forEach(line -> {
                    Lyrics l = new Lyrics();
                    l.setLine(line);
                    l.setTranslatedLine(getTranslateLyrcsLine(line));
                    l.setTimestamp(0);
                    track.getLyrics().add(l);
                });

        return track;
    }

    private PlaybackLyrics buildPlaybackLyrics(Track track) {
        PlaybackLyrics playbackLyrics = new PlaybackLyrics();
        playbackLyrics.setTrackName(track.getName());
        playbackLyrics.setArtistName(track.getArtist());
        List<LyricLine> lyrics = track.getLyrics().stream()
                .map(item -> new LyricLine(item.getTimestamp(), item.getLine(), item.getTranslatedLine()))
                .collect(Collectors.toList());

        playbackLyrics.setLyrics(lyrics);
        return playbackLyrics;
    }
    private List<String> extractArtistNames(PlayBackState state) {
        return state.getItem().getArtists()
                .stream()
                .map(Artist::getName)
                .collect(Collectors.toList());
    }
    private String getTranslateLyrcsLine(String lyrcsLine) {
        return translationService.translateText(lyrcsLine, "pt");
    }

    private Track getTrackFromDb(String nameMusic, List<String> artist) {
        return null;
    }
}
