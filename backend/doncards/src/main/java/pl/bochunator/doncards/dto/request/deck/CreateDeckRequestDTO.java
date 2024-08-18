package pl.bochunator.doncards.dto.request.deck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.bochunator.doncards.dto.common.card.CardDTO;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CreateDeckRequestDTO {

    private String name;

    private String description;

    private List<CardDTO> cardDTOs;

}
