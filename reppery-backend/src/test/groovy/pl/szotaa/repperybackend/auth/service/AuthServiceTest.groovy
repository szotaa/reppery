package pl.szotaa.repperybackend.auth.service

import pl.szotaa.repperybackend.auth.jwt.JwtToken
import pl.szotaa.repperybackend.auth.jwt.JwtTokenFactory
import pl.szotaa.repperybackend.user.domain.User
import spock.lang.Specification

class AuthServiceTest extends Specification {

    def jwtTokenFactory = Mock(JwtTokenFactory)
    def authService = new AuthService(jwtTokenFactory)

    def "Authenticate method should return JwtToken object"(){
        given:
            def user = new User()
            jwtTokenFactory.getJwtToken(_ as User) >> new JwtToken("jwtToken")
        when:
            def result = authService.authenticate(user)
        then:
            result instanceof JwtToken
    }
}
