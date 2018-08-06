package pl.szotaa.repperybackend.supermemo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.szotaa.repperybackend.supermemo.domain.Deck;
import pl.szotaa.repperybackend.user.domain.User;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> getAllByOwner(User user);
}
