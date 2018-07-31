package pl.szotaa.repperybackend.supermemo.domain

import spock.lang.Specification

class AnswerQualityTest extends Specification {

    def "GetValue should return correct enum Strings"(int value, AnswerQuality expected){
        when:
            AnswerQuality actual= AnswerQuality.getFromValue(value)
        then:
            actual == expected
        where:
            value | expected
            0 | AnswerQuality.BLACKOUT
            1 | AnswerQuality.INCORRECT
            2 | AnswerQuality.ALMOST
            3 | AnswerQuality.DIFFICULT
            4 | AnswerQuality.HESITATED
            5 | AnswerQuality.PERFECT
    }

    def ""
}
