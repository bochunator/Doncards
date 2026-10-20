package pl.bochunator.doncards.deck.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.PageResponse;
import pl.bochunator.doncards.deck.model.*;
import pl.bochunator.doncards.deck.service.DeckService;

@RestController
@RequestMapping("/decks")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

    @PostMapping // CreateDeckPage
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new deck")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Deck created successfully"
            )
    })
    public CreateDeckResponse create(@RequestBody CreateDeckRequest dto, @AuthenticationPrincipal Jwt jwt) {
        Long appUserId = Long.valueOf(jwt.getSubject());
        return deckService.create(dto, appUserId);
    }

    @GetMapping // HomePage
    @Operation(summary = "Get decks with authors and up to 5 preview cards")
    public PageResponse<DeckDetailsResponse> findAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam DeckSort deckSort
    ) {
        return deckService.findAll(page, size, deckSort);
    }

    @GetMapping("/by-user") // PublicProfilePage
    @Operation(summary = "Get User decks up to 5 preview cards")
    public PageResponse<DeckDetailsResponse> findAllByAppUserId(
            @RequestParam Long appUserId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam DeckSort deckSort
    ) {
        return deckService.findAllByAppUserId(appUserId, page, size, deckSort);
    }

    @GetMapping("/{id}") // DeckPreviewModal (HomePage), DeckPage, LearnPage
    @Operation(summary = "Get deck")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deck retrieved successfully"
            )
    })
    public DeckDetailsResponse findById(@PathVariable Long id) {
        return deckService.findById(id);
    }

    @PutMapping("/{id}") // DeckPage
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update deck")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deck updated successfully"
            )
    })
    public DeckSummaryResponse update(@PathVariable Long id, @RequestBody UpdateDeckRequest dto, @AuthenticationPrincipal Jwt jwt) {
        Long appUserId = Long.valueOf(jwt.getSubject());
        return deckService.update(id, dto, appUserId);
    }

    @DeleteMapping("/{id}") // DeckPage
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete deck")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Deck deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Deck not found"
            )
    })
    public void delete(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long appUserId = Long.valueOf(jwt.getSubject());
        deckService.delete(id, appUserId);
    }
}
