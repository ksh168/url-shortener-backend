package com.urlshortener.api.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class AppConstants {
    public static final int SHORT_URL_LENGTH = 6;
    public static final String CHARS_TO_GENERATE_SHORT_URL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final int MAX_RETRY_COUNT_FOR_UNIQUE_SHORT_URL = 5;
    public static final String DEFAULT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)" + // protocol
                    "((([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,63})|" + // domain name
                    "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))" + // OR ip address
                    "(:\\d{1,5})?" + // optional port
                    "(/[a-zA-Z0-9._/-]*)*" + // path
                    "(\\?[a-zA-Z0-9._&=+%-]*)?"); // query parameters

    public static final Pattern CUSTOM_ALIAS_PATTERN = Pattern.compile(
            "^[-_a-zA-Z0-9]" + // Start with hyphen, underscore, or alphanumeric
                    "[-_a-zA-Z0-9]*" + // Allow any number of hyphen, underscore, alphanumeric
                    "[-_a-zA-Z0-9]$" // End with hyphen, underscore, or alphanumeric
    );

    public static final Set<String> RESERVED_ALIASES = new HashSet<>(Arrays.asList(
            "admin", "api", "login", "logout", "signup", "register", "dashboard",
            "settings", "profile", "about", "contact", "terms", "privacy", "help",
            "support", "static", "images", "css", "js", "favicon", "shorten"));

    public static final Set<String> ALLOWED_PROTOCOLS = new HashSet<>(Arrays.asList("http", "https"));
    public static final Set<String> BLOCKED_DOMAINS = new HashSet<>(Arrays.asList(
            "example.com",
            "malicious-site.com"
    // Add more blocked domains as needed
    ));

    
    // Add other application constants here
}
