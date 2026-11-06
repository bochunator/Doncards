package pl.bochunator.doncards.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bochunator.doncards.appuser.model.AppRole;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner run() {
        return args -> {
            if (appUserRepository.findByUsername("admin").isPresent()) {
                return;
            }

            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setRole(AppRole.ADMIN);
            String adminPasswordHash = passwordEncoder.encode("Admin123!");
            admin.setPassword(adminPasswordHash);
            admin.setCreatedAt(LocalDateTime.now());
            appUserRepository.save(admin);
        };
    }
}
