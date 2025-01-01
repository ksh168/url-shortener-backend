package com.urlshortener.api.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.urlshortener.api.entity.UrlMapping;
import com.urlshortener.api.mapper.UrlMasterRepository;
import com.urlshortener.api.services.RedisCacheService;
import com.urlshortener.api.validators.UrlSanitizer;
import com.urlshortener.api.validators.UrlValidator;

@Component
public class CheckDb {
    @Autowired
    private UrlMasterRepository urlMasterRepository;

    @Autowired
    private UrlSanitizer urlSanitizer;

    @Autowired
    private UrlValidator urlValidator;

    @Autowired
    private UrlEncoder urlEncoder;

    @Autowired
    private RedisCacheService redisCacheService;

    public UrlMapping checkDbForShortUrl(String shortUrl) {
        try {

            // TODO: check if this decoding is necessary

            // First URL decode to handle encoded characters
            String decodedUrl = URLDecoder.decode(shortUrl, StandardCharsets.UTF_8);

            // Then sanitize
            String sanitizedShortUrl = urlSanitizer.sanitizeCustomAlias(decodedUrl);

            // Then validate
            urlValidator.validateCustomAlias(sanitizedShortUrl);

            // Finally encode for database lookup
            String encodedShortUrl = urlEncoder.encodeCustomAlias(sanitizedShortUrl);

            UrlMapping urlMapping = urlMasterRepository.findByShortUrl(encodedShortUrl)
                    .orElse(null);

            if (urlMapping != null) {
                // Save to cache for future requests
                redisCacheService.saveUrlMapping(shortUrl, urlMapping.getLongUrl());
            } else {
                throw new IllegalArgumentException("Short URL not found in database");
            }

            return urlMapping;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid short URL: " + e.getMessage());
        }
    }
}
