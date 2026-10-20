package pl.bochunator.doncards.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bochunator.doncards.card.exception.CardNotFoundException;
import pl.bochunator.doncards.card.model.Card;
import pl.bochunator.doncards.card.model.CardRequest;
import pl.bochunator.doncards.card.model.CardResponse;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.exception.DeckAccessDeniedException;
import pl.bochunator.doncards.deck.exception.DeckNotFoundException;
import pl.bochunator.doncards.deck.model.Deck;
import pl.bochunator.doncards.deck.repository.DeckRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private CardService cardService;

    private Deck deck;
    private Card card;
    private CardRequest request;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.setId(10L);

        var appUser = new pl.bochunator.doncards.appuser.model.AppUser();
        appUser.setId(1L);
        deck.setAppUser(appUser);

        card = new Card();
        card.setId(100L);
        card.setTerm("apple");
        card.setTranslation("jabłko");
        card.setDeck(deck);

        request = new CardRequest("book", "książka");
    }

    @Test
    void shouldCreateCardWhenUserOwnsDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        CardResponse result = cardService.create(10L, 1L, request);

        assertEquals("book", result.term());
        assertEquals("książka", result.translation());

        verify(cardRepository).save(argThat(saved ->
                saved.getDeck() == deck
                        && saved.getTerm().equals("book")
                        && saved.getTranslation().equals("książka")));
    }

    @Test
    void shouldThrowDeckNotFoundExceptionWhenCreatingCardInMissingDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(DeckNotFoundException.class,
                () -> cardService.create(10L, 1L, request));

        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void shouldThrowAccessDeniedWhenCreatingCardInAnotherUsersDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));

        assertThrows(DeckAccessDeniedException.class,
                () -> cardService.create(10L, 2L, request));

        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void shouldUpdateCardWhenUserOwnsDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(100L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        CardResponse result = cardService.update(10L, 100L, 1L, request);

        assertEquals("book", result.term());
        assertEquals("książka", result.translation());

        verify(cardRepository).save(card);
    }

    @Test
    void shouldThrowDeckNotFoundExceptionWhenUpdatingCardInMissingDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(DeckNotFoundException.class,
                () -> cardService.update(10L, 100L, 1L, request));

        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void shouldThrowCardNotFoundExceptionWhenUpdatingMissingCard() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> cardService.update(10L, 100L, 1L, request));

        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void shouldThrowAccessDeniedWhenUpdatingAnotherUsersDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(100L)).thenReturn(Optional.of(card));

        assertThrows(DeckAccessDeniedException.class,
                () -> cardService.update(10L, 100L, 2L, request));

        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void shouldDeleteCardWhenUserOwnsDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(100L)).thenReturn(Optional.of(card));

        cardService.delete(10L, 100L, 1L);

        verify(cardRepository).delete(card);
    }

    @Test
    void shouldThrowDeckNotFoundExceptionWhenDeletingCardInMissingDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(DeckNotFoundException.class,
                () -> cardService.delete(10L, 100L, 1L));

        verify(cardRepository, never()).delete(any(Card.class));
    }

    @Test
    void shouldThrowCardNotFoundExceptionWhenDeletingMissingCard() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> cardService.delete(10L, 100L, 1L));

        verify(cardRepository, never()).delete(any(Card.class));
    }

    @Test
    void shouldThrowAccessDeniedWhenDeletingCardFromAnotherUsersDeck() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(cardRepository.findById(100L)).thenReturn(Optional.of(card));

        assertThrows(DeckAccessDeniedException.class,
                () -> cardService.delete(10L, 100L, 2L));

        verify(cardRepository, never()).delete(any(Card.class));
    }
}