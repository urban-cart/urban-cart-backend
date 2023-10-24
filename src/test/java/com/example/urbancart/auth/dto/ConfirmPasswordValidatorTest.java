package com.example.urbancart.auth.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class ConfirmPasswordValidatorTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidPasswords() {
    ChangePasswordDto validDto = new ChangePasswordDto();
    validDto.setPassword("password123");
    validDto.setConfirmPassword("password123");

    assertTrue(validator.validate(validDto).isEmpty());
  }

  @Test
  void testInvalidPasswords() {
    ChangePasswordDto invalidDto = new ChangePasswordDto();
    invalidDto.setPassword("password123");
    invalidDto.setConfirmPassword("differentPassword");

    assertFalse(validator.validate(invalidDto).isEmpty());
  }
}
