package com.ags.spring_ecommerce_bff.config.security;

import com.ags.spring_ecommerce_bff.exception.auth.CustomAccessDeniedHandler;
import com.ags.spring_ecommerce_bff.exception.auth.CustomAuthenticationEntryPoint;
import com.ags.spring_ecommerce_bff.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;

  @Bean()
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/graphiql")
                    .permitAll() // Swagger
                    .requestMatchers("/api/auth/**")
                    .permitAll() // Permite acesso público às rotas de autenticação
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            exc ->
                exc.accessDeniedHandler(
                        accessDeniedHandler) // lança exceção personalizada quando o acesso é negado
                    .authenticationEntryPoint(
                        authenticationEntryPoint) // lança exceção personalizada quando a
            // autenticação falha
            );

    return http.build();
  }
}
