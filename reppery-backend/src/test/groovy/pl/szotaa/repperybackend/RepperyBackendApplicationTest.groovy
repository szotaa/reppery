package pl.szotaa.repperybackend

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class RepperyBackendApplicationTest extends Specification {

    def "Should boot application context"() {
        expect:
            true
    }
}
