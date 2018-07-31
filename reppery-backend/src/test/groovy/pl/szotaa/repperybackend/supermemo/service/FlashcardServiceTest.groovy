package pl.szotaa.repperybackend.supermemo.service

import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository
import spock.lang.Specification

class FlashcardServiceTest extends Specification {

    def flashcardRepository = Mock(FlashcardRepository)
    def flashcardService = new FlashcardService(flashcardRepository)

    def "FindFlashcardsForRepetition should return a limited list of flashcards fetched from FlashcardRepository"(){
        given:
            def flashcards = [new Flashcard(), new Flashcard(), new Flashcard(), new Flashcard()]
            def limit = 2
            1 * flashcardRepository.findAllWithNextDueDateEqualToToday() >> flashcards
        when:
            def result = flashcardService.findFlashcardsForRepetiton(limit)
        then:
            result.size() == 2
            result.get(0) instanceof Flashcard
    }

    def "ProcessAnswer saves updated entity"(){
        given:
            def id = 1
            def answerQuality = AnswerQuality.PERFECT
            def foundFlashcard = Flashcard.builder().repetitions(5).build()
            1 * flashcardRepository.findById(_) >> {Optional.of(foundFlashcard)}
        when:
            flashcardService.processAnswer(id, answerQuality)
        then:
            1 * flashcardRepository.save(_) >> {it.repetitions != foundFlashcard.repetitions}
    }

    def "ProcessAnswer with nonexistent id throw exception"(){
        given:
            def id = 5
            def answerQuality = AnswerQuality.PERFECT
            1 * flashcardRepository.findById(_) >> {Optional.empty()}
        when:
            flashcardService.processAnswer(id, answerQuality)
        then:
            thrown(RuntimeException)
    }
}
