package pl.bochunator.doncards.exception;

public class PasswordIsNotValidForRegistrationException extends RuntimeException {

    public PasswordIsNotValidForRegistrationException() {
        super("The password provided for registration is not valid. Please ensure it meets the required criteria.");
    }
}
