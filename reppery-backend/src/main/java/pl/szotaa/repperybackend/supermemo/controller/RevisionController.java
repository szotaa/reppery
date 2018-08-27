package pl.szotaa.repperybackend.supermemo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.service.RevisionService;

@RestController
@RequestMapping("/api/revise")
@RequiredArgsConstructor
public class RevisionController {

    private final RevisionService revisionService;

    @GetMapping("/{deckId}")
    @PreAuthorize("isAuthenticated() and @deckSecurityExpressions.isDeckOwner(#deckId, authentication)")
    public ResponseEntity<List<Flashcard>> getFlashcardsForRevision(@PathVariable long deckId){
        return ResponseEntity.ok(revisionService.getForRevision(deckId));
    }

    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @flashcardSecurityExpressions.isFlashcardOwner(#id, authentication)")
    public ResponseEntity<Void> processAnswer(@PathVariable Long id, @RequestBody AnswerQuality answerQuality)
            throws FlashcardNotFoundException {
        revisionService.processAnswer(id, answerQuality);
        return ResponseEntity.ok().build();
    }
}
