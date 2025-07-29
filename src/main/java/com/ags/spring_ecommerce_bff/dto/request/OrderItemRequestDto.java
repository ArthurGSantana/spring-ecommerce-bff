package com.ags.spring_ecommerce_bff.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderItemRequestDto {
  @NotBlank private UUID productId;

  @NotEmpty private Integer quantity;
}
