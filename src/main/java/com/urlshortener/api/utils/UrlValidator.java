package com.urlshortener.api.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class UrlValidator {
    private static final int MAX_URL_LENGTH = 2048;
    private static final int MIN_ALIAS_LENGTH = 3;
    private static final int MAX_ALIAS_LENGTH = 30;

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)" + // protocol
                    "((([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,63})|" + // domain name
                    "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))" + // OR ip address
                    "(:\\d{1,5})?" + // optional port
                    "(/[a-zA-Z0-9._/-]*)*" + // path
                    "(\\?[a-zA-Z0-9._&=+%-]*)?"); // query parameters

    // Updated pattern to allow starting with underscore or hyphen
    private static final Pattern CUSTOM_ALIAS_PATTERN = Pattern.compile(
            "^[-_a-zA-Z0-9]" + // Start with hyphen, underscore, or alphanumeric
                    "[-_a-zA-Z0-9 ]*" + // Allow any number of hyphen, underscore, alphanumeric, and space in middle
                    "[-_a-zA-Z0-9]$" // End with hyphen, underscore, or alphanumeric
    );

    private static final Set<String> RESERVED_ALIASES = new HashSet<>(Arrays.asList(
            "admin", "api", "login", "logout", "signup", "register", "dashboard",
            "settings", "profile", "about", "contact", "terms", "privacy", "help",
            "support", "static", "images", "css", "js", "favicon", "shorten"));

    private static final Set<String> ALLOWED_PROTOCOLS = new HashSet<>(Arrays.asList("http", "https"));
    private static final Set<String> BLOCKED_DOMAINS = new HashSet<>(Arrays.asList(
            "example.com",
            "malicious-site.com"
    // Add more blocked domains as needed
    ));

    public void validateUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        if (url.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("URL exceeds maximum length of " + MAX_URL_LENGTH + " characters");
        }

        URL parsedUrl;
        try {
            parsedUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format: " + e.getMessage());
        }

        // Validate protocol
        String protocol = parsedUrl.getProtocol().toLowerCase();
        if (!ALLOWED_PROTOCOLS.contains(protocol)) {
            throw new IllegalArgumentException("URL protocol must be HTTP or HTTPS");
        }

        // Check for blocked domains
        String host = parsedUrl.getHost().toLowerCase();
        if (BLOCKED_DOMAINS.contains(host)) {
            throw new IllegalArgumentException("This domain is not allowed");
        }

        // Validate URL pattern
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new IllegalArgumentException("URL contains invalid characters or format");
        }
    }

    public void validateCustomAlias(String alias) {
        if (alias == null || alias.isEmpty()) {
            throw new IllegalArgumentException("Custom alias cannot be null or empty");
        }

        if (alias.length() < MIN_ALIAS_LENGTH) {
            throw new IllegalArgumentException(
                    "Custom alias must be at least " + MIN_ALIAS_LENGTH + " characters long");
        }
        if (alias.length() > MAX_ALIAS_LENGTH) {
            throw new IllegalArgumentException("Custom alias cannot exceed " + MAX_ALIAS_LENGTH + " characters");
        }

        if (!CUSTOM_ALIAS_PATTERN.matcher(alias).matches()) {
            throw new IllegalArgumentException(
                    "Custom alias can only contain letters, numbers, hyphens, underscores, and spaces");
        }

        if (RESERVED_ALIASES.contains(alias.toLowerCase())) {
            throw new IllegalArgumentException("This custom alias is reserved and cannot be used");
        }
    }
}
