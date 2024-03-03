package com.urlshortener.api.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlshortener.api.entity.UrlMaster;

import java.util.List;

@Repository
public interface UrlMasterRepository extends JpaRepository<UrlMaster, String> {

    List<UrlMaster> findByUserId(Long userId);

}
