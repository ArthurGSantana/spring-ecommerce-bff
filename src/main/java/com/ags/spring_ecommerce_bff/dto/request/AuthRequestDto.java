package com.ags.spring_ecommerce_bff.dto.request;

import lombok.Data;

@Data
public class AuthRequestDto {
  private String email;
  private String password;
}
