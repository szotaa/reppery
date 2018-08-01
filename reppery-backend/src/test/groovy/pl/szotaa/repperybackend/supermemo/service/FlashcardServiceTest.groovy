package pl.szotaa.repperybackend.supermemo.service

import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.exception.FlashcardNotFoundException
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository
import spock.lang.Ignore
import spock.lang.Specification

class FlashcardServiceTest extends Specification {

    def flashcardRepository = Mock(FlashcardRepository)
    def flashcardService = new FlashcardService(flashcardRepository)

    def "FindFlashcardsForRepetition should return a limited list of flashcards fetched from FlashcardRepository"(){
        given:
            def flashcards = [new Flashcard(), new Flashcard(), new Flashcard(), new Flashcard()]
            def limit = 2
            flashcardRepository.findAllWithNextDueDateEqualToToday() >> flashcards
        when:
            def result = flashcardService.findForRepetiton(limit)
        then:
            result.size() == 2
            result.get(0) instanceof Flashcard
    }

    def "ProcessAnswer saves updated entity"(){
        given:
            def id = 1
            def answerQuality = AnswerQuality.PERFECT
            def foundFlashcard = Flashcard.builder().repetitions(5).build()
            flashcardRepository.findById(_) >> {Optional.of(foundFlashcard)}
        when:
            flashcardService.processAnswer(id, answerQuality)
        then:
            1 * flashcardRepository.save(_ as Flashcard) >> {it.repetitions != foundFlashcard.repetitions}
    }

    def "ProcessAnswer with non existent id throws exception"(){
        given:
            def id = 5
            def answerQuality = AnswerQuality.PERFECT
            flashcardRepository.findById(_) >> {Optional.empty()}
        when:
            flashcardService.processAnswer(id, answerQuality)
        then:
            thrown(FlashcardNotFoundException)
    }

    @Ignore
    def "FindById with existent id returns Flashcard object"(){
        given:
            def id = 5
            def flashcard = new Flashcard()
            flashcardRepository.findById(_ as long) >> Optional.of(flashcard)
        when:
            def found = flashcardService.findById(id)
        then:
            1 * flashcardRepository.findById(_ as long)
            found != null
            found instanceof Flashcard
    }

    @Ignore
    def "FindById with non existent id throws exception"(){
        given:
            def id = 5
            flashcardRepository.findById(_) >> {Optional.empty()}
        when:
            flashcardService.findById(id)
        then:
            1 * flashcardRepository.findById(_)
            thrown(FlashcardNotFoundException)
    }

    def "Add saves Flashcard object to repository"(){
        given:
            def flashcard = new Flashcard()
        when:
            flashcardService.add(flashcard)
        then:
            1 * flashcardRepository.save(_)
    }

    @Ignore
    def "Update with existent id saves to repository"(){
        given:
            def id = 5
            def flashcard = Flashcard.builder().id(id).build()
            flashcardRepository.existsById(_) >> {true}
        when:
            flashcardService.update(flashcard)
        then:
            1 * flashcardRepository.existsById(_)
            1 * flashcardRepository.save(_)
    }

    def "Update with non existent id throws exception"(){
        given:
            def id = 5
            def flashcard = Flashcard.builder().id(id).build()
            flashcardRepository.existsById(_) >> {false}
        when:
            flashcardService.update(flashcard)
        then:
            thrown(FlashcardNotFoundException)
    }

    @Ignore
    def "Delete with existent id deletes from repository"(){
        given:
            def id = 5
            flashcardRepository.existsById(_) >> {true}
        when:
            flashcardService.delete(id)
        then:
            1 * flashcardRepository.existsById(_)
            1 * flashcardRepository.deleteById(_)
    }

    def "Delete with non existent id throws exception"(){
        given:
            def id = 5
            flashcardRepository.existsById(_) >> {false}
        when:
            flashcardService.delete(id)
        then:
            thrown(FlashcardNotFoundException)
    }
}
