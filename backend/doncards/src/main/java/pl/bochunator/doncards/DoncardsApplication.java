package pl.bochunator.doncards;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.model.Role;
import pl.bochunator.doncards.repository.RoleRepository;
import pl.bochunator.doncards.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DoncardsApplication {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(DoncardsApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent())
                return;
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.save(new Role(1L, "ADMIN")));
            roles.add(roleRepository.save(new Role(2L, "USER")));
            ApplicationUser admin = new ApplicationUser(1L, adminUsername, passwordEncoder.encode(adminPassword), roles);
            userRepository.save(admin);
        };
    }

}
