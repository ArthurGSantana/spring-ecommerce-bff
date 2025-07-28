package com.ags.spring_ecommerce_bff.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddressResponseDto {
  private UUID id;
  private String street;
  private String city;
  private String state;
  private String zipCode;
}
