package com.ags.spring_ecommerce_bff.dto.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDto {
  private UUID id;
  private UUID shippingAddressId;
  private List<OrderItemResponseDto> items;
}
