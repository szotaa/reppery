package pl.szotaa.repperybackend.supermemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.exception.DeckNotFoundException;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.service.FlashcardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flashcard")
public class FlashcardController {

    private final FlashcardService flashcardService;

    @PostMapping("/{deckId}")
    @PreAuthorize("isAuthenticated() and @deckSecurityExpressions.isDeckOwner(#deckId, authentication)")
    public ResponseEntity<Void> add(@RequestBody Flashcard flashcard, @PathVariable long deckId)
            throws DeckNotFoundException {
        this.flashcardService.add(flashcard, deckId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @flashcardSecurityExpressions.isFlashcardOwner(#id, authentication)")
    public ResponseEntity<Flashcard> get(@PathVariable long id) throws FlashcardNotFoundException {
        return ResponseEntity.ok(this.flashcardService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @flashcardSecurityExpressions.isFlashcardOwner(#id, authentication)")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Flashcard flashcard) throws FlashcardNotFoundException {
        this.flashcardService.update(id, flashcard);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @flashcardSecurityExpressions.isFlashcardOwner(#id, authentication)")
    public ResponseEntity<Void> delete(@PathVariable long id) throws FlashcardNotFoundException {
        this.flashcardService.delete(id);
        return ResponseEntity.ok().build();
    }
}
