package com.ags.spring_ecommerce_bff.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestFilterDto {
  private String sku;
  private String name;
  private String sortBy; // e.g., "price", "name"
  private String sortOrder; // e.g., "asc", "desc"
  private Integer page = 0; // Default to first page
  private Integer size = 10; // Default to 10 items per page
}
