package pl.bochunator.doncards.dto.deck;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import pl.bochunator.doncards.model.Deck;

import java.util.List;

public class DeckDTOMapper {

    private DeckDTOMapper() {
    }

    public static Page<DeckDetailsDTO> mapToDeckDetailsDTOPage(Page<Deck> deckPage) {
        List<DeckDetailsDTO> deckDetailsDTOs = deckPage.stream()
                .map(DeckDTOMapper::mapToDeckDetailsDTO)
                .toList();
        return new PageImpl<>(deckDetailsDTOs, deckPage.getPageable(), deckPage.getTotalElements());
    }

    public static DeckDetailsDTO mapToDeckDetailsDTO(Deck deck) {
        return DeckDetailsDTO.builder()
                .deckId(deck.getDeckId())
                .authorId(deck.getAuthor().getUserId())
                .authorName(deck.getAuthor().getUsername())
                .name(deck.getName())
                .description(deck.getDescription())
                .cards(deck.getCards())
                .build();
    }

    public static Page<DeckSummaryDTO> mapToDeckSummaryDTOPage(Page<Deck> deckPage) {
        List<DeckSummaryDTO> deckSummaryDTOs = deckPage.stream()
                .map(DeckDTOMapper::mapToDeckSummaryDTO)
                .toList();
        return new PageImpl<>(deckSummaryDTOs, deckPage.getPageable(), deckPage.getTotalElements());
    }

    public static DeckSummaryDTO mapToDeckSummaryDTO(Deck deck) {
        return DeckSummaryDTO.builder()
                .deckId(deck.getDeckId())
                .name(deck.getName())
                .description(deck.getDescription())
                .cards(deck.getCards())
                .build();
    }
}
