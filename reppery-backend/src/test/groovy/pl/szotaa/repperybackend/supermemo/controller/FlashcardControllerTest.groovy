package pl.szotaa.repperybackend.supermemo.controller


import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.supermemo.service.FlashcardService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FlashcardControllerTest extends Specification {

    def flashcardService = Mock(FlashcardService)
    def flashcardController = new FlashcardController(flashcardService)
    def mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build()

    def "POST request on /flashcard/{deckId} should execute flashcardService.add" (){
        given:
            def json = "{\"title\": \"title\", \"front\": \"front\", \"back\": \"back\"}"
        when:
            def response = mockMvc.perform(post("/api/flashcard/5")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(json))
        then:
            1 * flashcardService.add(_, _)
            response.andExpect(status().isOk())
    }

    def "GET request on /api/flashcard/{id} should execute flashcardService.findById"(){
        when:
            def response = mockMvc.perform(get("/api/flashcard/5"))
        then:
            response.andExpect(status().isOk())
            1 * flashcardService.findById(_ as Long)
    }

    def "PUT request on /flashcard/{id} should execute flashcardService.update"(){
        given:
            def json = "{\"title\": \"title\", \"front\": \"front\", \"back\": \"back\"}"
        when:
            def response = mockMvc.perform(put("/api/flashcard/5")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(json))
        then:
            1 * flashcardService.update(_, _)
            response.andExpect(status().isOk())
    }

    def "DELETE request on /api/flashcard/{id} should execute flashcardService.delete"(){
        when:
            def response = mockMvc.perform(delete("/api/flashcard/5"))
        then:
            response.andExpect(status().isOk())
            1 * flashcardService.delete(_ as Long)
    }
}
