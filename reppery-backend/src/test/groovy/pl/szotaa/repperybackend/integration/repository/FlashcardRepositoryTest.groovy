package pl.szotaa.repperybackend.integration.repository

import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import pl.szotaa.repperybackend.supermemo.domain.Deck
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository
import pl.szotaa.repperybackend.user.domain.User
import spock.lang.Specification

import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FlashcardRepositoryTest extends Specification {

    @Autowired
    private TestEntityManager testEntityManager

    @Autowired
    private FlashcardRepository repository

    def setup(){
        def flashcard1 = Flashcard.builder()
                .title("flashcard1")
                .front("front")
                .back("back")
                .repetitions(5)
                .interval(5)
                .easiness(2.5)
                .nextDueDate(LocalDate.now())
                .build()

        def flashcard2 = Flashcard.builder()
                .title("flashcard2")
                .front("front")
                .back("back")
                .repetitions(5)
                .interval(5)
                .easiness(2.5)
                .nextDueDate(LocalDate.now().plusDays(1))
                .build()

        def flashcard3 = Flashcard.builder()
                .title("flashcard3")
                .front("front")
                .back("back")
                .repetitions(5)
                .interval(5)
                .easiness(2.5)
                .nextDueDate(LocalDate.now().plusDays(2))
                .build()

        flashcard1.setId(1L)
        flashcard2.setId(2L)
        flashcard3.setId(3L)

        testEntityManager.merge(flashcard1)
        testEntityManager.merge(flashcard2)
        testEntityManager.merge(flashcard3)

        def owner = User.builder()
                .email("user@email.com")
                .password("password")
                .build()

        testEntityManager.persist(owner)

        def deck = Deck.builder()
                .title("title")
                .owner(owner)
                .build()

        deck.setFlashcards(new HashSet<Flashcard>(
                Arrays.asList(
                        flashcard1,
                        flashcard2,
                        flashcard3
                )
        ))

        deck.setId(5L)

        testEntityManager.merge(deck)
        testEntityManager.flush()
    }

    @Ignore
    def "FindAllToRevise method should return all flashcards with nextDueDate equal to today's date"(){
        given:
            def deckId = 5L
        when:
            def flashcardsList = repository.findAllToRevise(deckId)
        then:
            flashcardsList.size() == 1
            flashcardsList.get(0).title == "flashcard1"
    }
}
