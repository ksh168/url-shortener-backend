package com.urlshortener.api.controller.shortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenerController {
    @GetMapping("/shorten")
    private ResponseEntity<String> wokingStatus() {
        return new ResponseEntity<>("Sup bro!", HttpStatus.OK);
    }
}
