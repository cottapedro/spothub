package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.domain.model.Lyrics;
import br.com.cotta.SpotHub.domain.model.Track;
import br.com.cotta.SpotHub.domain.port.LyricsParser;
import br.com.cotta.SpotHub.domain.port.LyricsProvider;
import br.com.cotta.SpotHub.domain.spotify.Artist;
import br.com.cotta.SpotHub.domain.spotify.PlayBackState;
import br.com.cotta.SpotHub.infrastructure.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackService {
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private LyricsProvider lyricsProvider;

    @Autowired
    private ArtistService artistService;
    @Autowired
    private LyricsParser lyricsParser;

    public Track resolveTrack(PlayBackState state){
        String trackName = state.getItem().getName();
        List<String> artistNames = extractArtistNames(state);

        return trackRepository.findByNameAndArtistNames(trackName, artistNames, artistNames.size())
                .orElseGet(() -> buildAndSaveTrack(state, trackName, artistNames));
    }

    private Track buildAndSaveTrack(PlayBackState state, String trackName, List<String> artistNames) {
        var lyrics = lyricsProvider.findLyricsByArtistAndSong(trackName, artistNames);
        if ( state.getItem() == null || lyrics == null)
            throw new IllegalStateException("Falha ao construir Track.");

        var item = state.getItem();
        var lines = lyricsParser.parse(lyrics);

        Track track = new Track();
        track.setName(item.getName());
        track.setAlbum(item.getAlbum().getName());
        track.setDuration(item.getDurationMs());
        track.setArtists(artistService.findOrCreateAll(state));
        track.setLyrics(buildLyricsList(lines, track));

        return trackRepository.save(track);
    }

    private List<String> extractArtistNames(PlayBackState state) {
        return state.getItem().getArtists().stream()
                .map(Artist::getName)
                .toList();
    }

    private List<Lyrics> buildLyricsList(List<String> lines, Track track) {
        return lines.stream()
                .map(line -> new Lyrics(track,"en" , line, getTranslateLyricsLine(line), 0))
                .toList();
    }

    private String getTranslateLyricsLine(String lyricsLine) {
        return lyricsParser.translate(lyricsLine, "pt");
    }
}

