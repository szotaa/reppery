package pl.szotaa.repperybackend.supermemo.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import pl.szotaa.repperybackend.supermemo.domain.Deck
import pl.szotaa.repperybackend.supermemo.exception.DeckNotFoundException
import pl.szotaa.repperybackend.supermemo.repository.DeckRepository
import pl.szotaa.repperybackend.user.domain.User
import spock.lang.Ignore
import spock.lang.Specification

class DeckServiceTest extends Specification {

    def deckRepository = Mock(DeckRepository)
    def deckService = new DeckService(deckRepository)

    def setup(){
        def user = new User()
        def auth = Mock(Authentication)
        auth.getPrincipal() >> user
        SecurityContextHolder.setContext(new SecurityContextImpl(auth))
    }

    def "Add should set owner and save deck"(){
        given:
            def deck = new Deck()
        when:
            deckService.add(deck)
        then:
            1 * deckRepository.save(_) >> {
                it.owner != null
            }
    }

    def "GetById with valid id should return user"(){
        given:
            def id = 5L
            deckRepository.findById(_) >> Optional.of(new Deck())
        when:
            def result = deckService.getById(id)
        then:
            result != null
            result instanceof Deck
    }

    def "GetById with valid id should throw exception"(){
        given:
            def id = 5L
            deckRepository.findById(_) >> Optional.empty()
        when:
            deckService.getById(id)
        then:
            thrown(DeckNotFoundException)
    }

    @Ignore
    def "GetAllGroupsOwnedByCurrentUser should call deckRepository.getAllByOwner"(){
        when:
            deckService.getAllGroupsOwnedByCurrentUser()
        then:
            1 * deckRepository.getAllByOwner(_ as User)
    }
}
