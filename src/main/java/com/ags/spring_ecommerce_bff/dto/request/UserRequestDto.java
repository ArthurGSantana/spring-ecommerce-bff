package com.ags.spring_ecommerce_bff.dto.request;

import com.ags.spring_ecommerce_bff.enums.UserRoleEnum;
import com.ags.spring_ecommerce_bff.enums.UserStatusEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
  private UUID id;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is required")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
      message =
          "Password must be at least 8 characters long and contain at least one letter and one number")
  private String password;

  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid")
  private String phone;

  @NotNull(message = "Status is required")
  private UserStatusEnum status;

  @NotNull(message = "Role is required")
  private UserRoleEnum role;
}
