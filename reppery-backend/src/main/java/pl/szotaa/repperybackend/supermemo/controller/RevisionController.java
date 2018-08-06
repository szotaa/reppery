package pl.szotaa.repperybackend.supermemo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.service.FlashcardService;

@RestController
@RequestMapping("/api/revise")
@RequiredArgsConstructor
public class RevisionController {

    private final FlashcardService flashcardService;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<Flashcard>> getFlashcardsForRevision(@PathVariable long groupId){
        return ResponseEntity.ok(flashcardService.getForRevision(groupId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> processAnswer(@PathVariable Long id, @RequestBody AnswerQuality answerQuality)
            throws FlashcardNotFoundException {
        flashcardService.processAnswer(id, answerQuality);
        return ResponseEntity.ok().build();
    }
}
