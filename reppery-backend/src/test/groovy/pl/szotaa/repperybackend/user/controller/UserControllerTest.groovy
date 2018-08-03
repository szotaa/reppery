package pl.szotaa.repperybackend.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.user.domain.User
import pl.szotaa.repperybackend.user.service.UserService
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserControllerTest extends Specification {

    def userService = Mock(UserService)
    def userController = new UserController(userService)
    def writer = new ObjectMapper().writer().withDefaultPrettyPrinter()
    def mockMvc = MockMvcBuilders.standaloneSetup(userController).build()

    @Ignore
    def "POST request to /user should call userService.register"(){
        given:
            def user = User.builder()
                    .email("user@email.com")
                    .password("password")
                    .build()
            def json = writer.writeValueAsString(user)
        when:
            def result = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json))
        then:
            result.andExpect(status().isOk())
            1 * userService.register(_) >> {it == user}
    }
}
