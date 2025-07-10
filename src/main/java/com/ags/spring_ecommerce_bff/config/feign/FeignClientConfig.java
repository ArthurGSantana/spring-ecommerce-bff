package com.ags.spring_ecommerce_bff.config.feign;

import com.ags.spring_ecommerce_bff.exception.errors.NotFoundException;
import com.ags.spring_ecommerce_bff.exception.errors.ValidationException;
import com.ags.spring_ecommerce_bff.interceptor.JwtFeignInterceptor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.RequestInterceptor;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignClientConfig {

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build();
  }

  @Bean
  public Client feignClient(okhttp3.OkHttpClient okHttpClient) {
    return new feign.okhttp.OkHttpClient(okHttpClient);
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return new ErrorDecoder() {
      @Override
      public Exception decode(String methodKey, Response response) {
        String errorMessage = extractErrorMessage(response);

        return switch (response.status()) {
          case 400 -> new ValidationException(errorMessage);
          case 404 -> new NotFoundException(errorMessage);
          case 409 -> new IllegalArgumentException(errorMessage);
          case 500 -> new RuntimeException("Internal server error: " + errorMessage);
          default -> new RuntimeException("Unexpected error occurred: " + errorMessage);
        };
      }

      private String extractErrorMessage(Response response) {
        try {
          if (response.body() != null) {
            String responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));

            // Parse do JSON para extrair a mensagem
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Extrair campos específicos do ErrorResponse
            String message = jsonNode.path("message").asText();
            String error = jsonNode.path("error").asText();

            return !message.isEmpty() ? message : error;
          }
        } catch (Exception e) {
          log.error("Error parsing error response: ", e);
        }

        return "Error occurred with status: " + response.status();
      }
    };
  }

  @Bean
  public RequestInterceptor jwtFeignInterceptor() {
    return new JwtFeignInterceptor();
  }
}
