package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bochunator.doncards.model.*;
import pl.bochunator.doncards.repository.RoleRepository;
import pl.bochunator.doncards.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public RegistrationDTO registerUser(RegistrationDTO registrationDTO) {
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        userRepository.save(new ApplicationUser(0L, registrationDTO.getUsername(), encodedPassword, authorities));
        return registrationDTO;
    }

    public LoginResponseDTO loginUser(LoginDTO loginDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(), token);
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }
}
