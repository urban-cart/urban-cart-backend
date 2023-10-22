package com.example.urbancart.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordDto {
  private String oldPassword;

  @NotNull
  @NotBlank
  @Length(min = 8, max = 20)
  private String newPassword;

  @NotNull
  @NotBlank
  @Length(min = 8, max = 20)
  private String confirmPassword;
}
