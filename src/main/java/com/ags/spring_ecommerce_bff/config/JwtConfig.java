package com.ags.spring_ecommerce_bff.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
  private String secret;
  private long expirationAccessToken;
  private long expirationRefreshToken;
}
