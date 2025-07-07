package com.ags.spring_ecommerce_bff.client;

import com.ags.spring_ecommerce_bff.config.feign.FeignClientConfig;
import com.ags.spring_ecommerce_bff.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "ecommerce-service",
    url = "${clients.ecomm-service.url}",
    configuration = FeignClientConfig.class)
public interface EcommerceServiceClient {
  @PostMapping("/user")
  UserDto createUser(@RequestBody UserDto userDto);
}
