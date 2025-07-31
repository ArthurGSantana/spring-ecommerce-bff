package com.ags.spring_ecommerce_bff.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
  private UUID id;
  private String street;
  private String city;
  private String state;
  private String number;
  private String zipCode;
}
