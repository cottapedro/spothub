package br.com.cotta.SpotHub.interfaces.controller;


import br.com.cotta.SpotHub.application.service.PlaybackLyricsService;
import br.com.cotta.SpotHub.interfaces.dto.out.PlaybackLyrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lyrics")
public class LyricsController {

    @Autowired
    private PlaybackLyricsService playbackLyricsService;

    @GetMapping
    public ResponseEntity<PlaybackLyrics> getLyrics(){
        PlaybackLyrics playbackLyrics = playbackLyricsService.getPlaybackLyrics();
        return ResponseEntity.ok(playbackLyrics);
    }
}
