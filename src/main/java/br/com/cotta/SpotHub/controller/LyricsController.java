package br.com.cotta.SpotHub.controller;


import br.com.cotta.SpotHub.dto.out.PlaybackLyrics;
import br.com.cotta.SpotHub.service.LyricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lyrics")
public class LyricsController {

    @Autowired
    private LyricService lyricService;

    @GetMapping
    public ResponseEntity<PlaybackLyrics> getLyrics(){
        PlaybackLyrics playbackLyrics = lyricService.getPlaybackLyrics();
        return ResponseEntity.ok(playbackLyrics);
    }
}
