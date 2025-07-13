package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.config.security.JwtConfig;
import com.ags.spring_ecommerce_bff.dto.internal.UserSessionDto;
import com.ags.spring_ecommerce_bff.dto.request.AuthRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.AuthResponseDto;
import com.ags.spring_ecommerce_bff.entity.User;
import com.ags.spring_ecommerce_bff.exception.errors.InvalidCredentialsException;
import com.ags.spring_ecommerce_bff.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;
  private final JwtConfig jwtConfig;
  private final SessionService sessionService;

  public AuthResponseDto authenticateUser(AuthRequestDto authDto) {
    var user =
        userRepository
            .findByEmail(authDto.getEmail())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "User not found with email: " + authDto.getEmail()));

    if (!isAuthenticated(authDto.getPassword(), user.getPassword())) {
      throw new InvalidCredentialsException("Invalid username or password");
    }

    return authenticateResponseBuilder(user);
  }

  private Boolean isAuthenticated(String loginPassword, String userPassword) {
    return passwordEncoder.matches(loginPassword, userPassword);
  }

  private AuthResponseDto authenticateResponseBuilder(User user) {
    Pair<String, String> accessTokenPair = tokenService.generateAccessToken(user);
    var token = accessTokenPair.getFirst();
    var tokenId = accessTokenPair.getSecond();

    Pair<String, String> refreshTokenPair = tokenService.generateRefreshToken(user);
    var refreshToken = refreshTokenPair.getFirst();
    var refreshTokenId = refreshTokenPair.getSecond();

    sessionService.createSession(user, tokenId, refreshTokenId);

    return AuthResponseDto.builder()
        .name(user.getEmail())
        .accessToken(token)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthResponseDto refreshToken(String refreshToken) {
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));

    try {
      var claims =
          Jwts.parser().verifyWith(key).build().parseSignedClaims(refreshToken).getPayload();

      var tokenType = claims.get("tokenType", String.class);
      if (!"refresh".equals(tokenType)) {
        throw new InvalidCredentialsException("Token inválido");
      }

      var userEmail = claims.getSubject();
      User user =
          userRepository
              .findByEmail(userEmail)
              .orElseThrow(() -> new InvalidCredentialsException("Usuário não encontrado"));

      var refreshTokenId = claims.getId();
      Optional<UserSessionDto> session = sessionService.getSession(user.getId());
      if (session.isEmpty() || !session.get().getRefreshTokenId().equals(refreshTokenId)) {
        throw new InvalidCredentialsException("Sessão inválida ou expirada");
      }

      return authenticateResponseBuilder(user);

    } catch (Exception e) {
      throw new InvalidCredentialsException("Token de atualização inválido ou expirado");
    }
  }
}
