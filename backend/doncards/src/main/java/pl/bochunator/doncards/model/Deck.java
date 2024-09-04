package pl.bochunator.doncards.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "deck",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"author_id", "name"})
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private Long deckId;

    // @JsonIgnoreProperties("decks")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private ApplicationUser author;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "created_at")//, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "version")
    private Integer version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "deck_card_junction",
            joinColumns = {@JoinColumn(name = "deck_id")},
            inverseJoinColumns = {@JoinColumn(name = "card_id")}
    )
    private List<Card> cards;

    public Deck(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public Deck(ApplicationUser author, String name, List<Card> cards) {
        this.author = author;
        this.name = name;
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deckId=" + deckId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", version=" + version +
                ", cards=" + cards +
                '}';
    }
}
