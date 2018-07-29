package pl.szotaa.repperybackend.supermemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}
