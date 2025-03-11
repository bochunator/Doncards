package pl.bochunator.doncards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochunator.doncards.enums.RoleName;
import pl.bochunator.doncards.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleName name);
}
