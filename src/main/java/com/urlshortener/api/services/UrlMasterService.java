package com.urlshortener.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlshortener.api.entity.UrlMaster;
import com.urlshortener.api.mapper.UrlMasterRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UrlMasterService {
    @Autowired
    UrlMasterRepository urlMasterRepository;

    public List<UrlMaster> getAllUrlsForUserId(Long userId) {
        if (userId == null) {
            return urlMasterRepository.findAll();
        }

        List<UrlMaster> result = urlMasterRepository.findByUserId(userId);
        if (!result.isEmpty()) {
            return result;
        } else {
            throw new EntityNotFoundException("user/users not found");
        }

    }
}
