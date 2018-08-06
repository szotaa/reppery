package pl.szotaa.repperybackend.supermemo.controller


import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.service.FlashcardService
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RevisionControllerTest extends Specification {

    def flashcardService = Mock(FlashcardService)
    def repetitionController = new RevisionController(flashcardService)
    def mockMvc = MockMvcBuilders.standaloneSetup(repetitionController).build()

    def "GET request to /api/repetition?limit=5 should ask FlashcardService for today's review items"(){
        given:
            def limit = 5
            def flashcards = [new Flashcard(), new Flashcard(), new Flashcard(), new Flashcard(), new Flashcard()]
            1 * flashcardService.findForRepetiton(_) >> {flashcards}
        when:
            def response = mockMvc.perform(get("/api/repetition")
                                    .param("limit", limit.toString()))
        then:
            response.andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    }

    @Ignore
    def "POST request to /repetition should updateByAnswer and persist Flashcard entity with provided id"(){
        given:
            def id = 1
            def answerQuality = "{\"answerQuality\": 1}"
        when:
            def response = mockMvc.perform(post("/repetition/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(answerQuality))
        then:
            response.andExpect(status().isOk())
    }
}
