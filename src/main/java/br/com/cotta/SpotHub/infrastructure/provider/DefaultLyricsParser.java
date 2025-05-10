package br.com.cotta.SpotHub.infrastructure.provider;

import br.com.cotta.SpotHub.domain.port.LyricsParser;
import br.com.cotta.SpotHub.domain.port.TranslationProvider;
import dev.spoocy.genius.data.Lyrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DefaultLyricsParser implements LyricsParser {
    @Autowired
    private TranslationProvider translationProvider;

    @Override
    public List<String> parse(Lyrics lyrics) {
        if (lyrics.getAsPlain() == null) return List.of();
        return Arrays.stream(lyrics.getAsPlain().split("\\n\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();
    }

    @Override
    public String translate(String line, String targetLanguage) {
        return translationProvider.translate(line, targetLanguage);
    }
}
