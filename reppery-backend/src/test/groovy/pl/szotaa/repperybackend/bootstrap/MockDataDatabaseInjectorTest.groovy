package pl.szotaa.repperybackend.bootstrap

import org.springframework.boot.DefaultApplicationArguments
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import javax.persistence.EntityManager

class MockDataDatabaseInjectorTest extends Specification {

    def entityManager = Mock(EntityManager)
    def passwordEncoder = Mock(PasswordEncoder)
    def injector = new MockDataDatabaseInjector(entityManager, passwordEncoder)

    def "Database gets populated"(){
        given:
            passwordEncoder.encode(_) >> "encodedPassword"
        when:
            injector.run(new DefaultApplicationArguments())
        then:
            (1.._) * entityManager.persist(_)
    }
}
