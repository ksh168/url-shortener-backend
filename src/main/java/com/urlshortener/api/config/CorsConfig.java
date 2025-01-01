package com.urlshortener.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // Add your React app URL
            .allowedMethods("GET", "POST")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
} 