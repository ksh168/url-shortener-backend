package com.urlshortener.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.urlshortener.api.entity.ShortUrlAccessLog;
import com.urlshortener.api.mapper.ShortUrlAccessLogRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UrlAccessLogService {

    @Autowired
    private ShortUrlAccessLogRepository shortUrlAccessLogRepository;

    @Async
    public void logUrlAccess(String shortUrl) {
        try {
            ShortUrlAccessLog accessLog = new ShortUrlAccessLog();
            accessLog.setShortUrl(shortUrl);

            shortUrlAccessLogRepository.save(accessLog);
        } catch (Exception e) {
            log.error("Error logging URL access: {}", e.getMessage());
        }
    }
}
