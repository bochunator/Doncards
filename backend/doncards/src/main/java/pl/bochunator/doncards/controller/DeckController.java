package pl.bochunator.doncards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.dto.common.deck.DeckDTO;
import pl.bochunator.doncards.dto.request.deck.CreateDeckRequestDTO;
import pl.bochunator.doncards.model.Deck;
import pl.bochunator.doncards.service.DeckService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @PostMapping("/user/deck/create")
    public ResponseEntity<Deck> createDeck(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @RequestBody CreateDeckRequestDTO createDeckRequestDTO) {
        return ResponseEntity.ok(deckService.createDeck(jwt, createDeckRequestDTO));
    }

    @GetMapping("/auth/decks/cards")
    public ResponseEntity<List<DeckDTO>> getDecks(@RequestParam(required = false) Integer page) {
        return ResponseEntity.ok(deckService.getDecks(page));
    }

}
