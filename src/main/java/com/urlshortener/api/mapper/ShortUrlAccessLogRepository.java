package com.urlshortener.api.mapper;

import com.urlshortener.api.database.ShortUrlAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlAccessLogRepository extends JpaRepository<ShortUrlAccessLog, String> {

}
