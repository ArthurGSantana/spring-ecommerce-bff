package com.ags.spring_ecommerce_bff.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseFilterDto<T> {
  private List<T> content;
  private Long totalElements;
  private Integer totalPages;
}
