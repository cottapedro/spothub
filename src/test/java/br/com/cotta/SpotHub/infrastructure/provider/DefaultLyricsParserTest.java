package br.com.cotta.SpotHub.infrastructure.provider;

import br.com.cotta.SpotHub.domain.port.TranslationProvider;
import dev.spoocy.genius.data.Lyrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultLyricsParserTest {

    @Mock
    private TranslationProvider translationProvider;

    @InjectMocks
    private DefaultLyricsParser parser;


    @Test
    void shouldParseLyricsIntoLines() {
        Lyrics lyrics = mock(Lyrics.class);
        when(lyrics.getAsPlain()).thenReturn("line1\n\nline2\n\nline3");

        List<String> lines = parser.parse(lyrics);

        assertEquals(List.of("line1", "line2", "line3"), lines);
    }

    @Test
    void shouldReturnEmptyListWhenLyricsIsNull() {
        Lyrics lyrics = mock(Lyrics.class);
        when(lyrics.getAsPlain()).thenReturn(null);

        List<String> lines = parser.parse(lyrics);

        assertTrue(lines.isEmpty());
    }

    @Test
    void shouldTranslateLine() {
        when(translationProvider.translate("hello", "pt")).thenReturn("olá");

        String translated = parser.translate("hello", "pt");

        assertEquals("olá", translated);
    }
}