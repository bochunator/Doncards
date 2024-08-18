package pl.bochunator.doncards.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "card",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"term", "translation"})
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"term", "translation"})

public class Card {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    private String term;

    private String translation;

    public Card(String term, String translation) {
        this.term = term;
        this.translation = translation;
    }

}
