package com.urlshortener.api.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatEx = (InvalidFormatException) ex.getCause();
            if (invalidFormatEx.getTargetType() == UUID.class) {
                return ResponseEntity.badRequest().body("Invalid UUID format. Please provide a valid UUID");
            }
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
