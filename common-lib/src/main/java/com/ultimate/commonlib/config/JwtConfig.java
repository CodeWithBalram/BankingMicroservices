package com.ultimate.commonlib.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration // 👈 This makes it a configuration bean automatically
@ConfigurationProperties(prefix="jwt")
@Setter
@Getter
public class JwtConfig
{
    private String secret;
    private long accessExpiration;
}

