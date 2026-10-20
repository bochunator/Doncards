
package pl.bochunator.doncards.authentication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import pl.bochunator.doncards.appuser.model.AppRole;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.appuser.exception.EmailAlreadyTakenException;
import pl.bochunator.doncards.appuser.exception.UsernameAlreadyTakenException;
import pl.bochunator.doncards.authentication.model.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");
        appUser.setUsername("testuser");
        appUser.setPassword("hashed-password");
        appUser.setRole(AppRole.USER);
    }

    @Test
    void shouldRegisterUserWithEncodedPassword() {
        RegisterRequest request = new RegisterRequest(
                "test@example.com",
                "testuser",
                "plain-password"
        );

        when(appUserRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(appUserRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("plain-password")).thenReturn("hashed-password");

        RegisterResponse response = authenticationService.register(request);

        assertEquals("Registration successful. You can now log in.", response.message());

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());

        AppUser savedUser = captor.getValue();
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("hashed-password", savedUser.getPassword());
        assertNotEquals("plain-password", savedUser.getPassword());
        assertNotNull(savedUser.getCreatedAt());
        assertEquals(AppRole.USER, savedUser.getRole());
    }

    @Test
    void shouldRejectRegistrationWhenEmailIsAlreadyTaken() {
        when(appUserRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(
                EmailAlreadyTakenException.class,
                () -> authenticationService.register(
                        new RegisterRequest(
                                "test@example.com",
                                "testuser",
                                "plain-password"
                        )
                )
        );

        verify(appUserRepository, never()).save(any());
        verify(appUserRepository, never()).existsByUsername(anyString());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldRejectRegistrationWhenUsernameIsAlreadyTaken() {
        when(appUserRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(appUserRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(
                UsernameAlreadyTakenException.class,
                () -> authenticationService.register(
                        new RegisterRequest(
                                "test@example.com",
                                "testuser",
                                "plain-password"
                        )
                )
        );

        verify(appUserRepository, never()).save(any());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldLoginWhenCredentialsAreValid() {
        when(appUserRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(appUser));
        when(passwordEncoder.matches("plain-password", "hashed-password"))
                .thenReturn(true);
        when(jwtService.generateToken(1L, AppRole.USER))
                .thenReturn("test-jwt-token");

        TokenResponse response = authenticationService.login(
                new LoginRequest("testuser", "plain-password")
        );

        assertEquals("test-jwt-token", response.accessToken());
        assertEquals("Bearer", response.tokenyType());

        verify(passwordEncoder).matches("plain-password", "hashed-password");
        verify(jwtService).generateToken(1L, AppRole.USER);
    }

    @Test
    void shouldRejectLoginWhenUsernameDoesNotExist() {
        when(appUserRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authenticationService.login(
                        new LoginRequest("unknown", "plain-password")
                )
        );

        assertEquals(401, exception.getStatusCode().value());

        verifyNoInteractions(passwordEncoder, jwtService);
    }

    @Test
    void shouldRejectLoginWhenPasswordIsIncorrect() {
        when(appUserRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(appUser));
        when(passwordEncoder.matches("wrong-password", "hashed-password"))
                .thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authenticationService.login(
                        new LoginRequest("testuser", "wrong-password")
                )
        );

        assertEquals(401, exception.getStatusCode().value());

        verifyNoInteractions(jwtService);
    }
}
