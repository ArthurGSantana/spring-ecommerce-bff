package com.ags.spring_ecommerce_bff.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductStatusEnum {
  AVAILABLE("AVAILABLE"),
  UNAVAILABLE("UNAVAILABLE"),
  DISCONTINUED("DISCONTINUED");

  private final String statusName;

  public static ProductStatusEnum from(String statusName) {
    for (ProductStatusEnum status : ProductStatusEnum.values()) {
      if (status.getStatusName().equalsIgnoreCase(statusName)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown status: " + statusName);
  }
}
