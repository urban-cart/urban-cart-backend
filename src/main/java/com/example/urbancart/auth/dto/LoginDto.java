package com.example.urbancart.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto {
  @Email private String email;

  private String password;
}
