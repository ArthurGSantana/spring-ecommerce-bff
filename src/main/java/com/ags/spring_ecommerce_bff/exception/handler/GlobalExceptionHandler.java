package com.ags.spring_ecommerce_bff.exception.handler;

import com.ags.spring_ecommerce_bff.exception.errors.InvalidCredentialsException;
import com.ags.spring_ecommerce_bff.exception.errors.JwtAuthenticationException;
import com.ags.spring_ecommerce_bff.exception.errors.NotFoundException;
import com.ags.spring_ecommerce_bff.exception.errors.ValidationException;
import com.ags.spring_ecommerce_bff.exception.models.ErrorResponse;
import com.ags.spring_ecommerce_bff.exception.models.ErrorType;
import jakarta.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
    log.error("Unexpected error: ", ex);
    return buildErrorResponse(ErrorType.INTERNAL_ERROR, "Unexpected error occurred");
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
    return buildErrorResponse(ErrorType.NOT_FOUND, ex.getMessage());
  }

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

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    return buildErrorResponse(ErrorType.INVALID_PARAMETER, ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
    return buildErrorResponse(ErrorType.DATA_CONFLICT, "Data integrity violation");
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

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
    log.warn("Invalid credentials: {}", ex.getMessage());
    return buildErrorResponse(ErrorType.UNAUTHORIZED, "Invalid credentials");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return buildErrorResponse(ErrorType.VALIDATION_ERROR, "Validation failed", errors);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorType errorType, String message) {
    return buildErrorResponse(errorType, message, null);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
      ErrorType errorType, String message, Map<String, String> details) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(errorType.getStatus().value())
            .error(errorType.getError())
            .message(message)
            .details(details)
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.status(errorType.getStatus()).body(errorResponse);
  }
}
