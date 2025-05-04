package br.com.cotta.SpotHub.domain.port;

import dev.spoocy.genius.data.Lyrics;

import java.util.List;

public interface LyricsProvider {
    Lyrics findLyricsByArtistAndSong(String musicName, List<String> artists);
}
