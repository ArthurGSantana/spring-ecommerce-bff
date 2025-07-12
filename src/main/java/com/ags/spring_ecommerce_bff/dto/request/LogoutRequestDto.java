package com.ags.spring_ecommerce_bff.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutRequestDto {
  @NotBlank
  private String refreshToken;
}
