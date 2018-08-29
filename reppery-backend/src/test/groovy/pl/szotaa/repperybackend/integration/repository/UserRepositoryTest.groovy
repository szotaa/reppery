package pl.szotaa.repperybackend.integration.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import pl.szotaa.repperybackend.user.domain.Role
import pl.szotaa.repperybackend.user.domain.User
import pl.szotaa.repperybackend.user.repository.UserRepository
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends Specification {

    @Autowired
    private TestEntityManager testEntityManager

    @Autowired
    private UserRepository repository

    def setup(){
        def user = User.builder()
                .email("user@email.com")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .emailActivationToken(null)
                .build()
        def admin = User.builder()
                .email("admin@email.com")
                .password("password")
                .role(Role.ROLE_ADMIN)
                .isEnabled(true)
                .emailActivationToken(null)
                .build()

        def notYetVerified = User.builder()
                .email("notVerified@email.com")
                .password("password")
                .build()

        testEntityManager.persist(user)
        testEntityManager.persist(admin)
        testEntityManager.persist(notYetVerified)
        testEntityManager.flush()
    }

    def "FindByEmail with existent email should return user optional"(){
        given:
            def email = "user@email.com"
        when:
            def result = repository.findByEmail(email)
        then:
            result instanceof Optional<User>
            result.get().email == email
    }

    def "FindByEmail with non existent email should return empty optional"(){
        given:
            def email = "noSuchUser@email.com"
        when:
            def result = repository.findByEmail(email)
        then:
            result instanceof Optional<User>
            !result.isPresent()
    }

    def "ExistsByEmail with existent email should return true"(){
        given:
            def email = "user@email.com"
        when:
            def result = repository.existsByEmail(email)
        then:
            result
    }

    def "ExistsByEmail with non existent email should return false"(){
        given:
            def email = "noSuchUser@email.com"
        when:
            def result = repository.existsByEmail(email)
        then:
            !result
    }
}
