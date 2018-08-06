package pl.szotaa.repperybackend.auth.controller


import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.auth.service.AuthService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthControllerTest extends Specification {

    def authService = Mock(AuthService)
    def authController = new AuthController(authService)
    def mockMvc = MockMvcBuilders.standaloneSetup(authController).build()

    def "POST request on /api/auth should call authService.authenticate"(){
        given:
            def json = "{\"email\": \"user@email.com\", \"password\": \"password\"}"
        when:
            def result = mockMvc.perform(post("/api/auth")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(json))
        then:
            1 * authService.authenticate(_)
            result.andExpect(status().isOk())
    }
}
