package pl.bochunator.doncards.card.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.card.exception.CardNotFoundException;
import pl.bochunator.doncards.card.model.Card;
import pl.bochunator.doncards.card.model.CardRequest;
import pl.bochunator.doncards.card.model.CardResponse;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.exception.DeckAccessDeniedException;
import pl.bochunator.doncards.deck.exception.DeckNotFoundException;
import pl.bochunator.doncards.deck.model.Deck;
import pl.bochunator.doncards.deck.repository.DeckRepository;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public CardResponse create(Long deckId, Long appUserId, CardRequest dto) {
        Deck deck = deckRepository.findById(deckId)
                        .orElseThrow(() -> new DeckNotFoundException(deckId));
        if (!deck.getAppUser().getId().equals(appUserId)) {
            throw new DeckAccessDeniedException();
        }
        Card card = new Card();
        card.setTerm(dto.term());
        card.setTranslation(dto.translation());
        card.setDeck(deck);
        return CardResponse.from(cardRepository.save(card));
    }


    public CardResponse update(Long deckId, Long cardId, Long appUserId, CardRequest dto) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(deckId));
        if (!deck.getAppUser().getId().equals(appUserId)) {
            throw new DeckAccessDeniedException();
        }
        card.setTerm(dto.term());
        card.setTranslation(dto.translation());
        return CardResponse.from(cardRepository.save(card));
    }

    public void delete(Long deckId, Long cardId, Long appUserId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(deckId));
        if (!deck.getAppUser().getId().equals(appUserId)) {
            throw new DeckAccessDeniedException();
        }
        cardRepository.delete(card);
    }
}
