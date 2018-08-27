package pl.szotaa.repperybackend.supermemo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query(value = "SELECT * FROM flashcards WHERE " +
            "deck_id = :deckId AND " +
            "next_due_date <= current_date",
           nativeQuery = true)
    List<Flashcard> findAllToRevise(@Param("deckId") long deckId);

    @Query(value = "SELECT deck_id FROM flashcards WHERE id = :id",
           nativeQuery = true)
    Long getDeckId(@Param("id") long id);
}
