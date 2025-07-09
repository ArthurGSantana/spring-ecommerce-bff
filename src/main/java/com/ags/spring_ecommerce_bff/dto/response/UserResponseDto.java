package com.ags.spring_ecommerce_bff.dto.response;

import com.ags.spring_ecommerce_bff.enums.UserStatusEnum;
import java.util.UUID;
import lombok.Data;

@Data
public class UserResponseDto {
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private UserStatusEnum status;
}
