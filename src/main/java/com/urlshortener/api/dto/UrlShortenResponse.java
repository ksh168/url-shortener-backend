package com.urlshortener.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlShortenResponse {
    private boolean success = false;
    private String message = null;
    private String shortUrl = null;
}
