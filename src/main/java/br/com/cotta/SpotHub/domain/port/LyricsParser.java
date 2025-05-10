package br.com.cotta.SpotHub.domain.port;

import dev.spoocy.genius.data.Lyrics;

import java.util.List;

public interface LyricsParser {
    List<String> parse(Lyrics lyrics);
    String translate(String line, String targetLanguage);
}
