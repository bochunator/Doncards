package pl.bochunator.doncards.card.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bochunator.doncards.deck.model.Deck;

@Entity
@Data
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String term;

    private String translation;

    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    public Card(String term, String translation, Deck deck) {
        this.term = term;
        this.translation = translation;
        this.deck = deck;
    }
}
