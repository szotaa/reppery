package pl.szotaa.repperybackend.auth.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.auth.service.AuthService
import pl.szotaa.repperybackend.user.domain.User
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthControllerTest extends Specification {

    def authService = Mock(AuthService)
    def authController = new AuthController(authService)
    def writer = new ObjectMapper().writer().withDefaultPrettyPrinter()
    def mockMvc = MockMvcBuilders.standaloneSetup(authController).build()

    @Ignore
    def "POST request on /auth should call authService.authenticate"(){
        given:
            def user = User.builder()
                    .email("user@email.com")
                    .password("password")
                    .build()
            def json = writer.writeValueAsString(user)
        when:
            def result = mockMvc.perform(post("/auth")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(json))
        then:
            result.andExpect(status().isOk())
    }
}
