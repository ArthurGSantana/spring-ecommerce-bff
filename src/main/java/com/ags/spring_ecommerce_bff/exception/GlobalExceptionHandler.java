package com.ags.spring_ecommerce_bff.exception;

import jakarta.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
    return buildErrorResponse(ErrorType.VALIDATION_ERROR, ex.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
    return buildErrorResponse(ErrorType.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
    return buildErrorResponse(ErrorType.INVALID_PARAMETER, ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
    return buildErrorResponse(ErrorType.DATA_CONFLICT, "Data integrity violation");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
    log.error("Unexpected error: ", ex);
    return buildErrorResponse(ErrorType.INTERNAL_ERROR, "Unexpected error occurred");
  }

  @ExceptionHandler(JwtAuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleJwtAuthentication(JwtAuthenticationException ex) {
    return buildErrorResponse(ErrorType.UNAUTHORIZED, "Invalid JWT token");
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
    return buildErrorResponse(ErrorType.UNAUTHORIZED, "Authentication failed");
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
    log.warn("Access denied: {}", ex.getMessage());
    return buildErrorResponse(ErrorType.ACCESS_DENIED, "Access denied");
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorType errorType, String message) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(errorType.getStatus().value())
            .error(errorType.getError())
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.status(errorType.getStatus()).body(errorResponse);
  }
}
