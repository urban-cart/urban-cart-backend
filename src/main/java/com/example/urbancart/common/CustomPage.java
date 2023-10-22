package com.example.urbancart.common;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class CustomPage<T> {
  private List<T> data = null;
  private int page = 0;
  private int size = 0;
  private long count = 0;
  private long totalPages = 0;

  public CustomPage(Page<T> page) {
    this.data = page.getContent();
    this.count = page.getTotalElements();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalPages = page.getTotalPages();
  }
}
