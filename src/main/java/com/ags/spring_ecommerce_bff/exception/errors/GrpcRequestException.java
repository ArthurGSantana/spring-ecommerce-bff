package com.ags.spring_ecommerce_bff.exception.errors;

public class GrpcRequestException extends RuntimeException {
  public GrpcRequestException(String message) {
    super(message);
  }

  public GrpcRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
