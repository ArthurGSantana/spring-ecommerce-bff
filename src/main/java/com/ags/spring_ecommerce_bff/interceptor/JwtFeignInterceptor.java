package com.ags.spring_ecommerce_bff.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
public class JwtFeignInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    // Obter o token do contexto de segurança
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      // Se tiver o token no SecurityContext
      if (authentication.getCredentials() instanceof String token) {
        template.header("Authorization", "Bearer " + token);
      }
      // Ou pegar do request atual
      else {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
          String authHeader = request.getHeader("Authorization");
          if (authHeader != null) {
            template.header("Authorization", authHeader);
          }
        }
      }
    }
  }

  private HttpServletRequest getCurrentRequest() {
    try {
      RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
      if (requestAttributes instanceof ServletRequestAttributes) {
        return ((ServletRequestAttributes) requestAttributes).getRequest();
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }
}
