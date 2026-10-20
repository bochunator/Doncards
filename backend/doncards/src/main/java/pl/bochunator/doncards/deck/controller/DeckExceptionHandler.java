package pl.bochunator.doncards.deck.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bochunator.doncards.deck.exception.DeckAccessDeniedException;
import pl.bochunator.doncards.deck.exception.DeckNotFoundException;

@RestControllerAdvice
public class DeckExceptionHandler {

    @ExceptionHandler(DeckNotFoundException.class)
    public ResponseEntity<String> handleDeckNotFound(DeckNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DeckAccessDeniedException.class)
    public ResponseEntity<String> handleDeckAccessDenied(DeckAccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }
}
