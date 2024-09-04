package pl.bochunator.doncards.dto.deck;

import lombok.Builder;
import lombok.Data;
import pl.bochunator.doncards.model.Card;

import java.util.List;

@Data
@Builder
public class DeckDetailsDTO {

    private Long deckId;
    private Long authorId;
    private String authorName;
    private String name;
    private String description;
    private List<Card> cards;
}
