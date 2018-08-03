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
import pl.szotaa.repperybackend.user.domain.Role;
import pl.szotaa.repperybackend.user.domain.User;

@Component
@Profile("dev")
@Transactional
public class DatabasePopulater implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.getMockFlashcards().forEach(entityManager::persist);
        this.getMockUsers().forEach(entityManager::persist);
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

    private List<User> getMockUsers(){
        User user = User.builder()
                .email("user@email.com")
                .password("password")
                .isEnabled(true)
                .emailActivationToken(null)
                .build();

        User admin = User.builder()
                .email("admin@email.com")
                .password("password")
                .role(Role.ROLE_ADMIN)
                .isEnabled(true)
                .emailActivationToken(null)
                .build();

        User notYetVerified = User.builder()
                .email("notVerified@email.com")
                .password("password")
                .build();

        return Arrays.asList(
                user,
                admin,
                notYetVerified
        )
    }
}
