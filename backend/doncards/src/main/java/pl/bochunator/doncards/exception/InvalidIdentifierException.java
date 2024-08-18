package pl.bochunator.doncards.exception;

public class InvalidIdentifierException extends RuntimeException {

    public InvalidIdentifierException(String identifier) {
        super(String.format("The identifier '%s' provided is not a valid email or username.", identifier));
    }

}
