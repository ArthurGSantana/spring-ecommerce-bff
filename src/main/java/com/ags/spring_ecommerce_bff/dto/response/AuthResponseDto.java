package com.ags.spring_ecommerce_bff.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
  private String accessToken;
  private String refreshToken;
  private String name;
}
