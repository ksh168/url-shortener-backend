package com.urlshortener.api.validators;

import org.springframework.stereotype.Component;

@Component
public class UrlSanitizer {
    
    public String sanitizeCustomAlias(String alias) {
        if (alias == null) {
            return null;
        }
        return alias.trim();
    }

    public String sanitizeUrl(String url) {
        if (url == null) {
            return null;
        }
        return url.trim();
    }
}
