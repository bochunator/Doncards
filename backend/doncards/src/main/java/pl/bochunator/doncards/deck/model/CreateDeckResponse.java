package pl.bochunator.doncards.deck.model;

public record CreateDeckResponse(
        Long id
) {

    public static CreateDeckResponse from(Deck deck) {
        return new CreateDeckResponse(deck.getId());
    }
}
