package com.ags.spring_ecommerce_bff.config.feign;

import com.ags.spring_ecommerce_bff.exception.NotFoundException;
import feign.Client;
import feign.codec.ErrorDecoder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
    return (methodKey, response) ->
        switch (response.status()) {
          case 400, 409 -> new IllegalArgumentException("Invalid request parameters");
          case 404 -> new NotFoundException("Resource not found");
          case 500 -> new RuntimeException("Internal server error");
          default -> new RuntimeException("Unexpected error occurred: " + response.status());
        };
  }
}
