package pl.bochunator.doncards.appuser.exception;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException(String email) {
        super(String.format("The email '%s' you provided is already in use.", email));
    }
}
