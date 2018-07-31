package pl.szotaa.repperybackend.supermemo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.service.FlashcardService;

@RestController
@RequestMapping("/api/repetition")
@RequiredArgsConstructor
public class RepetitionController {

    private final FlashcardService flashcardService;

    @GetMapping
    public ResponseEntity<List<Flashcard>> getTodaysRepetition(@RequestParam int limit){
        return ResponseEntity.ok(flashcardService.findFlashcardsForRepetiton(limit));
    }
}
