package pl.szotaa.repperybackend.supermemo.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository repository;

    public List<Flashcard> findFlashcardsForRepetiton(int limit){
        return repository
                .findAllWithNextDueDateEqualToToday()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void processAnswer(Long id, AnswerQuality answerQuality){
        Flashcard flashcard = repository.findById(id).orElseThrow(RuntimeException::new); //TODO: dedicated exception
        flashcard.updateByAnswer(answerQuality);
        repository.save(flashcard);
    }
}
