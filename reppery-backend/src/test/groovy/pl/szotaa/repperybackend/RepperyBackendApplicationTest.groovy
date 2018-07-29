package pl.szotaa.repperybackend

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest
class RepperyBackendApplicationTest extends Specification {

    @Ignore
    def "Should boot application context"() {
        expect:
            true
    }
}
