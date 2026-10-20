package pl.bochunator.doncards.deck.model;

import java.time.LocalDateTime;

public record DeckSummaryResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        Long viewCount
) {

    public static DeckSummaryResponse from(Deck deck) {
        return new DeckSummaryResponse(
                deck.getId(),
                deck.getName(),
                deck.getDescription(),
                deck.getCreatedAt(),
                deck.getViewCount()
        );
    }
}
