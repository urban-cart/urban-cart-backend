package com.example.urbancart.dto.product;

import com.example.urbancart.dto.category.CategoryOutputDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOutputDto {

  private UUID id;
  private String name;
  private String description;
  private BigInteger price;
  private Integer quantity;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updatedAt;

  private Set<CategoryOutputDto> categories;
}
