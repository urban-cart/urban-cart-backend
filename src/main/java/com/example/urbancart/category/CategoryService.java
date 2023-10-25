package com.example.urbancart.category;

import com.example.urbancart.category.dto.CategoryInputDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryService {

  public final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  public Category save(CategoryInputDto category) {
    if (categoryRepository.existsByName(category.getName())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Category with this name already exists");
    }
    var categoryModel = modelMapper.map(category, Category.class);
    return this.categoryRepository.save(categoryModel);
  }

  public List<Category> findAll() {
    return this.categoryRepository.findAll();
  }

  @Cacheable(value = "category", key = "#id", unless = "#result == null")
  public Category findById(Long id) {
    return this.categoryRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
  }

  @CachePut(key = "#id", unless = "#result == null", value = "category")
  public Category update(Long id, CategoryInputDto category) {
    var categoryModel = findById(id);
    categoryModel.setName(category.getName());
    categoryModel.setDescription(category.getDescription());
    return this.categoryRepository.save(categoryModel);
  }
}
