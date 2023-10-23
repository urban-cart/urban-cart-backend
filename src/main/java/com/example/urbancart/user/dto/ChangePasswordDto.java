package com.example.urbancart.user.dto;

import com.example.urbancart.common.validator.ConfirmPasswordValidator;
import com.example.urbancart.common.validator.IConfirmPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ConfirmPasswordValidator
public class ChangePasswordDto implements IConfirmPassword {
  private String oldPassword;

  @NotNull
  @NotBlank
  @Length(min = 8, max = 20)
  private String password;

  @NotNull
  @NotBlank
  @Length(min = 8, max = 20)
  private String confirmPassword;
}
