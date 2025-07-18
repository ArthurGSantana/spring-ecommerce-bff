package com.ags.spring_ecommerce_bff.entity;

import com.ags.spring_ecommerce_bff.enums.ProductStatusEnum;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "sku", nullable = false, unique = true)
  private String sku;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "stock", nullable = false)
  private Integer stock;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductStatusEnum status;
}
