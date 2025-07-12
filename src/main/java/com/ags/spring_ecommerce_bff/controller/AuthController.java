package com.ags.spring_ecommerce_bff.controller;

import com.ags.spring_ecommerce_bff.dto.request.AuthRequestDto;
import com.ags.spring_ecommerce_bff.dto.request.LogoutRequestDto;
import com.ags.spring_ecommerce_bff.dto.request.RefreshTokenRequest;
import com.ags.spring_ecommerce_bff.dto.response.AuthResponseDto;
import com.ags.spring_ecommerce_bff.service.AuthService;
import com.ags.spring_ecommerce_bff.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentication and Authorization APIs")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final SessionService sessionService;

  @PostMapping
  @Operation(
      summary = "Authenticate user",
      description = "Authenticate a user and return JWT tokens")
  public ResponseEntity<AuthResponseDto> authenticateUser(
      @Valid @RequestBody AuthRequestDto authRequest) {
    var authResponse = authService.authenticateUser(authRequest);
    return ResponseEntity.ok(authResponse);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshTokenRequest request) {
    AuthResponseDto response = authService.refreshToken(request.getRefreshToken());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequestDto request) {
    sessionService.logoutSession(request.getRefreshToken());

    return ResponseEntity.ok().build();
  }
}
