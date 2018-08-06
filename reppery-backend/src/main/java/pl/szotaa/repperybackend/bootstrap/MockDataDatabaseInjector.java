package pl.szotaa.repperybackend.bootstrap;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.common.entity.AbstractEntity;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.domain.Deck;
import pl.szotaa.repperybackend.user.domain.Role;
import pl.szotaa.repperybackend.user.domain.User;

@Component
@Profile("dev")
@Transactional
@RequiredArgsConstructor
public class MockDataDatabaseInjector implements ApplicationRunner {

    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.getMockData().forEach(entityManager::persist);
    }

    private List<AbstractEntity> getMockData(){
        Flashcard flashcard1 = Flashcard.builder()
                .title("yesterdaysFlashcard")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now().minusDays(1))
                .build();

        Flashcard flashcard2 = Flashcard.builder()
                .title("todaysFlashcard")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now())
                .build();

        Flashcard flashcard3 = Flashcard.builder()
                .title("tommorowsFlashcard")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now().plusDays(1))
                .build();

        Flashcard flashcard4 = Flashcard.builder()
                .title("flashcard4")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now().plusDays(1))
                .build();

        Flashcard flashcard5 = Flashcard.builder()
                .title("flashcard5")
                .front("front")
                .back("back")
                .repetitions(5)
                .nextDueDate(LocalDate.now().plusDays(1))
                .build();

        Set<Flashcard> flashcards = new HashSet<>(
                Arrays.asList(
                        flashcard1,
                        flashcard2,
                        flashcard3
                )
        );

        Set<Flashcard> adminFlashcards = new HashSet<>(
                Arrays.asList(
                        flashcard4,
                        flashcard5
                )
        );

        String encodedPassword = passwordEncoder.encode("password");

        User user = User.builder()
                .email("user@email.com")
                .password(encodedPassword)
                .isEnabled(true)
                .emailActivationToken(null)
                .build();

        User admin = User.builder()
                .email("admin@email.com")
                .password(encodedPassword)
                .role(Role.ROLE_ADMIN)
                .isEnabled(true)
                .emailActivationToken(null)
                .build();

        User notYetVerified = User.builder()
                .email("notVerified@email.com")
                .emailActivationToken("emailActivationToken")
                .password(encodedPassword)
                .build();

        Deck deck1 = Deck.builder()
                .title("flashcards")
                .flashcards(flashcards)
                .owner(user)
                .build();

        Deck deck2 = Deck.builder()
                .title("admin flashcards")
                .flashcards(adminFlashcards)
                .owner(admin)
                .build();


        return Arrays.asList(
                user,
                admin,
                notYetVerified,
                flashcard1,
                flashcard2,
                flashcard3,
                flashcard4,
                flashcard5,
                deck1,
                deck2
        );
    }
}
