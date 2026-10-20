package pl.bochunator.doncards.deck.exception;

public class DeckAccessDeniedException extends RuntimeException {
    public DeckAccessDeniedException() {
        super ("You cannot edit this deck");
    }
}
