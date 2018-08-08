package pl.szotaa.repperybackend.supermemo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.szotaa.repperybackend.supermemo.domain.Deck;
import pl.szotaa.repperybackend.user.domain.User;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> getAllByOwner(User user);

    @Query(value = "SELECT COUNT(*) " +
            "FROM flashcards " +
            "WHERE deck_id = :deckId AND " +
            "next_due_date <= current_date",
            nativeQuery = true)
    Integer countDue(@Param("deckId") long deckId);
}
