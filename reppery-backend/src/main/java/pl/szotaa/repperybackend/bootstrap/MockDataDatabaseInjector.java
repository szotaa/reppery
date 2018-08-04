package pl.szotaa.repperybackend.bootstrap;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.user.domain.Role;
import pl.szotaa.repperybackend.user.domain.User;

@Component
@Profile("dev")
@Transactional
public class MockDataDatabaseInjector implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        return Arrays.asList(
                user,
                admin,
                notYetVerified
        );
    }
}
