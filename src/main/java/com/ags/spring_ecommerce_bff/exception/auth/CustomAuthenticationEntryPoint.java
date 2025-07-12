package com.ags.spring_ecommerce_bff.exception.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {

    var message =
        "Authentication failed: "
            + (authException.getMessage() != null
                ? authException.getMessage()
                : "You must be authenticated to access this resource");

    Map<String, Object> body = new HashMap<>();
    body.put("status", HttpStatus.UNAUTHORIZED.value());
    body.put("error", "Not authenticated");
    body.put("message", message);
    body.put("path", request.getServletPath());

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    new ObjectMapper().writeValue(response.getOutputStream(), body);
  }
}
