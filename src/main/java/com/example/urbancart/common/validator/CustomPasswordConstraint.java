package com.example.urbancart.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomPasswordConstraint
    implements ConstraintValidator<ConfirmPasswordValidator, Object> {

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    if (obj instanceof IConfirmPassword registerDto) {
      if (registerDto.getPassword() == null
          || registerDto.getConfirmPassword() == null
          || !registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate("Password and Confirm Password do not match")
            .addPropertyNode("confirmPassword")
            .addConstraintViolation();
        return false;
      }
    }
    return true;
  }
}
