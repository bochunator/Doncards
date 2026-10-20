
package pl.bochunator.doncards.authentication.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pl.bochunator.doncards.authentication.model.LoginRequest;
import pl.bochunator.doncards.authentication.model.RegisterRequest;
import pl.bochunator.doncards.authentication.model.RegisterResponse;
import pl.bochunator.doncards.authentication.model.TokenResponse;
import pl.bochunator.doncards.authentication.service.AuthenticationService;
import pl.bochunator.doncards.config.ApplicationConfiguration;
import pl.bochunator.doncards.config.SecurityConfig;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@Import(SecurityConfig.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private ApplicationConfiguration applicationConfiguration;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldLoginAndReturnToken() throws Exception {
        LoginRequest request = new LoginRequest("testuser", "plain-password");

        when(authenticationService.login(request))
                .thenReturn(new TokenResponse("test-jwt-token", "Bearer"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("test-jwt-token"))
                .andExpect(jsonPath("$.tokenyType").value("Bearer"));

        verify(authenticationService).login(request);
    }

    @Test
    void shouldRegisterAndReturnCreated() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "test@example.com",
                "testuser",
                "plain-password"
        );

        when(authenticationService.register(request))
                .thenReturn(new RegisterResponse(
                        "Registration successful. You can now log in."
                ));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value("Registration successful. You can now log in."));

        verify(authenticationService).register(request);
    }

    @Test
    void shouldReturnUnauthorizedWhenLoginFails() throws Exception {
        LoginRequest request = new LoginRequest("unknown", "wrong-password");

        when(authenticationService.login(request))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.UNAUTHORIZED,
                        "Invalid username or password"
                ));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());

        verify(authenticationService).login(request);
    }

    @Test
    void shouldReturnConflictWhenRegistrationFails() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "test@example.com",
                "testuser",
                "plain-password"
        );

        when(authenticationService.register(request))
                .thenThrow(new pl.bochunator.doncards.appuser.exception.EmailAlreadyTakenException(
                        "test@example.com"
                ));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(authenticationService).register(request);
    }
}
