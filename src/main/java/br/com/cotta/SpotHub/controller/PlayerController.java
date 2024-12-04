package br.com.cotta.SpotHub.controller;


import br.com.cotta.SpotHub.domain.PlayBackState;
import br.com.cotta.SpotHub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<PlayBackState> getCurrentPlayBackState(){
        PlayBackState playBackState = playerService.getCurrentPlayBackStates();
        return ResponseEntity.ok(playBackState);
    }
}
