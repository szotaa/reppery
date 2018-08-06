package pl.szotaa.repperybackend.supermemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.domain.Group;
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException;
import pl.szotaa.repperybackend.supermemo.exception.GroupNotFoundException;
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final GroupService groupService;

    public Flashcard findById(long id) throws FlashcardNotFoundException {
        return flashcardRepository.findById(id).orElseThrow(FlashcardNotFoundException::new);
    }

    public void add(Flashcard flashcard, long groupId) throws GroupNotFoundException {
        Group group = groupService.getById(groupId);
        group.getFlashcards().add(flashcard);
        flashcardRepository.save(flashcard);
    }

    public void update(Flashcard flashcard) throws FlashcardNotFoundException {
        if(flashcardRepository.existsById(flashcard.getId())){
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
