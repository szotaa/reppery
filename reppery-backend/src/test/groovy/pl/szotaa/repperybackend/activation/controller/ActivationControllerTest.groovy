package pl.szotaa.repperybackend.activation.controller

import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.activation.service.ActivationService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ActivationControllerTest extends Specification {

    def activationService = Mock(ActivationService)
    def activationController = new ActivationController(activationService)
    def mockMvc = MockMvcBuilders.standaloneSetup(activationController).build()

    def "GET request on /api/activate/{token} with valid token should call activation service"(){
        given:
            def token = "token"
            activationService.activate(_ as String) >> void
        when:
            def response = mockMvc.perform(get("/api/activate/:token", token))
        then:
            1 * activationService.activate(_)
            response.andExpect(status().isOk())
    }
}
