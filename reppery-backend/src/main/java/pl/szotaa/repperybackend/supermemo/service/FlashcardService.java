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

    public List<Flashcard> findForRepetiton(int limit){
        return repository
                .findAllWithNextDueDateEqualToToday()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void processAnswer(long id, AnswerQuality answerQuality){
        Flashcard flashcard = repository.findById(id).orElseThrow(RuntimeException::new); //TODO: dedicated exception
        flashcard.updateByAnswer(answerQuality);
        repository.save(flashcard);
    }

    public Flashcard findById(long id){
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void add(Flashcard flashcard){
        repository.save(flashcard);
    }

    public void update(Flashcard flashcard){
        if(repository.existsById(flashcard.getId())){
            repository.save(flashcard);
        } else {
            throw new RuntimeException();
        }
    }

    public void delete(long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }
}
