package com.example.urbancart.category;

import com.example.urbancart.category.dto.CategoryInputDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

  public final CategoryService categoryService;

  @GetMapping
  public List<Category> findAll() {
    return this.categoryService.findAll();
  }

  @GetMapping("/{id}")
  public Category findById(@PathVariable Long id) {
    return this.categoryService.findById(id);
  }

  @PutMapping("/{id}")
  public Category update(@PathVariable Long id, @RequestBody @Valid CategoryInputDto category) {
    return this.categoryService.update(id, category);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Category save(@RequestBody @Valid CategoryInputDto category) {
    return this.categoryService.save(category);
  }
}
