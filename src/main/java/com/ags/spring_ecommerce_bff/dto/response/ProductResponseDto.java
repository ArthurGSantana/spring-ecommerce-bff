package com.ags.spring_ecommerce_bff.dto.response;

import com.ags.spring_ecommerce_bff.enums.ProductStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
  private String id;
  private String sku;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stock;
  private ProductStatusEnum status;
}
