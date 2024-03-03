package com.urlshortener.api.controller.shortener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.api.entity.UrlMaster;
import com.urlshortener.api.services.UrlMasterService;

@RestController
public class ShortenerController {

    @Autowired
    UrlMasterService UrlMasterService;

    @GetMapping("/allUrls")
    private ResponseEntity<List<UrlMaster>> getAllUrlsForUserId(@RequestParam(name = "userId", required = false) Long userId) {
        return new ResponseEntity<>(UrlMasterService.getAllUrlsForUserId(userId), HttpStatus.OK);
    }
}
