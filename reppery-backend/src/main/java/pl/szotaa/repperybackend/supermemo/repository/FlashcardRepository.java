package pl.szotaa.repperybackend.supermemo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query(value = "SELECT * FROM flashcards WHERE next_due_date <= current_date",
           nativeQuery = true)
    List<Flashcard> findAllWithNextDueDateBeforeOrEqualCurrentDate();
}
