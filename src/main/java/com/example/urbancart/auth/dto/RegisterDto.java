package com.example.urbancart.auth.dto;

import com.example.urbancart.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterDto {
  @Email private String email;

  @NotNull
  @NotBlank
  @Length(min = 8, max = 20)
  private String password;

  @NotNull
  @NotBlank
  @Length(min = 8, max = 20)
  private String confirmPassword;

  private Role role = Role.USER;
}
