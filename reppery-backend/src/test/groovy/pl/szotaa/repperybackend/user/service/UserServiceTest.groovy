package pl.szotaa.repperybackend.user.service

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import pl.szotaa.repperybackend.user.domain.User
import pl.szotaa.repperybackend.user.repository.UserRepository
import spock.lang.Specification

class UserServiceTest extends Specification {

    def userRepository = Mock(UserRepository)
    def passwordEncoder = Mock(PasswordEncoder)
    def userService = new UserService(userRepository, passwordEncoder)

    def "Register method should encode password"(){
        given:
            def email = "user@email.com"
            def password = "password"
            def encodedPassword = "encodedPassword"
            def user = User.builder()
                        .email(email)
                        .password(password)
                        .build()
            userRepository.existsByEmail(_ as String) >> false
            passwordEncoder.encode(_) >> encodedPassword
        when:
            userService.register(user)
        then:
            1 * passwordEncoder.encode(_)
            1 * userRepository.save(_) >> {it.password == encodedPassword}
    }

    def "LoadUserByUsername with existent username should return user"(){
        given:
            def username = "user@email.com"
            userRepository.findByEmail(_ as String) >> Optional.of(new User())
        when:
            def result = userService.loadUserByUsername(username)
        then:
            result instanceof User

    }

    def "LoadUserByUsername with non existent username should throw exception"(){
        given:
            def username = "user@email.com"
            userRepository.findByEmail(_ as String) >> Optional.empty()
        when:
            userService.loadUserByUsername(username)
        then:
            thrown(UsernameNotFoundException)

    }
}