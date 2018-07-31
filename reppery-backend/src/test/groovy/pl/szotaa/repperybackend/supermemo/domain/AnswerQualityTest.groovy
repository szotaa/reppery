package pl.szotaa.repperybackend.supermemo.domain

import spock.lang.Specification

class AnswerQualityTest extends Specification {

    def "GetValue should return correct enum strings"(int value, AnswerQuality expected){
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

    def "GetValue throws IllegalArgumentException for values other than from [0:5] range inclusive"(int value){
        when:
            AnswerQuality.getFromValue(value)
        then:
            thrown(IllegalArgumentException)
        where:
            value|_
            -1|_
             6|_
    }
}
