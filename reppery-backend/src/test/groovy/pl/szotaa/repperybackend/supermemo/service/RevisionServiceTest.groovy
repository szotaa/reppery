package pl.szotaa.repperybackend.supermemo.service

import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository
import spock.lang.Specification

class RevisionServiceTest extends Specification {

    def flashcardRepository = Mock(FlashcardRepository)
    def revisionService = new RevisionService(flashcardRepository)

    def "GetForRevision should return a list of flashcards"(){
        given:
            def id = 5
            flashcardRepository.findAllToRevise(_) >> [new Flashcard(), new Flashcard()]
        when:
            def result = revisionService.getForRevision(id)
        then:
            result.size() == 2
            result instanceof List<Flashcard>
    }

    def "ProcessAnswer should update and save flashcard"(){
        given:
            def id = 5
            def answerQuality = AnswerQuality.PERFECT
            flashcardRepository.findById(_) >> Optional.of(new Flashcard())
        when:
            revisionService.processAnswer(5, answerQuality)
        then:
            1 * flashcardRepository.save(_)
    }
}
