package com.ags.spring_ecommerce_bff.exception.errors;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
  public JwtAuthenticationException(String message) {
    super(message);
  }
}
