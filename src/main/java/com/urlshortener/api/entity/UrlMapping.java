package com.urlshortener.api.entity;

import java.time.ZonedDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "url_mapping")
@Getter
@Setter
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_at")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    @Column(name = "expires_at")
    private ZonedDateTime expiresAt;// can be used later for setting expiry time

    @Column(name = "is_active")
    private Boolean isActive = true;
}