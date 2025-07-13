package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.config.security.JwtConfig;
import com.ags.spring_ecommerce_bff.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final JwtConfig jwtConfig;

  public Pair<String, String> generateAccessToken(User user) {
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationAccessToken());
    String tokenId = UUID.randomUUID().toString();

    var token =
        Jwts.builder()
            .subject(user.getEmail())
            .issuedAt(now)
            .expiration(expiryDate)
            .id(tokenId)
            .claim("userId", user.getId())
            .claim("roles", List.of(user.getRole()))
            .claim("tokenType", "access")
            .signWith(key)
            .compact();

    return Pair.of(token, tokenId);
  }

  public Pair<String, String> generateRefreshToken(User user) {
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationRefreshToken());
    String refreshTokenId = UUID.randomUUID().toString();

    var refreshToken =
        Jwts.builder()
            .subject(user.getEmail())
            .issuedAt(now)
            .expiration(expiryDate)
            .id(refreshTokenId)
            .claim("userId", user.getId())
            .claim("tokenType", "refresh")
            .signWith(key)
            .compact();

    return Pair.of(refreshToken, refreshTokenId);
  }

  public UUID extractUserIdFromToken(String token) {
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));

    var claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

    return UUID.fromString(claims.get("userId", String.class));
  }
}
