package com.example.urbancart.service;

import com.example.urbancart.common.CustomPage;
import com.example.urbancart.dto.product.ProductInputDto;
import com.example.urbancart.dto.product.ProductOutputDto;
import com.example.urbancart.model.Product;
import com.example.urbancart.repository.ProductRepository;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  public CustomPage<ProductOutputDto> findAll(
      int page, int size, String sortBy, String sortDirection, Boolean isDeleted, String search) {

    var direction =
        sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
    var data =
        productRepository.findAllByIsDeletedAndNameContainingIgnoreCase(
            pageable, isDeleted, search);
    var output = data.map(product -> modelMapper.map(product, ProductOutputDto.class));
    return new CustomPage<ProductOutputDto>(output);
  }

  @Cacheable(key = "#id", unless = "#result == null", value = "product")
  public ProductOutputDto findById(UUID id) {
    var data =
        this.productRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    return modelMapper.map(data, ProductOutputDto.class);
  }

  public ProductOutputDto save(ProductInputDto product) {
    var productToSave = modelMapper.map(product, Product.class);
    var categories = categoryService.findAllByIds(product.getCategories());
    productToSave.setCategories(categories);
    var data = this.productRepository.save(productToSave);
    return modelMapper.map(data, ProductOutputDto.class);
  }

  @CachePut(key = "#id", unless = "#result == null", value = "product")
  public ProductOutputDto update(UUID id, ProductInputDto product) {
    var categories = categoryService.findAllByIds(product.getCategories());
    var productToUpdate =
        this.productRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    productToUpdate.setName(product.getName());
    productToUpdate.setPrice(product.getPrice());
    productToUpdate.setQuantity(product.getQuantity());
    productToUpdate.setDescription(product.getDescription());
    productToUpdate.setCategories(categories);
    var data = this.productRepository.save(productToUpdate);
    return modelMapper.map(data, ProductOutputDto.class);
  }

  @CacheEvict(key = "#id", value = "product")
  public void remove(UUID id, Boolean isHardDelete) {
    if (isHardDelete) this.productRepository.deleteById(id);
    else this.productRepository.softDeleteById(id);
  }

  public long count() {
    return this.productRepository.count();
  }
}
