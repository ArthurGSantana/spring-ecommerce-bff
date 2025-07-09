package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.dto.request.AuthRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.AuthResponseDto;
import com.ags.spring_ecommerce_bff.exception.errors.InvalidCredentialsException;
import com.ags.spring_ecommerce_bff.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

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

    return AuthResponseDto.builder()
        .name(user.getName())
        .accessToken(tokenService.generateAccessToken(user))
        .refreshToken(tokenService.generateRefreshToken(user))
        .build();
  }

  private Boolean isAuthenticated(String loginPassword, String userPassword) {
    return passwordEncoder.matches(loginPassword, userPassword);
  }
}
