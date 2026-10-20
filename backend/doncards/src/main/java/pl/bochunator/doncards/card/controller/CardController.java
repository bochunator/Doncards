package pl.bochunator.doncards.card.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.card.model.CardRequest;
import pl.bochunator.doncards.card.model.CardResponse;
import pl.bochunator.doncards.card.service.CardService;

@RestController
@RequestMapping("/decks/{deckId}/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping // DeckPage
    @SecurityRequirement(name = "bearerAuth")
    public CardResponse create(
            @PathVariable Long deckId,
            @RequestBody CardRequest dto,
            @AuthenticationPrincipal Jwt jwt) {
        Long appUserId = Long.valueOf(jwt.getSubject());
        return cardService.create(deckId, appUserId, dto);
    }

    @PutMapping("/{cardId}") // DeckPage
    @SecurityRequirement(name = "bearerAuth")
    public CardResponse update(
            @PathVariable Long deckId,
            @PathVariable Long cardId,
            @RequestBody CardRequest dto,
            @AuthenticationPrincipal Jwt jwt) {
        Long appUserId = Long.valueOf(jwt.getSubject());
        return cardService.update(deckId, cardId, appUserId, dto);
    }

    @DeleteMapping("/{cardId}") // DeckPage
    @SecurityRequirement(name = "bearerAuth")
    public void delete(
            @PathVariable Long deckId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal Jwt jwt) {
        Long appUserId = Long.valueOf(jwt.getSubject());
        cardService.delete(deckId, cardId, appUserId);
    }
}
