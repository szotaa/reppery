package pl.szotaa.repperybackend.common;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;

@Component
@Profile("dev")
@Transactional
public class DatabasePopulater implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.getMockFlashcards().forEach(entityManager::persist);
    }

    private List<Flashcard> getMockFlashcards(){
        Flashcard yesterdaysFlashcard = Flashcard.builder()
                .title("yesterdaysFlashcard")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now().minusDays(1))
                .build();

        Flashcard todaysFlashcard = Flashcard.builder()
                .title("todaysFlashcard")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now())
                .build();

        Flashcard tommorowsFlashcard = Flashcard.builder()
                .title("tommorowsFlashcard")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now().plusDays(1))
                .build();

        return Arrays.asList(
                yesterdaysFlashcard,
                todaysFlashcard,
                tommorowsFlashcard);
    }
}
