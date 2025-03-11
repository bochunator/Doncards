package pl.bochunator.doncards.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bochunator.doncards.enums.RoleName;
import pl.bochunator.doncards.model.AppUser;
import pl.bochunator.doncards.model.Role;
import pl.bochunator.doncards.repository.AppUserRepository;
import pl.bochunator.doncards.repository.RoleRepository;

import java.util.Set;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(RoleName.ADMIN)));
        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseGet(() -> roleRepository.save(new Role(RoleName.USER)));
        AppUser admin = appUserRepository.findByUsername("admin")
                .orElseGet(() -> appUserRepository.save(AppUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(Set.of(adminRole, userRole))
                .build()));
        IntStream.rangeClosed(1, 5)
                .forEach(i -> appUserRepository.findByUsername("user" + i)
                        .orElseGet(() -> appUserRepository.save(AppUser.builder()
                                .username("user" + i)
                                .password(passwordEncoder.encode("user" + i))
                                .roles(Set.of(userRole))
                                .build())));
            /*
            String defaultEmail = "user" + i + "@emai.com";
            if (appUserRepository.existsByEmail(defaultEmail)) {
                continue;
            }
             */

    }
}