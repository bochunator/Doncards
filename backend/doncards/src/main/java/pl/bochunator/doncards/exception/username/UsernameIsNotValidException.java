package pl.bochunator.doncards.exception.username;

public class UsernameIsNotValidException extends RuntimeException {

    public UsernameIsNotValidException(String username) {
        super(String.format("The username '%s' you provided is not valid. Please ensure it follows the correct format.", username));
    }
}
