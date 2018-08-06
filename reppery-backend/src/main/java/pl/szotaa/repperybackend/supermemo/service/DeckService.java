package pl.szotaa.repperybackend.supermemo.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.Deck;
import pl.szotaa.repperybackend.supermemo.exception.DeckNotFoundException;
import pl.szotaa.repperybackend.supermemo.repository.DeckRepository;
import pl.szotaa.repperybackend.user.domain.User;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;

    public void add(Deck deck){
        deck.setOwner(this.getCurrentUser());
        deckRepository.save(deck);
    }

    public Deck getById(long id) throws DeckNotFoundException {
        return deckRepository.findById(id).orElseThrow(DeckNotFoundException::new);
    }

    public List<Deck> getAllGroupsOwnedByCurrentUser(){
        return deckRepository.getAllByOwner(this.getCurrentUser());
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optional = Optional.ofNullable((User) auth.getPrincipal());
        return optional.<IllegalStateException>orElseThrow(() -> {
            throw new IllegalStateException("Principal object is null.");
        });
    }
}