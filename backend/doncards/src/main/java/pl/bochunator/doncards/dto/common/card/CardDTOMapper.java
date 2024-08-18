package pl.bochunator.doncards.dto.common.card;

import pl.bochunator.doncards.model.Card;

import java.util.Set;

public class CardDTOMapper {

    public Card mapToCard(CardDTO cardDTO) {
        return Card
                .builder()
                .term(cardDTO.getTerm())
                .translation(cardDTO.getTranslation())
                .build();
    }

}
