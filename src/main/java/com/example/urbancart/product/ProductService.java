package com.example.urbancart.product;

import static org.springframework.data.domain.Sort.Direction;

import com.example.urbancart.category.CategoryService;
import com.example.urbancart.common.CustomPage;
import com.example.urbancart.product.dto.ProductInputDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final ModelMapper modelMapper;

  public CustomPage<Product> findAll(
      int page,
      int size,
      String sortBy,
      String sortDirection,
      Boolean isDeleted,
      String search,
      Integer categoryId) {

    var direction = sortDirection.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
    var pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
    var data =
        (categoryId == null)
            ? this.productRepository.findAllByIsDeletedAndNameContainingIgnoreCase(
                pageable, isDeleted, search)
            : this.productRepository.findAllByIsDeletedAndNameContainingIgnoreCaseAndCategoryId(
                pageable, isDeleted, search, categoryId);
    return new CustomPage<Product>(data);
  }

  @Cacheable(key = "#id", unless = "#result == null", value = "product")
  public Product findById(UUID id) {
    var data =
        this.productRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    return data;
  }

  public Product save(ProductInputDto product) {
    var productToSave = modelMapper.map(product, Product.class);
    var category = categoryService.findById(product.getCategoryId());
    productToSave.setCategory(category);
    return this.productRepository.save(productToSave);
  }

  @CachePut(key = "#id", unless = "#result == null", value = "product")
  public Product update(UUID id, ProductInputDto product) {
    var productToUpdate = findById(id);
    var category = categoryService.findById(product.getCategoryId());

    productToUpdate.setName(product.getName());
    productToUpdate.setPrice(product.getPrice());
    productToUpdate.setQuantity(product.getQuantity());
    productToUpdate.setDescription(product.getDescription());
    productToUpdate.setCategory(category);
    productToUpdate.setImageUrl(product.getImageUrl());

    return this.productRepository.save(productToUpdate);
  }

  @CacheEvict(key = "#id", value = "product")
  public void remove(UUID id, Boolean isHardDelete) {
    if (isHardDelete) this.productRepository.deleteById(id);
    else this.productRepository.softDeleteById(id);
  }
}
