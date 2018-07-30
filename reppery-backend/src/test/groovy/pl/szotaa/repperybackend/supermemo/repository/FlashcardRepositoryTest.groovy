package pl.szotaa.repperybackend.supermemo.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
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

        def flashcard4 = Flashcard.builder()
                .title("flashcard4")
                .front("front")
                .back("back")
                .repetitions(5)
                .interval(5)
                .easiness(2.5)
                .nextDueDate(LocalDate.now().plusDays(3))
                .build()

        def flashcard5 = Flashcard.builder()
                .title("flashcard5")
                .front("front")
                .back("back")
                .repetitions(5)
                .interval(5)
                .easiness(2.5)
                .nextDueDate(LocalDate.now().plusDays(4))
                .build()

        testEntityManager.persist(flashcard1)
        testEntityManager.persist(flashcard2)
        testEntityManager.persist(flashcard3)
        testEntityManager.persist(flashcard4)
        testEntityManager.persist(flashcard5)
        testEntityManager.flush()
    }

    def "FindAllByOrderByNextDueDateDesc method should return all flashcards ordered descending by date"(){
        when:
            def flashcardsList = repository.findAllByOrderByNextDueDateDesc()
        then:
            flashcardsList.size() == 5
            flashcardsList.get(0).title == "flashcard5"
            flashcardsList.get(1).title == "flashcard4"
            flashcardsList.get(2).title == "flashcard3"
            flashcardsList.get(3).title == "flashcard2"
            flashcardsList.get(4).title == "flashcard1"
    }
}
