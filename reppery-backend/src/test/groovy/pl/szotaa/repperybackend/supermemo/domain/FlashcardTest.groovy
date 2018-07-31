package pl.szotaa.repperybackend.supermemo.domain

import spock.lang.Specification

import java.time.LocalDate

class FlashcardTest extends Specification {

    def "Easiness should decrease with incorrect answers"(AnswerQuality answerQuality){
        given:
            def defaultEasiness = 2.5
            def flashcard = new Flashcard()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.easiness < defaultEasiness
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.ALMOST|_
    }

    def "Easiness should increase with correct answers"(AnswerQuality answerQuality){
        given:
            def exampleEasiness = 2.0
            def flashcard = Flashcard.builder().easiness(exampleEasiness).build()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.easiness > exampleEasiness
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.HESITATED|_
            AnswerQuality.DIFFICULT|_
    }

    def "Easiness variable should never exceed 2.5"(){
        given:
            def flashcard = new Flashcard()
        when:
            flashcard.updateByAnswer(AnswerQuality.PERFECT)
        then:
            flashcard.easiness == 2.5
    }

    def "Easiness variable should never be lower than 1.3"(){
        given:
            def lowestPossibleEasiness = 1.3
            def flashcard = Flashcard.builder().easiness(lowestPossibleEasiness).build()
        when:
            flashcard.updateByAnswer(AnswerQuality.BLACKOUT)
        then:
            flashcard.easiness == 1.3
    }

    def "Repetitions should be set to 0 on incorrect answer"(AnswerQuality answerQuality){
        given:
            def flashcard = new Flashcard()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.repetitions == 0
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.ALMOST|_
    }

    def "Repetitions should increase on correct answer"(AnswerQuality answerQuality){
        given:
            def flashcard = new Flashcard()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.repetitions > 0
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.HESITATED|_
            AnswerQuality.DIFFICULT|_
    }

    def "Interval should increase on correct answers"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.interval > 1
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.HESITATED|_
            AnswerQuality.DIFFICULT|_
    }

    def "Interval should be set to 1 on incorrect answer"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.interval == 1
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.ALMOST|_
    }

    def "NextDueDate should be tomorrow on incorrect answer"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.nextDueDate == LocalDate.now().plusDays(1)
        where:
            answerQuality|_
            AnswerQuality.BLACKOUT|_
            AnswerQuality.INCORRECT|_
            AnswerQuality.ALMOST|_
    }

    def "NextDueDate should be later than tomorrow on correct answer"(AnswerQuality answerQuality){
        given:
            def flashcard = Flashcard.builder().repetitions(5).build()
        when:
            flashcard.updateByAnswer(answerQuality)
        then:
            flashcard.nextDueDate > LocalDate.now().plusDays(1)
        where:
            answerQuality|_
            AnswerQuality.PERFECT|_
            AnswerQuality.HESITATED|_
            AnswerQuality.DIFFICULT|_
    }
}
