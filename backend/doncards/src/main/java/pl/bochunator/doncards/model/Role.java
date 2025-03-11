package pl.bochunator.doncards.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.bochunator.doncards.enums.RoleName;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> appUsers;

    public Role(RoleName name) {
        this.name = name;
    }
}
