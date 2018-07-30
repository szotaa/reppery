package pl.szotaa.repperybackend.supermemo.service

import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import spock.lang.Specification

import java.time.LocalDate

class RepetitionServiceTest extends Specification {

    def reviewService = new RepetitionService()

    def "Easiness should decrease with incorrect answers"(AnswerQuality answerQuality){
        given:
            def defaultEasiness = 2.5
            def flashcard = new Flashcard()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.easiness < defaultEasiness
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.INCORRECT_ALMOST|_
    }

    def "Easiness should increase with correct answers"(AnswerQuality answerQuality){
        given:
            def exampleEasiness = 2.0
            def flashcard = Flashcard.builder().easiness(exampleEasiness).build()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.easiness > exampleEasiness
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.CORRECT_HESITATION|_
            AnswerQuality.CORRECT_DIFFICLTY|_
    }

    def "Easiness variable should never exceed 2.5"(){
        given:
            def flashcard = new Flashcard()
        when:
            def result = reviewService.processAnswer(flashcard, AnswerQuality.PERFECT)
        then:
            result.easiness == 2.5
    }

    def "Easiness variable should never be lower than 1.3"(){
        given:
            def lowestPossibleEasiness = 1.3
            def flashcard = Flashcard.builder().easiness(lowestPossibleEasiness).build()
        when:
            def result = reviewService.processAnswer(flashcard, AnswerQuality.BLACKOUT)
        then:
            result.easiness == 1.3
    }

    def "Repetitions should be set to 0 on incorrect answer"(AnswerQuality answerQuality){
        given:
            def flashcard = new Flashcard()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.repetitions == 0
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.INCORRECT_ALMOST|_
    }

    def "Repetitions should increase on correct answer"(AnswerQuality answerQuality){
        given:
            def flashcard = new Flashcard()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.repetitions > 0
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.CORRECT_HESITATION|_
            AnswerQuality.CORRECT_DIFFICLTY|_
    }

    def "Interval should increase on correct answers"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.interval > 1
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.CORRECT_HESITATION|_
            AnswerQuality.CORRECT_DIFFICLTY|_
    }

    def "Interval should be set to 1 on incorrect answer"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.interval == 1
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.INCORRECT_ALMOST|_
    }

    def "NextDueDate should be tomorrow on incorrect answer"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.nextDueDate == LocalDate.now().plusDays(1)
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.INCORRECT_ALMOST|_
    }

    def "NextDueDate should be later than tomorrow on correct answer"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            def result = reviewService.processAnswer(flashcard, answerQuality)
        then:
            result.nextDueDate > LocalDate.now().plusDays(1)
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.CORRECT_HESITATION|_
            AnswerQuality.CORRECT_DIFFICLTY|_
    }
}
