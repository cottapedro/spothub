package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.domain.model.Artists;
import br.com.cotta.SpotHub.domain.model.Lyrics;
import br.com.cotta.SpotHub.domain.port.TranslationProvider;
import br.com.cotta.SpotHub.domain.spotify.Artist;
import br.com.cotta.SpotHub.domain.spotify.PlayBackState;
import br.com.cotta.SpotHub.domain.model.Track;
import br.com.cotta.SpotHub.infrastructure.provider.GoogleTranslationProvider;
import br.com.cotta.SpotHub.interfaces.dto.out.LyricLine;
import br.com.cotta.SpotHub.interfaces.dto.out.PlaybackLyrics;
import br.com.cotta.SpotHub.infrastructure.repository.ArtistRepository;
import br.com.cotta.SpotHub.infrastructure.repository.TrackRepository;
import br.com.cotta.SpotHub.domain.port.LyricsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LyricService {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private LyricsProvider lyricsProvider;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TranslationProvider translationProvider;
    @Autowired
    private ArtistRepository artistRepository;

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
            dev.spoocy.genius.data.Lyrics lyrics = lyricsProvider.findLyricsByArtistAndSong(nameMusic, artist);
            track = buildTrackFromStateAndLyrics(state, lyrics);

            trackRepository.save(track);
        }
        return track;
    }

    private Track buildTrackFromStateAndLyrics(PlayBackState state, dev.spoocy.genius.data.Lyrics lyrics) {
        Track track = new Track();
        track.setAlbum(state.getItem().getAlbum().getName());
        track.setName(state.getItem().getName());
        track.setDuration(state.getItem().getDurationMs());
        track.setLyrics(new ArrayList<>());
        track.setArtists(getArtists(state));

        Arrays.stream(lyrics.getAsPlain().split("\\n\\n"))
                .forEach(line -> {
                    Lyrics l = new Lyrics();
                    l.setLine(line);
                    l.setTranslatedLine(getTranslateLyrcsLine(line));
                    l.setTimestamp(0);
                    l.setTrack(track);
                    l.setLanguage("en");
                    track.getLyrics().add(l);
                });

        return track;
    }


    private Set<Artists> getArtists(PlayBackState state) {
        return state.getItem().getArtists()
                .stream()
                .map(spotifyArtist -> artistRepository.findByName(spotifyArtist.getName())
                        .orElseGet(() -> new Artists(spotifyArtist.getName()))
                )
                .collect(Collectors.toSet());
    }

    private PlaybackLyrics buildPlaybackLyrics(Track track) {
        PlaybackLyrics playbackLyrics = new PlaybackLyrics();
        playbackLyrics.setTrackId(track.getId());
        playbackLyrics.setTrackName(track.getName());
        playbackLyrics.setArtistName(track.getArtists());
        List<LyricLine> lyrics = track.getLyrics()
                .stream()
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
        return translationProvider.translate(lyrcsLine, "pt");
    }

    private Track getTrackFromDb(String nameMusic, List<String> artist) {
        return null;
    }
}
