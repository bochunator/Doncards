package pl.bochunator.doncards.card.exception;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(Long id) {
        super(String.format("Not found card with id = '%s'.", id));
    }
}
