package com.ags.spring_ecommerce_bff.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
  PENDING("PENDING"),
  PROCESSING("PROCESSING"),
  SHIPPED("SHIPPED"),
  DELIVERED("DELIVERED"),
  CANCELLED("CANCELLED");

  private final String statusName;

  public static OrderStatusEnum from(String statusName) {
    for (OrderStatusEnum status : OrderStatusEnum.values()) {
      if (status.getStatusName().equalsIgnoreCase(statusName)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown status: " + statusName);
  }
}
