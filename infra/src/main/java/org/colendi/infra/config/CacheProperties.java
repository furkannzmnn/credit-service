package org.colendi.infra.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "redis.lock")
@Configuration
@Data
public class CacheProperties {

    private int expirationTime;
}
