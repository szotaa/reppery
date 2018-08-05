package pl.szotaa.repperybackend.supermemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.service.FlashcardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flashcard")
public class FlashcardController {

    private final FlashcardService flashcardService;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody Flashcard flashcard){
        flashcardService.add(flashcard);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> get(@PathVariable long id) throws FlashcardNotFoundException {
        return ResponseEntity.ok(flashcardService.findById(id));
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Flashcard flashcard) throws FlashcardNotFoundException {
        flashcardService.update(flashcard);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws FlashcardNotFoundException {
        flashcardService.delete(id);
        return ResponseEntity.ok().build();
    }
}
