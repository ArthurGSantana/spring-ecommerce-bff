package com.ags.spring_ecommerce_bff.client;

import com.ags.spring_ecommerce_bff.config.feign.FeignClientConfig;
import com.ags.spring_ecommerce_bff.dto.request.AddressRequestDto;
import com.ags.spring_ecommerce_bff.dto.request.UserRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.AddressResponseDto;
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
  UserRequestDto createUser(@RequestBody UserRequestDto userDto);

  @PutMapping("/user/{id}")
  UserRequestDto updateUser(@RequestBody UserRequestDto userDto, @PathVariable UUID id);

  @DeleteMapping("/user/{id}")
  void deleteUserById(@PathVariable UUID id);

  @PostMapping("/address")
  AddressResponseDto createAddress(@RequestBody AddressRequestDto addressDto);

  @PutMapping("/address/{id}")
  AddressResponseDto updateAddress(
      @RequestBody AddressRequestDto addressDto, @PathVariable UUID id);

  @DeleteMapping("/address/{id}")
  void deleteAddressById(@PathVariable UUID id);
}
