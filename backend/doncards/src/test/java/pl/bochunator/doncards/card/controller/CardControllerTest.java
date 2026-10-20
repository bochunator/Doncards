
package pl.bochunator.doncards.card.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.bochunator.doncards.card.model.CardResponse;
import pl.bochunator.doncards.card.service.CardService;
import pl.bochunator.doncards.config.ApplicationConfiguration;
import pl.bochunator.doncards.config.SecurityConfig;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardService cardService;

    @MockitoBean
    private ApplicationConfiguration applicationConfiguration;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldCreateCardWhenValidJwtIsProvided() throws Exception {
        CardResponse response = new CardResponse(
                100L, "apple", "jabłko"
        );

        when(cardService.create(eq(10L), eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(post("/decks/10/cards")
                        .with(jwt().jwt(jwt -> jwt.subject("1"))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "term": "apple",
                                    "translation": "jabłko"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.term").value("apple"))
                .andExpect(jsonPath("$.translation").value("jabłko"));

        verify(cardService).create(eq(10L), eq(1L), any());
    }

    @Test
    void shouldUpdateCardWhenValidJwtIsProvided() throws Exception {
        CardResponse response = new CardResponse(
                100L, "book", "książka"
        );

        when(cardService.update(eq(10L), eq(100L), eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(put("/decks/10/cards/100")
                        .with(jwt().jwt(jwt -> jwt.subject("1"))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "term": "book",
                                    "translation": "książka"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.term").value("book"))
                .andExpect(jsonPath("$.translation").value("książka"));

        verify(cardService).update(eq(10L), eq(100L), eq(1L), any());
    }

    @Test
    void shouldDeleteCardWhenValidJwtIsProvided() throws Exception {
        mockMvc.perform(delete("/decks/10/cards/100")
                        .with(jwt().jwt(jwt -> jwt.subject("1"))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(cardService).delete(10L, 100L, 1L);
    }

    @Test
    void shouldRejectCreatingCardWhenUserIsNotAuthenticated()
            throws Exception {
        mockMvc.perform(post("/decks/10/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "term": "apple",
                                    "translation": "jabłko"
                                }
                                """))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(cardService);
    }
}
