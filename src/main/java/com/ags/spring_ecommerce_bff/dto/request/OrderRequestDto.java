package com.ags.spring_ecommerce_bff.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderRequestDto {
  @NotNull private UUID userId;

  @NotNull private UUID shippingAddressId;

  @NotEmpty @Valid private List<OrderItemRequestDto> items;
}
