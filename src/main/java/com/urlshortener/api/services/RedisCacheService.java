package com.urlshortener.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import java.time.Duration;

@Service
@Slf4j
public class RedisCacheService {

    private static final String KEY_PREFIX = "url-shortener-";

    @Value("${spring.cache.redis.time-to-live}")
    private long defaultTtl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void saveUrlMapping(String shortUrl, String longUrl) {
        saveUrlMapping(shortUrl, longUrl, defaultTtl);
    }

    public void saveUrlMapping(String shortUrl, String longUrl, long timeoutInSeconds) {
        try {
            String key = KEY_PREFIX + shortUrl;
            redisTemplate.opsForValue().set(key, longUrl, Duration.ofSeconds(timeoutInSeconds));
            log.info("Saved URL mapping to Redis: {} -> {} with TTL: {}s", shortUrl, longUrl, timeoutInSeconds);
        } catch (Exception e) {
            log.warn("Failed to save to Redis cache: {} - Cause: {}", e.getMessage(), 
                    e.getCause() != null ? e.getCause().getMessage() : "Unknown");
        }
    }

    public String getLongUrl(String shortUrl) {
        try {
            String key = KEY_PREFIX + shortUrl;
            String longUrl = redisTemplate.opsForValue().get(key);

            if (longUrl != null) {
                log.info("Retrieved URL mapping from Redis: {} -> {}", shortUrl, longUrl);
            } else {
                log.info("No URL mapping found for short URL: {}", shortUrl);
            }

            return longUrl;
        } catch (Exception e) {
            log.warn("Failed to retrieve from Redis cache: {} - Cause: {}", e.getMessage(), 
                    e.getCause() != null ? e.getCause().getMessage() : "Unknown");
            return null; // Return null instead of throwing exception
        }
    }
}