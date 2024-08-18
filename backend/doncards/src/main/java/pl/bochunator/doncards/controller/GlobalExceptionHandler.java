package pl.bochunator.doncards.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.bochunator.doncards.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EmailIsNotValidException.class,
            UsernameIsNotValidException.class,
            PasswordIsNotValidForRegistrationException.class,
            PasswordIsNotValidForLoginException.class,
            InvalidIdentifierException.class
    })
    public ResponseEntity<String> handleValidationExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            EmailAlreadyTakenException.class,
            UsernameAlreadyTakenException.class,
            DeckAlreadyExistsException.class
    })
    public ResponseEntity<String> handleConflictExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            BadCredentialsException.class
    })
    public ResponseEntity<String> handleBadCredentialsException() {
        String customMessage = "Invalid username or password.";
        return new ResponseEntity<>(customMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            UserRoleNotFoundException.class //,
            // DatabaseConnectionException.class,
            // EntityNotFoundException.class
    })
    public ResponseEntity<String> handleSystemExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            EmailNotFoundException.class
    })
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
