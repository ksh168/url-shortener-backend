package com.urlshortener.api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.urlshortener.api.entity.UrlMapping;
import com.urlshortener.api.services.RedisCacheService;

@Component
public class CheckCache {
    @Autowired
    private RedisCacheService redisCacheService;

    public UrlMapping checkRedisCacheForShortUrl(String shortUrl) {
        // First check Redis cache
        String cachedLongUrl = redisCacheService.getLongUrl(shortUrl);
        if (cachedLongUrl != null) {
            UrlMapping urlMapping = new UrlMapping();
            urlMapping.setLongUrl(cachedLongUrl);
            urlMapping.setShortUrl(shortUrl);

            return urlMapping;
        }

        return null;
    }
}
