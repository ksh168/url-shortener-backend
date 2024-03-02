package com.urlshortener.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {
    @GetMapping("/health/stats")
    private ResponseEntity<String> wokingStatus() {
        return new ResponseEntity<>("Hello There!", HttpStatus.OK);
    }
}
