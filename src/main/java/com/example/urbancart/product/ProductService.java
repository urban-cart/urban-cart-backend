package com.example.urbancart.product;

import com.example.urbancart.category.CategoryService;
import com.example.urbancart.common.CustomPage;
import com.example.urbancart.product.dto.ProductInputDto;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final ModelMapper modelMapper;

  @Autowired
  public ProductService(
      ProductRepository productRepository,
      ModelMapper modelMapper,
      CategoryService categoryService) {
    this.productRepository = productRepository;
    this.modelMapper = modelMapper;
    this.categoryService = categoryService;
  }

  public CustomPage<Product> findAll(
      int page, int size, String sortBy, String sortDirection, Boolean isDeleted, String search) {

    var direction =
        sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    var pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
    var data =
        this.productRepository.findAllByIsDeletedAndNameContainingIgnoreCase(
            pageable, isDeleted, search);
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
    var productToUpdate =
        this.productRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    var category = categoryService.findById(product.getCategoryId());
    productToUpdate.setName(product.getName());
    productToUpdate.setPrice(product.getPrice());
    productToUpdate.setQuantity(product.getQuantity());
    productToUpdate.setDescription(product.getDescription());
    productToUpdate.setCategory(category);
    return this.productRepository.save(productToUpdate);
  }

  @CacheEvict(key = "#id", value = "product")
  public void remove(UUID id, Boolean isHardDelete) {
    if (isHardDelete) this.productRepository.deleteById(id);
    else this.productRepository.softDeleteById(id);
  }
}
