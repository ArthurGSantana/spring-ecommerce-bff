package com.ags.spring_ecommerce_bff.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
  private UUID id;
  private UUID shippingAddressId;
  private List<OrderItemResponseDto> items;
}
