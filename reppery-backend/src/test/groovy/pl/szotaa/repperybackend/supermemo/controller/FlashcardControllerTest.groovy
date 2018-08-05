package pl.szotaa.repperybackend.supermemo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.service.FlashcardService
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FlashcardControllerTest extends Specification {

    def flashcardService = Mock(FlashcardService)
    def flashcardController = new FlashcardController(flashcardService)
    def writer = new ObjectMapper().writer().withDefaultPrettyPrinter()
    def mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build()

    @Ignore
    def "POST request on /flashcard should execute flashcardService.add" (){
        given:
            def flashcard = Flashcard.builder().id(1).build()
            def json = writer.writeValueAsString(flashcard)
        when:
            def response = mockMvc.perform(post("/flashcard")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(json))
        then:
            1 * flashcardService.add(_)
            response.andExpect(status().isOk())
    }

    def "GET request on /api/flashcard/{id} should execute flashcardService.findById"(){
        when:
            def response = mockMvc.perform(get("/api/flashcard/5"))
        then:
            response.andExpect(status().isOk())
            1 * flashcardService.findById(_ as Long)
    }

    @Ignore
    def "PUT request on /flashcard should execute flashcardService.update"(){
        given:
            def flashcard = Flashcard.builder().id(1).build()
            def json = writer.writeValueAsString(flashcard)
        when:
            def response = mockMvc.perform(put("/flashcard/5")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(json))
        then:
            response.andExpect(status().isOk())
            1 * flashcardService.update(_)
    }

    def "DELETE request on /api/flashcard/{id} should execute flashcardService.delete"(){
        when:
            def response = mockMvc.perform(delete("/api/flashcard/5"))
        then:
            response.andExpect(status().isOk())
            1 * flashcardService.delete(_ as Long)
    }
}
