package pl.szotaa.repperybackend.auth.jwt

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import pl.szotaa.repperybackend.user.domain.Role
import pl.szotaa.repperybackend.user.domain.User
import pl.szotaa.repperybackend.user.service.UserService
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilterTest extends Specification {

    def jwtTokenUtil = Mock(JwtTokenUtil)
    def userService = Mock(UserService)
    def jwtAuthFilter = new JwtAuthenticationFilter(jwtTokenUtil, userService)

    def "DoFilterInternal should set authentication for request containing JWT token"(){
        given:
            def request = new MockHttpServletRequest()
            def response = Mock(HttpServletResponse)
            def filterChain = Mock(FilterChain)
            request.addHeader("Authorization", "Bearer token")
            jwtTokenUtil.getUsernameFromJwt(_) >> "user@email.com"
            def user = new User()
            user.role = Role.ROLE_USER
            userService.loadUserByUsername(_) >> user
        when:
            jwtAuthFilter.doFilterInternal(request, response, filterChain)
        then:
            SecurityContextHolder.getContext() != null
    }
}
