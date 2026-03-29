package com.ultimate.commonlib.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix="jwt")
public class JwtConfig
{
    private String secret;
    private long accessExpiration;
}

