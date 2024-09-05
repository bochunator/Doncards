package pl.bochunator.doncards.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.dto.applicationuser.ApplicationUserWithDecksDTO;
import pl.bochunator.doncards.dto.deck.DeckCreateDTO;
import pl.bochunator.doncards.dto.deck.DeckDetailsDTO;
import pl.bochunator.doncards.dto.deck.DeckSummaryDTO;
import pl.bochunator.doncards.model.Deck;
import pl.bochunator.doncards.service.DeckService;

@RestController
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @PostMapping("/decks")
    public ResponseEntity<Deck> createDeck(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @RequestBody DeckCreateDTO deckCreateDTO) {
        System.out.println("RAZ");
        Deck createdDeck = deckService.createDeck(jwt, deckCreateDTO);
        System.out.println("DWA");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeck);
    }

    @GetMapping("/decks")
    public ResponseEntity<Page<DeckDetailsDTO>> getDetailsDecksDTOs(
            @RequestParam(required = false) Integer page) {
        Page<DeckDetailsDTO> deckDetailsDTOs = deckService.getDetailsDecksDTOs(page);
        return ResponseEntity.ok(deckDetailsDTOs);
    }

    @GetMapping("/users/{userId}/with-decks")
    public ResponseEntity<ApplicationUserWithDecksDTO> getApplicationUserWithDecksByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getApplicationUserWithDecksByUserId(userId));
    }

    @GetMapping("/users/{userId}/decks")
    public ResponseEntity<Page<DeckSummaryDTO>> getNextDecksByUserId(@PathVariable Long userId, @RequestParam(required = false) Integer page) {
        return ResponseEntity.ok(deckService.getNextDecksByUserId(userId, page));
    }

    @GetMapping("/decks/{deckId}")
    public ResponseEntity<Deck> getDeckByDeckId(@PathVariable Long deckId) {
        Deck deck = deckService.getDeckByDeckId(deckId);
        Hibernate.initialize(deck.getAuthor());
        return ResponseEntity.ok(deck);
    }
}
