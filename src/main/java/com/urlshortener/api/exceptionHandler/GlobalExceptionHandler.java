package com.urlshortener.api.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.urlshortener.api.dto.UrlShortenResponse;

import java.util.UUID;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<UrlShortenResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message;
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatEx = (InvalidFormatException) ex.getCause();
            if (invalidFormatEx.getTargetType() == UUID.class) {
                message = "Invalid UUID format. Please provide a valid UUID";
            } else {
                message = "Invalid format: " + ex.getMessage();
            }
        } else {
            message = ex.getMessage();
        }
        
        return new ResponseEntity<>(
                new UrlShortenResponse(false, message, null),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UrlShortenResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                new UrlShortenResponse(false, "An unexpected error occurred: " + ex.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
