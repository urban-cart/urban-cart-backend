package com.example.urbancart.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomPasswordConstraint
    implements ConstraintValidator<ConfirmPasswordValidator, Object> {

  @Override
  public void initialize(ConfirmPasswordValidator constraintAnnotation) {}

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    if (obj instanceof IConfirmPassword) {
      var registerDto = (IConfirmPassword) obj;
      if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate("Password and Confirm Password do not match")
            .addPropertyNode("confirmPassword")
            .addConstraintViolation();
        return false;
      }
    }
    return false;
  }
}
