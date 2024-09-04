package pl.bochunator.doncards.exception;

public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException(Long userId) {
        super(String.format("User with ID '%d' was not found.", userId));
    }
}
