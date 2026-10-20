
package pl.bochunator.doncards.appuser.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bochunator.doncards.appuser.exception.AppUserNotFoundException;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.model.AppUserDetailsResponse;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.repository.DeckRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");
        appUser.setUsername("testuser");
        appUser.setCreatedAt(LocalDateTime.of(2026, 1, 15, 12, 30));
    }

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        // Arrange
        when(appUserRepository.findById(1L))
                .thenReturn(Optional.of(appUser));
        when(deckRepository.countByAppUserId(1L))
                .thenReturn(3L);
        when(cardRepository.countByDeck_AppUser_Id(1L))
                .thenReturn(25L);

        // Act
        AppUserDetailsResponse result = appUserService.findById(1L);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("test@example.com", result.email());
        assertEquals("testuser", result.username());
        assertEquals(LocalDateTime.of(2026, 1, 15, 12, 30), result.createdAt());
        assertEquals(3L, result.deckCount());
        assertEquals(25L, result.cardCount());

        verify(appUserRepository).findById(1L);
        verify(deckRepository).countByAppUserId(1L);
        verify(cardRepository).countByDeck_AppUser_Id(1L);
    }

    @Test
    void shouldThrowAppUserNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        when(appUserRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                AppUserNotFoundException.class,
                () -> appUserService.findById(99L)
        );

        verify(deckRepository, never()).countByAppUserId(anyLong());
        verify(cardRepository, never()).countByDeck_AppUser_Id(anyLong());
    }

    @Test
    void shouldReturnZeroCountsWhenUserHasNoDecksOrCards() {
        // Arrange
        when(appUserRepository.findById(1L))
                .thenReturn(Optional.of(appUser));
        when(deckRepository.countByAppUserId(1L))
                .thenReturn(0L);
        when(cardRepository.countByDeck_AppUser_Id(1L))
                .thenReturn(0L);

        // Act
        AppUserDetailsResponse result = appUserService.findById(1L);

        // Assert
        assertEquals(0L, result.deckCount());
        assertEquals(0L, result.cardCount());
        assertEquals("testuser", result.username());
    }
}
