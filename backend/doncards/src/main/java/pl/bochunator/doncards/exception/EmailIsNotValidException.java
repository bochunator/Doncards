package pl.bochunator.doncards.exception;

public class EmailIsNotValidException extends RuntimeException {

    public EmailIsNotValidException(String email) {
        super(String.format("The email address '%s' is invalid. Please ensure it follows the correct format.", email));
    }
}
