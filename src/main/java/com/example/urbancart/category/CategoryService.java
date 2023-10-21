package com.example.urbancart.category;

import com.example.urbancart.category.dto.CategoryInputDto;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryService {

  public final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  public Category save(CategoryInputDto category) {
    var categoryModel = modelMapper.map(category, Category.class);
    return this.categoryRepository.save(categoryModel);
  }

  public List<Category> findAll() {
    return this.categoryRepository.findAll();
  }

  @Cacheable(value = "category", key = "#id", unless = "#result == null")
  public Category findById(UUID id) {
    return this.categoryRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
  }

  @CachePut(key = "#id", unless = "#result == null", value = "category")
  public Category update(UUID id, CategoryInputDto category) {
    var categoryModel = modelMapper.map(category, Category.class);
    categoryModel.setId(id);
    return this.categoryRepository.save(categoryModel);
  }
}
