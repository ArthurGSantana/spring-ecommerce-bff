package com.ags.spring_ecommerce_bff.filter;

import com.ags.spring_ecommerce_bff.config.security.JwtConfig;
import com.ags.spring_ecommerce_bff.service.SessionService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtConfig jwtConfig;
  private final SessionService sessionService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = authHeader.substring(7);
      SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));

      var claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

      String userEmail = claims.getSubject();
      String tokenType = claims.get("tokenType", String.class);
      var userId = UUID.fromString(claims.get("userId", String.class));

      // Verificar se é um token de acesso (não de refresh)
      if (!"access".equals(tokenType)) {
        throw new JwtException("Token inválido");
      }

      // Verificar se o usuário está ativo
     var session = sessionService.getSession(userId);
      if(session.isEmpty()) throw new JwtException("Sessão inválida ou expirada");

      // Atualizar última atividade
      sessionService.updateLastActivity(userId);

      List<String> roles = (List<String>) claims.getOrDefault("roles", Collections.emptyList());

      var authorities =
          roles.stream()
              .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
              .collect(Collectors.toList());

      // Criar o objeto Authentication
      var authentication = new UsernamePasswordAuthenticationToken(userEmail, token, authorities);

      // Definir a autenticação no contexto de segurança
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (Exception e) {
      // Limpar o contexto de segurança em caso de falha
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}
