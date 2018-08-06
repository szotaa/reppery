package pl.szotaa.repperybackend.supermemo.service

import pl.szotaa.repperybackend.supermemo.domain.Deck
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository
import spock.lang.Specification

class FlashcardServiceTest extends Specification {

    def flashcardRepository = Mock(FlashcardRepository)
    def deckService = Mock(DeckService)
    def flashcardService = new FlashcardService(flashcardRepository, deckService)

    def "FindById with existent id should return flashcard"(){
        given:
            def id = 5L
            flashcardRepository.findById(_) >> Optional.of(new Flashcard())
        when:
            def result = flashcardService.findById(id)
        then:
            result != null
            result instanceof Flashcard
    }

    def "Add should save flashcard"(){
        given:
            def flashcard = new Flashcard()
            def groupId = 5L
            deckService.getById(_) >> new Deck()
        when:
            flashcardService.add(flashcard, groupId)
        then:
            1 * flashcardRepository.save(_ as Flashcard)
    }

    def "Update with existent id should update flashcard"(){
        given:
            def id = 5L
            def flashcard = new Flashcard()
            flashcardRepository.existsById(_) >> true
        when:
            flashcardService.update(id, flashcard)
        then:
            1 * flashcardRepository.save(_ as Flashcard) >> {
                it.id == id
            }
    }

    def "Delete should delete flashcard"(){
        given:
            def id = 5L
            flashcardRepository.existsById(_) >> true
        when:
            flashcardService.delete(id)
        then:
            1 * flashcardRepository.deleteById(_)
    }
}
