package pl.szotaa.repperybackend.bootstrap

import org.springframework.boot.DefaultApplicationArguments
import spock.lang.Specification

import javax.persistence.EntityManager

class MockDataDatabaseInjectorTest extends Specification {

    def entityManager = Mock(EntityManager)
    def injector = new MockDataDatabaseInjector(entityManager: entityManager)

    def "Database gets populated"(){
        when:
            injector.run(new DefaultApplicationArguments())
        then:
            (1.._) * entityManager.persist(_)
    }
}
