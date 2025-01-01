package com.urlshortener.api.controller.shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.api.dto.ShortenRequestDto;
import com.urlshortener.api.dto.UrlShortenResponse;
import com.urlshortener.api.entity.UrlMapping;
import com.urlshortener.api.services.UrlMasterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;

@RestController
public class ShortenerController {

    @Autowired
    UrlMasterService UrlMasterService;

    @PostMapping("/api/shorten")
    public ResponseEntity<UrlShortenResponse> shortenUrl(@Valid @RequestBody ShortenRequestDto shortUrlRequest,
            HttpServletRequest request) {
        try {
            UrlMapping shortened = UrlMasterService.createShortUrl(shortUrlRequest);
            String baseUrl = getBaseUrl(request);
            String fullShortUrl = baseUrl + "/" + shortened.getShortUrl();

            UrlShortenResponse response = new UrlShortenResponse(
                    true,
                    null,
                    fullShortUrl);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            UrlShortenResponse response = new UrlShortenResponse(
                    false,
                    e.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable(name = "shortUrl") String shortUrl) {
        try {
            UrlMasterService.validateShortUrl(shortUrl);

            UrlMapping urlMapping = UrlMasterService.getOriginalUrl(shortUrl);
            if (urlMapping != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", urlMapping.getLongUrl());
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }

            return new ResponseEntity<>(
                    new UrlShortenResponse(false, "Short URL not found", null),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new UrlShortenResponse(false, "Error processing request: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName();
    }
}
