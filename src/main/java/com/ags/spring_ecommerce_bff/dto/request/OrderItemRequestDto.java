package com.ags.spring_ecommerce_bff.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderItemRequestDto {
  @NotNull private UUID productId;

  @NotNull
  @Min(0)
  @Max(150)
  private Integer quantity;
}
