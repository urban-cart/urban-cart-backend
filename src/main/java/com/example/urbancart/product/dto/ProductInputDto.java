package com.example.urbancart.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInputDto {
  @NotBlank @NotNull private String name;

  private String description;

  @Min(0)
  private BigInteger price;

  @Min(0)
  private Integer quantity;

  private Long categoryId;
}
