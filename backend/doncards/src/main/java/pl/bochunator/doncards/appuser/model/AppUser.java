package pl.bochunator.doncards.appuser.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bochunator.doncards.deck.model.Deck;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AppRole role = AppRole.USER;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "appUser")
    private List<Deck> decks;
}
