package pl.szotaa.repperybackend.supermemo.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.domain.Group;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.exception.GroupNotFoundException;
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository repository;
    private final GroupService groupService;

    public List<Flashcard> findForRepetiton(int limit){
        return repository
                .findAllWithNextDueDateBeforeOrEqualCurrentDate()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void processAnswer(long id, AnswerQuality answerQuality) throws FlashcardNotFoundException {
        Flashcard flashcard = repository.findById(id).orElseThrow(FlashcardNotFoundException::new);
        flashcard.updateByAnswer(answerQuality);
        repository.save(flashcard);
    }

    public Flashcard findById(long id) throws FlashcardNotFoundException {
        return repository.findById(id).orElseThrow(FlashcardNotFoundException::new);
    }

    public void add(Flashcard flashcard, long groupId) throws GroupNotFoundException {
        Group group = groupService.getById(groupId);
        group.getFlashcards().add(flashcard);
        repository.save(flashcard);
    }

    public void update(Flashcard flashcard) throws FlashcardNotFoundException {
        if(repository.existsById(flashcard.getId())){
            repository.save(flashcard);
        } else {
            throw new FlashcardNotFoundException();
        }
    }

    public void delete(long id) throws FlashcardNotFoundException {
        if(repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new FlashcardNotFoundException();
        }
    }
}
