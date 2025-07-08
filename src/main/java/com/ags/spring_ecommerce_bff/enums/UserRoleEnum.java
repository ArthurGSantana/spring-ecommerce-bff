package com.ags.spring_ecommerce_bff.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRoleEnum {
  CUSTOMER("CUSTOMER"),
  ADMIN("ADMIN");

  private final String roleName;
}
