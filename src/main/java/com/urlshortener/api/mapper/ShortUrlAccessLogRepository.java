package com.urlshortener.api.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlshortener.api.entity.ShortUrlAccessLog;

@Repository
public interface ShortUrlAccessLogRepository extends JpaRepository<ShortUrlAccessLog, String> {

}
