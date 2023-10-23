package com.example.urbancart.common.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CustomPasswordConstraint.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ConfirmPasswordValidator {
  String message() default "Invalid Password";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
