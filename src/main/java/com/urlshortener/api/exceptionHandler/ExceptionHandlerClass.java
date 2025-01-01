package com.urlshortener.api.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.urlshortener.api.dto.UrlShortenResponse;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerClass {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<UrlShortenResponse> exception(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new UrlShortenResponse(false, exception.getMessage(), null),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InstanceAlreadyExistsException.class)
    public ResponseEntity<UrlShortenResponse> alreadyExistException(InstanceAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new UrlShortenResponse(false, exception.getMessage(), null),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UrlShortenResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(
                new UrlShortenResponse(false, "Validation failed: " + errors.toString(), null),
                HttpStatus.BAD_REQUEST);
    }
}
