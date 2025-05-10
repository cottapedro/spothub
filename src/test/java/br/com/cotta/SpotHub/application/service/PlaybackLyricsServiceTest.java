package br.com.cotta.SpotHub.application.service;

import br.com.cotta.SpotHub.domain.model.Track;
import br.com.cotta.SpotHub.domain.spotify.PlayBackState;
import br.com.cotta.SpotHub.interfaces.dto.out.PlaybackLyrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaybackLyricsServiceTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private TrackService trackService;

    @Mock
    private PlaybackLyricsMapper playbackLyricsMapper;

    @InjectMocks
    private PlaybackLyricsService playbackLyricsService;

    @Test
    void shouldReturnPlaybackLyricsSuccessfully() {
        // Arrange
        PlayBackState mockState = mock(PlayBackState.class);
        Track mockTrack = mock(Track.class);
        PlaybackLyrics expectedLyrics = new PlaybackLyrics();

        when(playerService.getCurrentPlayBackStates()).thenReturn(mockState);
        when(trackService.resolveTrack(mockState)).thenReturn(mockTrack);
        when(playbackLyricsMapper.mapFromTrack(mockTrack)).thenReturn(expectedLyrics);

        // Act
        PlaybackLyrics result = playbackLyricsService.getPlaybackLyrics();

        // Assert
        assertNotNull(result);
        assertEquals(expectedLyrics, result);
        verify(playerService).getCurrentPlayBackStates();
        verify(trackService).resolveTrack(mockState);
        verify(playbackLyricsMapper).mapFromTrack(mockTrack);
    }

    @Test
    void shouldThrowException_WhenPlayBackStateIsNull() {
        // Arrange
        when(playerService.getCurrentPlayBackStates()).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> playbackLyricsService.getPlaybackLyrics());

        verify(playerService).getCurrentPlayBackStates();
        verifyNoInteractions(trackService, playbackLyricsMapper);
    }

}
