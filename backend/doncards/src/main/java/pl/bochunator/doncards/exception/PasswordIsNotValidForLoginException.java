package pl.bochunator.doncards.exception;

public class PasswordIsNotValidForLoginException extends RuntimeException {

    public PasswordIsNotValidForLoginException() {
        super("The password provided for login is not valid.");
    }
}
