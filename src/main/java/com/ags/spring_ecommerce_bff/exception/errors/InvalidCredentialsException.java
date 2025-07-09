package com.ags.spring_ecommerce_bff.exception.errors;

public class InvalidCredentialsException extends RuntimeException {
  public InvalidCredentialsException(String message) {
    super(message);
  }
}
