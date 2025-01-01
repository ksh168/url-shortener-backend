package com.urlshortener.api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private String minimumIdle;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private String maximumPoolSize;

    @Value("${spring.datasource.hikari.idle-timeout}")
    private String idleTimeout;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private String connectionTimeout;

    @Value("${spring.datasource.hikari.max-lifetime}")
    private String maxLifetime;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        // Connection pool settings
        config.setMinimumIdle(Integer.parseInt(minimumIdle));
        config.setMaximumPoolSize(Integer.parseInt(maximumPoolSize));
        config.setIdleTimeout(Integer.parseInt(idleTimeout));
        config.setConnectionTimeout(Integer.parseInt(connectionTimeout));
        config.setMaxLifetime(Integer.parseInt(maxLifetime));
        config.setDriverClassName(driverClassName);
        return config;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }
}