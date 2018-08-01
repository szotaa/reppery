package pl.szotaa.repperybackend.supermemo.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import spock.lang.Specification

@JsonTest
class AnswerQualityJsonTest extends Specification {

    @Autowired
    private JacksonTester<AnswerQuality> jacksonTester

    def "Should properly deserialize ordinal to AnswerQuality object"(int ordinal, AnswerQuality expected){
        given:
            def json = ordinal.toString()
        when:
            def object = jacksonTester.parse(json).getObject()
        then:
            object instanceof AnswerQuality
            object == expected
        where:
            ordinal|expected
            0|AnswerQuality.BLACKOUT
            1|AnswerQuality.INCORRECT
            2|AnswerQuality.ALMOST
            3|AnswerQuality.DIFFICULT
            4|AnswerQuality.HESITATED
            5|AnswerQuality.PERFECT
    }

    def "Should properly serialize AnswerQuality object into ordinal"(AnswerQuality answerQuality, int expected){
        when:
            def json = jacksonTester.write(answerQuality)
        then:
            json.getJson() == expected.toString()
        where:
            answerQuality|expected
            AnswerQuality.BLACKOUT|0
            AnswerQuality.INCORRECT|1
            AnswerQuality.ALMOST|2
            AnswerQuality.DIFFICULT|3
            AnswerQuality.HESITATED|4
            AnswerQuality.PERFECT|5
    }
}
