
package pl.bochunator.doncards.deck.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import pl.bochunator.doncards.PageResponse;
import pl.bochunator.doncards.appuser.model.AppUserSummaryResponse;
import pl.bochunator.doncards.card.model.CardResponse;
import pl.bochunator.doncards.config.ApplicationConfiguration;
import pl.bochunator.doncards.config.SecurityConfig;
import pl.bochunator.doncards.deck.model.*;
import pl.bochunator.doncards.deck.service.DeckService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeckController.class)
@Import(SecurityConfig.class)
class DeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeckService deckService;

    @MockitoBean
    private ApplicationConfiguration applicationConfiguration;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    private static final LocalDateTime CREATED_AT =
            LocalDateTime.of(2026, 1, 15, 12, 30);

    private static org.springframework.test.web.servlet.request
            .RequestPostProcessor userJwt() {
        return jwt().jwt(jwt -> jwt.subject("1"))
                .authorities(new SimpleGrantedAuthority("ROLE_USER"));
    }

    private DeckDetailsResponse deckDetailsResponse() {
        AppUserSummaryResponse user = new AppUserSummaryResponse(
                1L, "test@example.com", "testuser", CREATED_AT
        );

        List<CardResponse> cards = List.of(
                new CardResponse(100L, "apple", "jabłko")
        );

        return new DeckDetailsResponse(
                10L, "English", "English vocabulary",
                CREATED_AT, 100L, user, cards
        );
    }

    @Test
    void shouldCreateDeckWhenAuthenticated() throws Exception {
        when(deckService.create(any(CreateDeckRequest.class), eq(1L)))
                .thenReturn(new CreateDeckResponse(10L));

        mockMvc.perform(post("/decks")
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "English",
                                    "description": "English vocabulary"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10));

        verify(deckService).create(any(CreateDeckRequest.class), eq(1L));
    }

    @Test
    void shouldRejectCreatingDeckWhenUnauthenticated() throws Exception {
        mockMvc.perform(post("/decks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "English",
                                    "description": "English vocabulary"
                                }
                                """))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(deckService);
    }

    @Test
    void shouldReturnAllDecks() throws Exception {
        PageResponse<DeckDetailsResponse> response =
                new PageResponse<>(List.of(deckDetailsResponse()), 0, 10, 1, false);

        when(deckService.findAll(0, 10, DeckSort.LATEST)).thenReturn(response);

        mockMvc.perform(get("/decks")
                        .param("page", "0")
                        .param("size", "10")
                        .param("deckSort", "LATEST"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(10))
                .andExpect(jsonPath("$.content[0].name").value("English"))
                .andExpect(jsonPath("$.content[0].appUserSummaryResponse.username")
                        .value("testuser"))
                .andExpect(jsonPath("$.content[0].cardResponses[0].term")
                        .value("apple"));

        verify(deckService).findAll(0, 10, DeckSort.LATEST);
    }

    @Test
    void shouldReturnDecksByUser() throws Exception {
        PageResponse<DeckDetailsResponse> response =
                new PageResponse<>(List.of(deckDetailsResponse()), 0, 10, 1, false);

        when(deckService.findAllByAppUserId(1L, 0, 10, DeckSort.POPULAR))
                .thenReturn(response);

        mockMvc.perform(get("/decks/by-user")
                        .param("appUserId", "1")
                        .param("page", "0")
                        .param("size", "10")
                        .param("deckSort", "POPULAR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(10))
                .andExpect(jsonPath("$.content[0].name").value("English"));

        verify(deckService).findAllByAppUserId(1L, 0, 10, DeckSort.POPULAR);
    }

    @Test
    void shouldReturnDeckById() throws Exception {
        when(deckService.findById(10L)).thenReturn(deckDetailsResponse());

        mockMvc.perform(get("/decks/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("English"))
                .andExpect(jsonPath("$.appUserSummaryResponse.username")
                        .value("testuser"))
                .andExpect(jsonPath("$.cardResponses[0].translation")
                        .value("jabłko"));

        verify(deckService).findById(10L);
    }

    @Test
    void shouldUpdateDeckWhenAuthenticated() throws Exception {
        when(deckService.update(eq(10L), any(UpdateDeckRequest.class), eq(1L)))
                .thenReturn(new DeckSummaryResponse(
                        10L, "Updated English", "Updated description",
                        CREATED_AT, 100L
                ));

        mockMvc.perform(put("/decks/10")
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Updated English",
                                    "description": "Updated description"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Updated English"))
                .andExpect(jsonPath("$.description").value("Updated description"));

        verify(deckService).update(eq(10L), any(UpdateDeckRequest.class), eq(1L));
    }

    @Test
    void shouldRejectUpdatingDeckWhenUnauthenticated() throws Exception {
        mockMvc.perform(put("/decks/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Updated English",
                                    "description": "Updated description"
                                }
                                """))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(deckService);
    }

    @Test
    void shouldDeleteDeckWhenAuthenticated() throws Exception {
        mockMvc.perform(delete("/decks/10").with(userJwt()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(deckService).delete(10L, 1L);
    }

    @Test
    void shouldRejectDeletingDeckWhenUnauthenticated() throws Exception {
        mockMvc.perform(delete("/decks/10"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(deckService);
    }
}
