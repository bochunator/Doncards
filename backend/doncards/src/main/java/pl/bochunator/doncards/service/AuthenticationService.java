package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bochunator.doncards.dto.request.applicationuser.CreateApplicationUserRequestDTO;
import pl.bochunator.doncards.dto.request.applicationuser.LoginApplicationUserRequestDTO;
import pl.bochunator.doncards.dto.response.applicationuser.LoginApplicationUserResponseDTO;
import pl.bochunator.doncards.exception.*;
import pl.bochunator.doncards.exception.username.UsernameAlreadyTakenException;
import pl.bochunator.doncards.exception.username.UsernameIsNotValidException;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.model.Role;
import pl.bochunator.doncards.repository.ApplicationUserRepository;
import pl.bochunator.doncards.repository.RoleRepository;
import pl.bochunator.doncards.utils.ValidationUtils;

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

    private final ValidationUtils validationUtils;

    public Long registerUser(CreateApplicationUserRequestDTO applicationUserDto) {
        if (!validationUtils.isValidEmail(applicationUserDto.getEmail())) {
            throw new EmailIsNotValidException(applicationUserDto.getEmail());
        }
        if (!validationUtils.isValidUsername(applicationUserDto.getUsername())) {
            throw new UsernameIsNotValidException(applicationUserDto.getUsername());
        }
        if (!validationUtils.isValidPassword(applicationUserDto.getPassword())) {
            throw new PasswordIsNotValidForRegistrationException();
        }
        if (applicationUserRepository.existsByEmail(applicationUserDto.getEmail())) {
            throw new EmailAlreadyTakenException(applicationUserDto.getEmail());
        }
        if (applicationUserRepository.existsByUsername(applicationUserDto.getUsername())) {
            throw new UsernameAlreadyTakenException(applicationUserDto.getUsername());
        }
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow(UserRoleNotFoundException::new);
        ApplicationUser applicationUser = ApplicationUser.builder()
                .email(applicationUserDto.getEmail())
                .username(applicationUserDto.getUsername())
                .password(passwordEncoder.encode(applicationUserDto.getPassword()))
                .authorities(Set.of(userRole))
                .build();
        return applicationUserRepository.save(applicationUser).getUserId();
    }

    public LoginApplicationUserResponseDTO loginUser(LoginApplicationUserRequestDTO loginRequestDTO) {
        if (!validationUtils.isValidPassword(loginRequestDTO.getPassword())) {
            throw new PasswordIsNotValidForLoginException();
        }
        final String identifier = loginRequestDTO.getIdentifier();
        String username = identifier;
        if (validationUtils.isValidEmail(identifier)) {
            ApplicationUser findByEmail = applicationUserRepository.findByEmail(identifier)
                    .orElseThrow(() -> new EmailNotFoundException(identifier));
            username = findByEmail.getUsername();
        } else if (!validationUtils.isValidUsername(identifier)) {
            throw new InvalidIdentifierException(identifier);
        }
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, loginRequestDTO.getPassword())
        );
        String jwt = tokenService.generateJwt(auth);
        ApplicationUser authenticatedUser = (ApplicationUser) auth.getPrincipal();
        return LoginApplicationUserResponseDTO.builder()
                .user(authenticatedUser)
                .jwt(jwt)
                .build();
    }

    public ApplicationUser verifyUserByToken(String jwt) {
        String username = tokenService.getUsernameFromToken(jwt);
        return applicationUserRepository.findByUsername(username).orElseThrow();
    }

    public ApplicationUser findByUsername(String username) {
        ApplicationUser user = applicationUserRepository.findByUsername(username).orElseThrow();
        System.out.println(user);
        return user;
    }
}
