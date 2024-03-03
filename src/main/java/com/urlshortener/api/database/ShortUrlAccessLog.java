package com.urlshortener.api.database;

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
@Table(name = "public.public.shorturlaccesslog")
@AllArgsConstructor
public class ShortUrlAccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shorturl")
    private String shortUrl;

    @Column(name = "accessat")
    private ZonedDateTime accessAt = ZonedDateTime.now();

    @Column(name = "ipaddress")
    private String ipAddress;
}