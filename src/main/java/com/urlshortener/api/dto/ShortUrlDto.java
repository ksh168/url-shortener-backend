package com.urlshortener.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortUrlDto {
    private String longUrl;
    private Long userId;
}