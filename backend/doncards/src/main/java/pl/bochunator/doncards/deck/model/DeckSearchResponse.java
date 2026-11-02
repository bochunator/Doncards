package pl.bochunator.doncards.deck.model;

public record DeckSearchResponse (
        Long id,
        String name
) {

    public static DeckSearchResponse from(Deck deck) {
        return new DeckSearchResponse(
                deck.getId(),
                deck.getName()
        );
    }
}