package pl.bochunator.doncards.exception;

public class DeckNotFoundException extends RuntimeException {

    public DeckNotFoundException(Long deckId) {
        super(String.format("Deck with ID '%d' was not found.", deckId));
    }
}
