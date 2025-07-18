package com.ags.spring_ecommerce_bff.dto.response;

import com.ags.spring_ecommerce_bff.enums.ProductStatusEnum;
import java.math.BigDecimal;
import lombok.Data;

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
