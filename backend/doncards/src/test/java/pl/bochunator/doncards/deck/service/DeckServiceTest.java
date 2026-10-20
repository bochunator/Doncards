
package pl.bochunator.doncards.deck.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import pl.bochunator.doncards.appuser.exception.AppUserNotFoundException;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.card.model.Card;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.exception.DeckAccessDeniedException;
import pl.bochunator.doncards.deck.exception.DeckNotFoundException;
import pl.bochunator.doncards.deck.model.*;
import pl.bochunator.doncards.deck.repository.DeckRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeckServiceTest {

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private DeckService deckService;

    private AppUser appUser;
    private Deck deck;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");
        appUser.setUsername("testuser");
        appUser.setCreatedAt(LocalDateTime.of(2026, 1, 1, 12, 0));

        deck = new Deck();
        deck.setId(10L);
        deck.setName("English");
        deck.setDescription("English vocabulary");
        deck.setCreatedAt(LocalDateTime.of(2026, 1, 2, 12, 0));
        deck.setViewCount(100L);
        deck.setAppUser(appUser);
    }

    @Test
    void shouldCreateDeckWhenUserExists() {
        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));
        when(deckRepository.save(any(Deck.class))).thenAnswer(invocation -> {
            Deck savedDeck = invocation.getArgument(0);
            savedDeck.setId(10L);
            return savedDeck;
        });

        CreateDeckResponse response = deckService.create(
                new CreateDeckRequest("English", "English vocabulary"),
                1L
        );

        assertEquals(10L, response.id());

        ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
        verify(deckRepository).save(captor.capture());

        Deck savedDeck = captor.getValue();
        assertEquals("English", savedDeck.getName());
        assertEquals("English vocabulary", savedDeck.getDescription());
        assertEquals(appUser, savedDeck.getAppUser());
        assertNotNull(savedDeck.getCreatedAt());
        assertNotNull(savedDeck.getViewCount());
        assertTrue(savedDeck.getViewCount() >= 0);
        assertTrue(savedDeck.getViewCount() < Integer.MAX_VALUE);
    }

    @Test
    void shouldThrowWhenCreatingDeckForNonexistentUser() {
        when(appUserRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                AppUserNotFoundException.class,
                () -> deckService.create(
                        new CreateDeckRequest("English", "Description"),
                        99L
                )
        );

        verify(deckRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllDecksWithPreviewCards() {
        Page<Deck> deckPage = new PageImpl<>(List.of(deck));
        Card card = new Card();
        card.setId(100L);
        card.setTerm("apple");
        card.setTranslation("jabłko");

        when(deckRepository.findAll(any(Pageable.class))).thenReturn(deckPage);
        when(cardRepository.findTop5ByDeckId(10L)).thenReturn(List.of(card));

        var response = deckService.findAll(0, 10, DeckSort.LATEST);

        assertEquals(1, response.content().size());
        assertEquals(10L, response.content().get(0).id());
        assertEquals("English", response.content().get(0).name());
        assertEquals(1, response.content().get(0).cardResponses().size());
        assertEquals("apple", response.content().get(0).cardResponses().get(0).term());

        verify(cardRepository).findTop5ByDeckId(10L);
    }

    @Test
    void shouldSortAllDecksByLatestWhenLatestIsSelected() {
        when(deckRepository.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        deckService.findAll(0, 10, DeckSort.LATEST);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(deckRepository).findAll(captor.capture());

        Pageable pageable = captor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        assertEquals("createdAt", pageable.getSort().getOrderFor("createdAt").getProperty());
        assertTrue(pageable.getSort().getOrderFor("createdAt").isDescending());
    }

    @Test
    void shouldSortAllDecksByViewCountWhenPopularIsSelected() {
        when(deckRepository.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        deckService.findAll(1, 5, DeckSort.POPULAR);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(deckRepository).findAll(captor.capture());

        Pageable pageable = captor.getValue();
        assertEquals(1, pageable.getPageNumber());
        assertEquals(5, pageable.getPageSize());
        assertTrue(pageable.getSort().getOrderFor("viewCount").isDescending());
    }

    @Test
    void shouldReturnUserDecksWithPreviewCards() {
        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));
        when(deckRepository.findAllByAppUserId(eq(1L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(deck)));
        when(cardRepository.findTop5ByDeckId(10L)).thenReturn(List.of());

        var response = deckService.findAllByAppUserId(1L, 0, 10, DeckSort.LATEST);

        assertEquals(1, response.content().size());
        assertEquals(10L, response.content().get(0).id());
        assertTrue(response.content().get(0).cardResponses().isEmpty());

        verify(deckRepository).findAllByAppUserId(eq(1L), any(Pageable.class));
    }

    @Test
    void shouldThrowWhenListingDecksForNonexistentUser() {
        when(appUserRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                AppUserNotFoundException.class,
                () -> deckService.findAllByAppUserId(99L, 0, 10, DeckSort.LATEST)
        );

        verify(deckRepository, never())
                .findAllByAppUserId(anyLong(), any(Pageable.class));
    }

    @Test
    void shouldReturnDeckDetailsWhenDeckExists() {
        Card card = new Card();
        card.setId(100L);
        card.setTerm("apple");
        card.setTranslation("jabłko");
        deck.setCards(List.of(card));

        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));

        DeckDetailsResponse response = deckService.findById(10L);

        assertEquals(10L, response.id());
        assertEquals("English", response.name());
        assertEquals("testuser", response.appUserSummaryResponse().username());
        assertEquals(1, response.cardResponses().size());
        assertEquals("apple", response.cardResponses().get(0).term());
    }

    @Test
    void shouldThrowWhenFindingNonexistentDeck() {
        when(deckRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                DeckNotFoundException.class,
                () -> deckService.findById(99L)
        );
    }

    @Test
    void shouldUpdateDeckWhenUserOwnsIt() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(deckRepository.save(deck)).thenReturn(deck);

        DeckSummaryResponse response = deckService.update(
                10L,
                new UpdateDeckRequest("Updated English", "Updated description"),
                1L
        );

        assertEquals("Updated English", response.name());
        assertEquals("Updated description", response.description());
        verify(deckRepository).save(deck);
    }

    @Test
    void shouldKeepDeckNameWhenUpdateNameIsNull() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));
        when(deckRepository.save(deck)).thenReturn(deck);

        DeckSummaryResponse response = deckService.update(
                10L,
                new UpdateDeckRequest(null, "New description"),
                1L
        );

        assertEquals("English", response.name());
        assertEquals("New description", response.description());
    }

    @Test
    void shouldRejectUpdatingDeckOwnedByAnotherUser() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));

        assertThrows(
                AccessDeniedException.class,
                () -> deckService.update(
                        10L,
                        new UpdateDeckRequest("Hacked", "Hacked"),
                        2L
                )
        );

        verify(deckRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentDeck() {
        when(deckRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                DeckNotFoundException.class,
                () -> deckService.update(
                        99L,
                        new UpdateDeckRequest("Name", "Description"),
                        1L
                )
        );

        verify(deckRepository, never()).save(any());
    }

    @Test
    void shouldDeleteDeckWhenUserOwnsIt() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));

        deckService.delete(10L, 1L);

        verify(deckRepository).delete(deck);
    }

    @Test
    void shouldRejectDeletingDeckOwnedByAnotherUser() {
        when(deckRepository.findById(10L)).thenReturn(Optional.of(deck));

        assertThrows(
                DeckAccessDeniedException.class,
                () -> deckService.delete(10L, 2L)
        );

        verify(deckRepository, never()).delete(any());
    }

    @Test
    void shouldThrowWhenDeletingNonexistentDeck() {
        when(deckRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                DeckNotFoundException.class,
                () -> deckService.delete(99L, 1L)
        );

        verify(deckRepository, never()).delete(any());
    }
}
