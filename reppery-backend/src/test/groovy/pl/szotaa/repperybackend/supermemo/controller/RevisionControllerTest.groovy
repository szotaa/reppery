package pl.szotaa.repperybackend.supermemo.controller

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.szotaa.repperybackend.supermemo.domain.Flashcard
import pl.szotaa.repperybackend.supermemo.service.RevisionService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RevisionControllerTest extends Specification {

    def revisionService = Mock(RevisionService)
    def repetitionController = new RevisionController(revisionService)
    def mockMvc = MockMvcBuilders.standaloneSetup(repetitionController).build()

    def "GET request to /api/revise/{deckId} should call revisionService.getForRevision"(){
        given:
            revisionService.getForRevision(_) >> [new Flashcard(), new Flashcard(), new Flashcard()]
        when:
            def response = mockMvc.perform(get("/api/revise/5"))
        then:
            1 * revisionService.getForRevision(_)
            response.andExpect(status().isOk())
    }

    def "POST request to /revise should updateByAnswer and persist Flashcard entity with provided id"(){
        given:
            def id = 1
            def answerQuality = "1"
        when:
            def response = mockMvc.perform(post("/api/revise/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(answerQuality))
        then:
            response.andExpect(status().isOk())
    }
}
