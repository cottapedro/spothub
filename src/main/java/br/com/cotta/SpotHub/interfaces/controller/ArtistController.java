package br.com.cotta.SpotHub.interfaces.controller;

import br.com.cotta.SpotHub.domain.spotify.Artist;
import br.com.cotta.SpotHub.application.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable String id){
        Artist artist = artistService.getArtists(id);
        return ResponseEntity.ok(artist);
    }
}
