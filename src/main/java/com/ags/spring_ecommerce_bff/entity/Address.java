package com.ags.spring_ecommerce_bff.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Address {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "street", nullable = false)
  private String street;

  @Column(name = "number", nullable = false)
  private String number;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "state", nullable = false)
  private String state;

  @Column(name = "zip_code", nullable = false)
  private String zipCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
