package com.example.urbancart.product;

import com.example.urbancart.category.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {

  @Id @GeneratedValue private UUID id;

  @NotBlank @NotNull private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Min(0)
  @JsonIgnore
  @Column(columnDefinition = "BIGINT")
  private BigInteger price; // in cents

  @Min(0)
  private Integer quantity;

  @JsonIgnore
  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
}
