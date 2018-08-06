package pl.szotaa.repperybackend.supermemo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.supermemo.domain.Deck;
import pl.szotaa.repperybackend.supermemo.service.DeckService;

@RestController
@RequestMapping("/api/deck")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @GetMapping
    public ResponseEntity<List<Deck>> getAllGroupsOwnedByCurrentUser(){
        return ResponseEntity.ok(deckService.getAllGroupsOwnedByCurrentUser());
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody Deck deck){
        this.deckService.add(deck);
        return ResponseEntity.ok().build();
    }
}
