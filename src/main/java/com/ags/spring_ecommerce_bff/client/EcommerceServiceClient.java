package com.ags.spring_ecommerce_bff.client;

import com.ags.spring_ecommerce_bff.config.feign.FeignClientConfig;
import com.ags.spring_ecommerce_bff.dto.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "ecommerce-service",
    url = "${clients.ecomm-service.url}",
    configuration = FeignClientConfig.class)
// @CircuitBreaker(name = "ecomm-service", fallbackMethod = "fallbackMethod") // Descomente para
// usar o Circuit Breaker com fallback
@CircuitBreaker(name = "ecomm-service")
@Retry(name = "ecomm-service")
// @TimeLimiter(name = "ecomm-service") // Deve ser usado apenas em métodos que retornam
// CompletableFuture ou Mono
public interface EcommerceServiceClient {
  @PostMapping("/user")
  UserDto createUser(@RequestBody UserDto userDto);

  @PutMapping("/user/{id}")
  UserDto updateUser(@RequestBody UserDto userDto, @PathVariable UUID id);

  @DeleteMapping("/user/{id}")
  void deleteUserById(@PathVariable UUID id);
}
