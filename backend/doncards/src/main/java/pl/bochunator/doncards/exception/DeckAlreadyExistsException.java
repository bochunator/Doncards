package pl.bochunator.doncards.exception;

public class DeckAlreadyExistsException extends RuntimeException {

    public DeckAlreadyExistsException(String authorName, String deckName) {
        super(String.format("Deck with the name '%s' already exists for author '%s'.", deckName, authorName));
    }

}
