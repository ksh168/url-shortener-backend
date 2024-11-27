package com.urlshortener.api.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlshortener.api.entity.UrlMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlMasterRepository extends JpaRepository<UrlMapping, String> {

    List<UrlMapping> findByUserId(UUID userId);

    Optional<UrlMapping> findByShortUrl(String shortUrl);

}
