package com.ags.spring_ecommerce_bff.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderRequestDto {
  @NotBlank private UUID userId;

  @NotBlank private UUID shippingAddressId;

  @NotEmpty @Valid private List<OrderItemRequestDto> items;
}
