package pl.bochunator.doncards.exception.username;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException(String username) {
        super(String.format("User with username '%s' was not found.", username));
    }
}
