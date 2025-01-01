package com.urlshortener.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
public class HealthController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/api/health/stats")
    private ResponseEntity<String> workingStatus() {
        return new ResponseEntity<>("Hello There!", HttpStatus.OK);
    }

    @GetMapping("/api/health/db")
    private ResponseEntity<String> dbStatus() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return new ResponseEntity<>("Database connection successful!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Database connection failed: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
