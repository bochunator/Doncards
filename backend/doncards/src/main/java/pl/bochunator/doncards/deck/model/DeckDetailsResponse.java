package pl.bochunator.doncards.deck.model;

import pl.bochunator.doncards.appuser.model.AppUserSummaryResponse;
import pl.bochunator.doncards.card.model.CardResponse;

import java.time.LocalDateTime;
import java.util.List;


public record DeckDetailsResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        Long viewCount,
        Long cardCount,
        AppUserSummaryResponse appUserSummaryResponse,
        List<CardResponse> cardResponses
) {

    public static DeckDetailsResponse from(
            Deck deck,
            AppUserSummaryResponse appUserSummaryResponse,
            List<CardResponse> cardResponses,
            Long cardCount
    ) {
        return new DeckDetailsResponse(
                deck.getId(),
                deck.getName(),
                deck.getDescription(),
                deck.getCreatedAt(),
                deck.getViewCount(),
                cardCount,
                appUserSummaryResponse,
                cardResponses
        );
    }
}
