package com.urlshortener.api.services;

import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlshortener.api.constants.AppConstants;
import com.urlshortener.api.dto.ShortenRequestDto;
import com.urlshortener.api.entity.UrlMapping;
import com.urlshortener.api.mapper.UrlMasterRepository;
import com.urlshortener.api.utils.CheckCache;
import com.urlshortener.api.utils.CheckDb;
import com.urlshortener.api.utils.NanoIdUtils;
import com.urlshortener.api.validators.UrlSanitizer;
import com.urlshortener.api.validators.UrlValidator;
import com.urlshortener.api.utils.UrlEncoder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UrlMasterService {
    @Autowired
    UrlMasterRepository urlMasterRepository;

    @Autowired
    private UrlValidator urlValidator;

    @Autowired
    private UrlSanitizer urlSanitizer;

    @Autowired
    private UrlEncoder urlEncoder;

    @Autowired
    private UrlAccessLogService urlAccessLogService;

    @Autowired
    private CheckCache checkCache;

    @Autowired
    private CheckDb checkDb;

    public UrlMapping createShortUrl(ShortenRequestDto request) {
        // Step 1: Sanitize inputs
        String sanitizedUrl = urlSanitizer.sanitizeUrl(request.getLongUrl());
        String sanitizedAlias = urlSanitizer.sanitizeCustomAlias(request.getCustomAlias());

        // Step 2: Validate inputs
        urlValidator.validateUrl(sanitizedUrl);

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(sanitizedUrl);
        urlMapping.setUserId(request.getUserId());

        String shortUrl;
        if (sanitizedAlias != null) {
            // Validate the sanitized alias
            urlValidator.validateCustomAlias(sanitizedAlias);

            // Step 3: Encode the alias for URL safety
            String encodedAlias = urlEncoder.encodeCustomAlias(sanitizedAlias);

            // Check if the encoded alias is already in use
            if (urlMasterRepository.findByShortUrl(encodedAlias).isPresent()) {
                throw new IllegalArgumentException("This custom alias is already in use");
            }
            shortUrl = encodedAlias;
        } else {
            // Generate random short URL
            shortUrl = generateUniqueShortUrl();
        }

        urlMapping.setShortUrl(shortUrl);
        UrlMapping savedMapping = urlMasterRepository.save(urlMapping);

        // Save to Redis cache
        // redisCacheService.saveUrlMapping(shortUrl, urlMapping.getLongUrl());

        return savedMapping;
    }

    private String generateUniqueShortUrl() {
        try {
            // Try to use the strongest secure random available
            return generateWithSecureRandom(SecureRandom.getInstanceStrong());
        } catch (Exception e) {
            log.error("Error generating secure random number, falling back to default SecureRandom", e);
            // Fallback to basic SecureRandom if getInstanceStrong() fails
            return generateWithSecureRandom(new SecureRandom());
        }
    }

    private String generateWithSecureRandom(SecureRandom random) {
        Integer retry_counter = 0;
        while (true) {
            String shortUrl = NanoIdUtils.randomNanoId(
                    random,
                    AppConstants.CHARS_TO_GENERATE_SHORT_URL.toCharArray(),
                    AppConstants.SHORT_URL_LENGTH);

            if (urlMasterRepository.findByShortUrl(shortUrl).isEmpty()) {
                return shortUrl;
            }

            retry_counter++;
            if (retry_counter > AppConstants.MAX_RETRY_COUNT_FOR_UNIQUE_SHORT_URL) {
                throw new RuntimeException("Failed to generate a unique short URL after "
                        + AppConstants.MAX_RETRY_COUNT_FOR_UNIQUE_SHORT_URL + " attempts");
            }
        }
    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        try {
            // First check Redis cache
            UrlMapping urlMapping = checkCache.checkRedisCacheForShortUrl(shortUrl);

            // If not in cache, proceed with database lookup
            if (urlMapping == null) {
                urlMapping = checkDb.checkDbForShortUrl(shortUrl);
            }

            // Log access asynchronously
            logUrlAccess(shortUrl);

            return urlMapping;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid short URL: " + e.getMessage());
        }
    }

    public void logUrlAccess(String shortUrl) {
        CompletableFuture.runAsync(() -> {
            try {
                urlAccessLogService.logUrlAccess(shortUrl);
            } catch (Exception e) {
                log.error("Error logging URL access: {}", e.getMessage());
            }
        });
    }

    public void validateShortUrl(String shortUrl) {
        urlValidator.validateCustomAlias(shortUrl);
    }
}
