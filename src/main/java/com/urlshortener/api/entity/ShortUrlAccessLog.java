package com.urlshortener.api.entity;

import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "short_url_access_log")
@Data
public class ShortUrlAccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "access_at")
    private ZonedDateTime accessAt = ZonedDateTime.now();
}