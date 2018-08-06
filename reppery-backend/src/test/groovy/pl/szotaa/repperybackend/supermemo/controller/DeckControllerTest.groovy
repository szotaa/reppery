package pl.szotaa.repperybackend.supermemo.controller

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.supermemo.domain.Deck
import pl.szotaa.repperybackend.supermemo.service.DeckService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DeckControllerTest extends Specification {

    def deckService = Mock(DeckService)
    def deckController = new DeckController(deckService)
    def mockMvc = MockMvcBuilders.standaloneSetup(deckController).build()

    def "GET request on /api/deck should call deckService.getAllGroupsOwnedByCurrentUser"(){
        given:
            deckService.getAllGroupsOwnedByCurrentUser() >> new ArrayList<Deck>()
        when:
            def result = mockMvc.perform(get("/api/deck"))
        then:
            1 * deckService.getAllGroupsOwnedByCurrentUser()
            result.andExpect(status().isOk())
    }

    def "POST request on /api/deck should call deckService.add"(){
        given:
            def json = "{\"title\":\"title\"}"
        when:
            def result = mockMvc.perform(post("/api/deck")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(json))
        then:
            1 * deckService.add(_)
            result.andExpect(status().isOk())
    }
}
