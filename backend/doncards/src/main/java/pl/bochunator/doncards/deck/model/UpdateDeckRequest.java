package pl.bochunator.doncards.deck.model;

public record UpdateDeckRequest(
        String name,
        String description
) {
}
