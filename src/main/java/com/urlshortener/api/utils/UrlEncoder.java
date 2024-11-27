package com.urlshortener.api.utils;

import org.springframework.stereotype.Component;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class UrlEncoder {
    
    public String encodeCustomAlias(String alias) {
        if (alias == null) {
            return null;
        }
        return URLEncoder.encode(alias, StandardCharsets.UTF_8);
    }
}
