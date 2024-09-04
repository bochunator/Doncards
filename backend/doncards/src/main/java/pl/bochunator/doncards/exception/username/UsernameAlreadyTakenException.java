package pl.bochunator.doncards.exception.username;

public class UsernameAlreadyTakenException extends RuntimeException {

    public UsernameAlreadyTakenException(String username) {
        super(String.format("The username '%s' you provided is already in use.", username));
    }
}
