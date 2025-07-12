package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.config.security.JwtConfig;
import com.ags.spring_ecommerce_bff.dto.internal.UserSessionDto;
import com.ags.spring_ecommerce_bff.entity.User;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final RedisTemplate<String, Object> redisTemplate;
  private final JwtConfig jwtConfig;
  private final TokenService tokenService;

  private static final String SESSION_PREFIX = "user:session:";
  private static final String REFRESH_TOKEN_PREFIX = "refresh:token:";

  public void createSession(User user, String refreshTokenId) {
    UserSessionDto session =
        new UserSessionDto(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRole(),
            new Date(),
            refreshTokenId);

    String sessionKey = SESSION_PREFIX + user.getId();
    redisTemplate
        .opsForValue()
        .set(sessionKey, session, Duration.ofMillis(jwtConfig.getExpirationRefreshToken()));

    // Mapear refresh token ID para user ID
    String refreshKey = REFRESH_TOKEN_PREFIX + refreshTokenId;
    redisTemplate
        .opsForValue()
        .set(refreshKey, user.getId(), Duration.ofMillis(jwtConfig.getExpirationRefreshToken()));
  }

  public Optional<UserSessionDto> getSession(UUID userId) {
    String sessionKey = SESSION_PREFIX + userId;
    UserSessionDto session = (UserSessionDto) redisTemplate.opsForValue().get(sessionKey);
    return Optional.ofNullable(session);
  }

  public void updateLastActivity(UUID userId) {
    getSession(userId)
        .ifPresent(
            session -> {
              session.setLastActivity(new Date());
              String sessionKey = SESSION_PREFIX + userId;
              redisTemplate.opsForValue().set(sessionKey, session);
            });
  }

  public void logoutSession(String refreshToken) {
    var userId = tokenService.extractUserIdFromToken(refreshToken);

    getSession(userId)
        .ifPresent(
            session -> {
              invalidateSession(userId);
            });
  }

  public void invalidateSession(UUID userId) {
    getSession(userId)
        .ifPresent(
            session -> {
              String sessionKey = SESSION_PREFIX + userId;
              String refreshKey = REFRESH_TOKEN_PREFIX + session.getRefreshTokenId();

              redisTemplate.delete(sessionKey);
              redisTemplate.delete(refreshKey);
            });
  }

  public Optional<String> getUserIdByRefreshToken(String refreshTokenId) {
    String refreshKey = REFRESH_TOKEN_PREFIX + refreshTokenId;
    String userId = (String) redisTemplate.opsForValue().get(refreshKey);
    return Optional.ofNullable(userId);
  }
}
