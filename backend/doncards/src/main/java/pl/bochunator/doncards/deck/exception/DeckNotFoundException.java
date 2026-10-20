package pl.bochunator.doncards.deck.exception;

public class DeckNotFoundException extends RuntimeException {

    public DeckNotFoundException(Long id) {
        super(String.format("Not found deck with id = '%s'.", id));
    }
}
