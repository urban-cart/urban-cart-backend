package com.example.urbancart.user.dto;

import com.example.urbancart.user.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class UserResponseDto {
  private UUID id;
  private Boolean isVerified;
  private Role role;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updatedAt;
}
