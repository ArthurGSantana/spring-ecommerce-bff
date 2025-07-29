package com.ags.spring_ecommerce_bff.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDto {
  private UUID productId;
  private Integer quantity;
}
