package com.ags.spring_ecommerce_bff.dto.request;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class ProductRequestDto {
  private UUID id;
  private String sku;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stock;
}
