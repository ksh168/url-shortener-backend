package com.urlshortener.api.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class ShortenRequestDto {
    @NotBlank(message = "Long URL cannot be empty")
    @URL(message = "Please provide a valid URL")
    @Size(max = 2048, message = "URL length cannot exceed 2048 characters")
    private String longUrl;

    @NotNull(message = "User ID is required")
    private UUID userId;

    @Nullable
    @Size(min = 3, max = 32, message = "Custom alias must be at least 3 and max 32 characters long")
    private String customAlias;
}
