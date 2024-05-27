package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bochunator.doncards.model.*;
import pl.bochunator.doncards.repository.RoleRepository;
import pl.bochunator.doncards.repository.ApplicationUserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final ApplicationUserRepository applicationUserRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public ResponseEntity<Response> registerUser(String email, String username, String password) {
        Response response = new Response();
        Optional<ApplicationUser> findByEmail = applicationUserRepository.findByEmail(email);
        if (findByEmail.isPresent()) {
            response.setMessage("Email already exists");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
        Optional<ApplicationUser> findByUsername = applicationUserRepository.findByUsername(username);
        if (findByUsername.isPresent()) {
            response.setMessage("Username already exists");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        ApplicationUser user = new ApplicationUser();
        user.setEmail(email);
        user.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setAuthorities(authorities);
        applicationUserRepository.save(user);
        response.setMessage("User registered successfully");
        return ResponseEntity.ok(response);
    }

    public LoginResponseDTO loginUser(LoginDTO loginDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(applicationUserRepository.findByUsername(loginDTO.getUsername()).orElseThrow(), token);
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }
}
