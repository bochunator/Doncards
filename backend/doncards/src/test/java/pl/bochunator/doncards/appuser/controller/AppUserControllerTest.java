
package pl.bochunator.doncards.appuser.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.bochunator.doncards.PageResponse;
import pl.bochunator.doncards.appuser.model.AppUserDetailsResponse;
import pl.bochunator.doncards.appuser.model.AppUserSearchResponse;
import pl.bochunator.doncards.appuser.service.AppUserService;
import pl.bochunator.doncards.config.ApplicationConfiguration;
import pl.bochunator.doncards.config.SecurityConfig;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppUserController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppUserService appUserService;

    @MockitoBean
    private ApplicationConfiguration applicationConfiguration;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldReturnUserDetailsWhenUserExists() throws Exception {
        // Arrange
        AppUserDetailsResponse response = new AppUserDetailsResponse(
                1L,
                "test@example.com",
                "testuser",
                LocalDateTime.of(2026, 1, 15, 12, 30),
                3L,
                25L
        );

        when(appUserService.findById(1L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/app-users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.deckCount").value(3))
                .andExpect(jsonPath("$.cardCount").value(25));

        verify(appUserService).findById(1L);
    }

    @Test
    void shouldReturnCurrentUserDetailsWhenValidJwtIsProvided() throws Exception {
        // Arrange
        AppUserDetailsResponse response = new AppUserDetailsResponse(
                1L,
                "test@example.com",
                "testuser",
                LocalDateTime.of(2026, 1, 15, 12, 30),
                3L,
                25L
        );

        when(appUserService.findById(1L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/app-users/me")
                        .with(jwt().jwt(jwt -> jwt.subject("1"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.deckCount").value(3))
                .andExpect(jsonPath("$.cardCount").value(25));

        verify(appUserService).findById(1L);
    }

    @Test
    void shouldReturnUsersMatchingSearchQuery() throws Exception {
        // Arrange
        AppUserSearchResponse user = new AppUserSearchResponse(
                1L,
                "testuser"
        );


        PageResponse<AppUserSearchResponse> response = new PageResponse<>(
                List.of(user),
                0,
                10,
                1,
                true
        );

        when(appUserService.findAllByQuery(0, 10, "test"))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/app-users/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("query", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].username").value("testuser"));

        verify(appUserService).findAllByQuery(0, 10, "test");
    }
}
