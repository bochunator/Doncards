package pl.bochunator.doncards.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String email) {
        super(String.format("The email address '%s' was not found in our system.", email));
    }
}
