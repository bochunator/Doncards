package pl.bochunator.doncards.dto.deck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.bochunator.doncards.dto.card.CardDTO;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DeckCreateDTO {

    private String name;
    private String description;
    private List<CardDTO> cardDTOs;
}
