package pl.szotaa.repperybackend.common

import org.springframework.boot.DefaultApplicationArguments
import spock.lang.Specification

import javax.persistence.EntityManager

class DatabasePopulaterTest extends Specification {

    def entityManager = Mock(EntityManager)
    def dbPopulater = new DatabasePopulater(entityManager: entityManager)

    def "Database gets populated"(){
        when:
            dbPopulater.run(new DefaultApplicationArguments())
        then:
            (1.._) * entityManager.persist(_)
    }
}
