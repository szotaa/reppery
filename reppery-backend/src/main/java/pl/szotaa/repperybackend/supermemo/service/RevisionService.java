package pl.szotaa.repperybackend.supermemo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository;

@Service
@RequiredArgsConstructor
public class RevisionService {

    private final FlashcardRepository flashcardRepository;

    public List<Flashcard> getForRevision(long deckId){
        return flashcardRepository.findAllToRevise(deckId);
    }

    public void processAnswer(long id, AnswerQuality answerQuality) throws FlashcardNotFoundException {
        Flashcard flashcard = flashcardRepository.findById(id).orElseThrow(FlashcardNotFoundException::new);
        flashcard.updateByAnswer(answerQuality);
        flashcardRepository.save(flashcard);
    }
}
