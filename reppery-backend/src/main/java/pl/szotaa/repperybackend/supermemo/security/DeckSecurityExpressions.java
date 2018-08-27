package pl.szotaa.repperybackend.supermemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.supermemo.repository.DeckRepository;
import pl.szotaa.repperybackend.user.domain.User;

@Component
@RequiredArgsConstructor
public class DeckSecurityExpressions {

    private final DeckRepository deckRepository;

    public boolean isDeckOwner(long deckId, Authentication authentication) {
       User user = (User) authentication.getPrincipal();
       long ownerId = deckRepository.getOwnerId(deckId);
       return user.getId() == ownerId;
    }
}
