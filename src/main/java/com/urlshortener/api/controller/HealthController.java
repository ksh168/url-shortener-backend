package com.urlshortener.api.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
public class HealthController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/api/health/redis")
    public ResponseEntity<String> redisHealth() {
        try {
            String key = "health-check";
            redisTemplate.opsForValue().set(key, "OK", Duration.ofSeconds(1));
            String value = redisTemplate.opsForValue().get(key);
            if ("OK".equals(value)) {
                return new ResponseEntity<>("Redis connection successful!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Redis connection failed: Unable to verify test value",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Redis connection failed: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
