package com.ags.spring_ecommerce_bff.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation Error"),
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid Parameter"),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access Denied"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
  DATA_CONFLICT(HttpStatus.CONFLICT, "Data Conflict"),
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

  private final HttpStatus status;
  private final String error;
}
