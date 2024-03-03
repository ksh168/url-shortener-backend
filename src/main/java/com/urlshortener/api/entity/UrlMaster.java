package com.urlshortener.api.entity;

import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "urlmaster")
@AllArgsConstructor
public class UrlMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "longurl")
    private String longUrl;

    @Column(name = "shorturl", unique = true)
    private String shortUrl;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "createdat")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(name = "updatedat")
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    @Column(name = "expiresat")
    private ZonedDateTime expiresAt;

    @Column(name = "isactive")
    private Boolean isActive = true;
}