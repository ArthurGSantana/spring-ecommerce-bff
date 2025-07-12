package com.ags.spring_ecommerce_bff.dto.internal;

import com.ags.spring_ecommerce_bff.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserSessionDto {
  private UUID userId;
  private String email;
  private String name;
  private UserRoleEnum role;
  private Date lastActivity;
  private String refreshTokenId;
}