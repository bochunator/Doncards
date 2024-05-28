package pl.bochunator.doncards;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.model.Role;
import pl.bochunator.doncards.repository.RoleRepository;
import pl.bochunator.doncards.repository.ApplicationUserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DoncardsApplication {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${host}")
    private String host;

    public static void main(String[] args) {
        SpringApplication.run(DoncardsApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent())
                return;
            Set<Role> authorities = new HashSet<>();
            authorities.add(roleRepository.save(new Role(1L, "ADMIN")));
            authorities.add(roleRepository.save(new Role(2L, "USER")));
            ApplicationUser admin = new ApplicationUser();
            admin.setUserId(1L);
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setAuthorities(authorities);
            applicationUserRepository.save(admin);
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(host);
            }
        };
    }

}
