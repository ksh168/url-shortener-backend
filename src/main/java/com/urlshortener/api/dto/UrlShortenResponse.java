package com.urlshortener.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlShortenResponse {
    private boolean success = false;
    private String message = null;
    private String shortUrl = null;
}
