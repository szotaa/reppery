package pl.szotaa.repperybackend.supermemo.service;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.szotaa.repperybackend.supermemo.domain.Deck;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.exception.DeckNotFoundException;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository;

@Service
@Validated
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final DeckService deckService;

    public Flashcard findById(long id) throws FlashcardNotFoundException {
        return flashcardRepository.findById(id).orElseThrow(FlashcardNotFoundException::new);
    }

    public void add(@Valid Flashcard flashcard, long groupId) throws DeckNotFoundException {
        Deck deck = deckService.getById(groupId);
        deck.getFlashcards().add(flashcard);
        flashcardRepository.save(flashcard);
    }

    public void update(long id, @Valid Flashcard flashcard) throws FlashcardNotFoundException {
        if(flashcardRepository.existsById(id)){
            flashcard.setId(id);
            flashcardRepository.save(flashcard);
        } else {
            throw new FlashcardNotFoundException();
        }
    }

    public void delete(long id) throws FlashcardNotFoundException {
        if(flashcardRepository.existsById(id)){
            flashcardRepository.deleteById(id);
        } else {
            throw new FlashcardNotFoundException();
        }
    }
}
