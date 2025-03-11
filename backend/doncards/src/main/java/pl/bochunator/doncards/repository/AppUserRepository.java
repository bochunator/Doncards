package pl.bochunator.doncards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochunator.doncards.model.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
}
