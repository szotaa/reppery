package pl.szotaa.repperybackend.user.controller


import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.user.service.UserService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserControllerTest extends Specification {

    def userService = Mock(UserService)
    def userController = new UserController(userService)
    def mockMvc = MockMvcBuilders.standaloneSetup(userController).build()

    def "POST request to /api/user should call userService.register"(){
        given:
            def json = "{\"email\": \"user@email.com\", \"password\": \"password\"}"
        when:
            def result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json))
        then:
            1 * userService.register(_)
            result.andExpect(status().isOk())
    }
}
