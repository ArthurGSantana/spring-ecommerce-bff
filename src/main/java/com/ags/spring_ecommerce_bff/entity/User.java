package com.ags.spring_ecommerce_bff.entity;

import com.ags.spring_ecommerce_bff.enums.UserRoleEnum;
import com.ags.spring_ecommerce_bff.enums.UserStatusEnum;
import jakarta.persistence.*;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "phone", length = 20)
  private String phone;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserStatusEnum status;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRoleEnum role;
}
