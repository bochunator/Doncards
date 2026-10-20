package pl.bochunator.doncards.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bochunator.doncards.appuser.exception.EmailAlreadyTakenException;
import pl.bochunator.doncards.appuser.exception.UsernameAlreadyTakenException;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.authentication.model.LoginRequest;
import pl.bochunator.doncards.authentication.model.RegisterRequest;
import pl.bochunator.doncards.authentication.model.RegisterResponse;
import pl.bochunator.doncards.authentication.model.TokenResponse;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponse login(LoginRequest loginRequest) {
        AppUser appUser = appUserRepository
                .findByUsername(loginRequest.username())
                .orElseThrow(() -> new ResponseStatusException(
                        UNAUTHORIZED,
                        "Invalid username or password"
                ));

        if (!passwordEncoder.matches(
                loginRequest.password(),
                appUser.getPassword()
        )) {
            throw new ResponseStatusException(
                    UNAUTHORIZED,
                    "Invalid username or password"
            );
        }

        String token = jwtService.generateToken(
                appUser.getId(),
                appUser.getRole()
        );

        return new TokenResponse(token, "Bearer");
    }

    public RegisterResponse register(RegisterRequest dto) {
        if (appUserRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyTakenException(dto.email());
        }
        if (appUserRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyTakenException(dto.username());
        }
        AppUser appUser = new AppUser();
        appUser.setEmail(dto.email());
        appUser.setUsername(dto.username());
        String passwordHash = passwordEncoder.encode(dto.password());
        appUser.setPassword(passwordHash);
        appUser.setCreatedAt(LocalDateTime.now());
        appUserRepository.save(appUser);
        return new RegisterResponse("Registration successful. You can now log in.");
    }
}
