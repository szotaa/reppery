package pl.szotaa.repperybackend.supermemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository;

@Component
@RequiredArgsConstructor
public class FlashcardSecurityExpressions {

    private final FlashcardRepository flashcardRepository;
    private final DeckSecurityExpressions deckSecurityExpressions;

    public boolean isFlashcardOwner(long flashcardId, Authentication authentication){
        long deckId = this.flashcardRepository.getDeckId(flashcardId);
        return this.deckSecurityExpressions.isDeckOwner(deckId, authentication);
    }
}
