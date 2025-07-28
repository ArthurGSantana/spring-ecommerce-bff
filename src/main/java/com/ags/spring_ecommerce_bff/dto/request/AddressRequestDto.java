package com.ags.spring_ecommerce_bff.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressRequestDto {
  @NotBlank
  private String street;

  @NotBlank
  private String number;

  @NotBlank
  private String city;

  @NotBlank
  private String state;

  @NotBlank
  @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$", message = "Invalid zip code format")
  private String zipCode;

  private UUID userId;
}
