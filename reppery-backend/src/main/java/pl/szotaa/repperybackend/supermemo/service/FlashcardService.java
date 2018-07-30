package pl.szotaa.repperybackend.supermemo.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
