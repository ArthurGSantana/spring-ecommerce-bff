package com.ags.spring_ecommerce_bff.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDto {
  private UUID id;
  private String sku;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Description is required")
  private String description;

  @NotNull
  @Min(value = 0, message = "Price must be greater than or equal to 0")
  private BigDecimal price;

  @NotNull
  @Min(value = 0, message = "Stock must be greater than or equal to 0")
  private Integer stock;
}
