package pl.bochunator.doncards.appuser.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochunator.doncards.appuser.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByEmail(String email);
    Page<AppUser> findAll(Pageable pageable);
    boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);
}
