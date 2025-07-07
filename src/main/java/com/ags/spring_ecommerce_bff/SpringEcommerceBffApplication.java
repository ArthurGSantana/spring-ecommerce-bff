package com.ags.spring_ecommerce_bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringEcommerceBffApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringEcommerceBffApplication.class, args);
  }
}
