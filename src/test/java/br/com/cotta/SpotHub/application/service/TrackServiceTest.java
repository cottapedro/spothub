package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.domain.model.Artists;
import br.com.cotta.SpotHub.domain.model.Track;
import br.com.cotta.SpotHub.domain.port.LyricsParser;
import br.com.cotta.SpotHub.domain.port.LyricsProvider;
import br.com.cotta.SpotHub.domain.spotify.Album;
import br.com.cotta.SpotHub.domain.spotify.Artist;
import br.com.cotta.SpotHub.domain.spotify.Item;
import br.com.cotta.SpotHub.domain.spotify.PlayBackState;
import br.com.cotta.SpotHub.infrastructure.repository.TrackRepository;
import dev.spoocy.genius.data.Lyrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;
    @Mock private LyricsProvider lyricsProvider;
    @Mock private ArtistService artistService;
    @Mock private LyricsParser lyricsParser;

    @InjectMocks
    private TrackService trackService;

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void shouldReturnTrackFromDatabase_WhenTrackExists() {
        // Arrange
        PlayBackState state = buildMockState("Song A", List.of("Artist A"));
        Track dbTrack = new Track();

        when(trackRepository.findByNameAndArtistNames("Song A", List.of("Artist A"), 1))
                .thenReturn(Optional.of(dbTrack));

        // Act
        Track result = trackService.resolveTrack(state);

        // Assert
        assertEquals(dbTrack, result);
        verify(trackRepository).findByNameAndArtistNames("Song A", List.of("Artist A"), 1);
        verifyNoMoreInteractions(trackRepository);
    }

    @Test
    void shouldBuildAndSaveTrack_WhenTrackNotInDatabase() {
        // Arrange
        PlayBackState state = buildMockState("Song B", List.of("Artist B"));
        Lyrics mockLyrics = mock(Lyrics.class);
        List<String> parsedLines = List.of("line 1", "line 2");
        Set<Artists> mockArtists = Set.of(new Artists("Artist B"));

        when(trackRepository.findByNameAndArtistNames("Song B", List.of("Artist B"), 1))
                .thenReturn(Optional.empty());
        when(lyricsProvider.findLyricsByArtistAndSong("Song B", List.of("Artist B")))
                .thenReturn(mockLyrics);
        when(lyricsParser.parse(mockLyrics)).thenReturn(parsedLines);
        when(artistService.findOrCreateAll(state)).thenReturn(mockArtists);
        when(lyricsParser.translate(any(), eq("pt"))).thenReturn("traducao");
        when(trackRepository.save(any(Track.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Track result = trackService.resolveTrack(state);

        // Assert
        assertEquals("Song B", result.getName());
        assertEquals(mockArtists, result.getArtists());
        assertEquals(2, result.getLyrics().size());
        verify(trackRepository).save(any());
    }

    private static PlayBackState buildMockState(String song, List<String> artists) {
        Item item = mock(Item.class);
        Album album = mock(Album.class);

        when(item.getName()).thenReturn(song);
        when(item.getDurationMs()).thenReturn(200000);
        when(item.getAlbum()).thenReturn(album);
        when(album.getName()).thenReturn("Album X");

        List<Artist> artistList = artists.stream().map(name -> {
            Artist a = mock(Artist.class);
            when(a.getName()).thenReturn(name);
            return a;
        }).toList();

        when(item.getArtists()).thenReturn(artistList);

        PlayBackState state = mock(PlayBackState.class);
        when(state.getItem()).thenReturn(item);
        return state;
    }
}