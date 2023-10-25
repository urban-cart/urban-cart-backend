package com.example.urbancart.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.urbancart.category.Category;
import com.example.urbancart.category.CategoryService;
import com.example.urbancart.product.dto.ProductInputDto;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock private ProductRepository productRepository;
  @Mock private CategoryService categoryService;
  @InjectMocks private ProductService productService;
  @Mock private ModelMapper modelMapper;

  @Test
  void testFindAll() {
    List<Product> productList = new ArrayList<>();
    when(productRepository.findAllByIsDeletedAndNameContainingIgnoreCase(
            any(Pageable.class), anyBoolean(), anyString()))
        .thenReturn(new PageImpl<>(productList));

    var result = productService.findAll(0, 10, "name", "asc", false, "", null);

    assertNotNull(result);
    verify(productRepository, times(1))
        .findAllByIsDeletedAndNameContainingIgnoreCase(
            any(Pageable.class), anyBoolean(), anyString());
  }

  @Test
  void testFindById() {
    UUID productId = UUID.randomUUID();
    var product = new Product();
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    var result = productService.findById(productId);
    assertNotNull(result);
    verify(productRepository, times(1)).findById(productId);
  }

  @Test
  void testFindByIdNotFound() {
    UUID productId = UUID.randomUUID();
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> productService.findById(productId));
    verify(productRepository, times(1)).findById(productId);
  }

  @Test
  void testSave() {
    Long categoryId = Long.MIN_VALUE;
    var category = new Category();
    category.setId(categoryId);

    when(categoryService.findById(eq(categoryId))).thenReturn(category);

    var productInputDto = new ProductInputDto();
    productInputDto.setCategoryId(categoryId);

    var product = new Product();
    product.setCategory(category);

    when(modelMapper.map(eq(productInputDto), eq(Product.class))).thenReturn(product);
    when(productRepository.save(any(Product.class))).thenReturn(product);

    var result = productService.save(productInputDto);
    assertNotNull(result);

    verify(productRepository, times(1)).save(product);
  }

  @Test
  void testUpdate() {
    UUID productId = UUID.randomUUID();
    var product = new Product();
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productRepository.save(any(Product.class))).thenReturn(product);
    var result = productService.update(productId, new ProductInputDto());
    assertNotNull(result);
    verify(productRepository, times(1)).save(product);
  }

  @Test
  void testRemove() {
    productService.remove(UUID.randomUUID(), true);
    verify(productRepository, times(1)).deleteById(any(UUID.class));
  }

  @Test
  void testRemoveSoftDelete() {
    productService.remove(UUID.randomUUID(), false);
    verify(productRepository, times(1)).softDeleteById(any(UUID.class));
  }

  @Test
  void testUpdate_notFound() {
    UUID productId = UUID.randomUUID();
    when(productRepository.findById(productId)).thenReturn(Optional.empty());
    assertThrows(
        ResponseStatusException.class,
        () -> productService.update(productId, new ProductInputDto()));
  }

  @Test
  void testFindAllSortingDirection() {
    var productList = new ArrayList<Product>();
    when(productRepository.findAllByIsDeletedAndNameContainingIgnoreCase(
            any(Pageable.class), anyBoolean(), anyString()))
        .thenReturn(new PageImpl<>(productList));

    var resultAsc = productService.findAll(0, 10, "name", "asc", false, "", null);
    var resultDesc = productService.findAll(0, 10, "name", "desc", false, "", null);

    assertNotNull(resultDesc);
    assertNotNull(resultAsc);
    verify(productRepository, times(1))
        .findAllByIsDeletedAndNameContainingIgnoreCase(
            argThat(
                (Pageable pageable) -> {
                  Sort.Order order = pageable.getSort().getOrderFor("name");
                  return order != null && order.getDirection().equals(Sort.Direction.DESC);
                }),
            anyBoolean(),
            anyString());
    verify(productRepository, times(1))
        .findAllByIsDeletedAndNameContainingIgnoreCase(
            argThat(
                (Pageable pageable) -> {
                  Sort.Order order = pageable.getSort().getOrderFor("name");
                  return order != null && order.getDirection().equals(Sort.Direction.ASC);
                }),
            anyBoolean(),
            anyString());
  }

  @Test
  void testFindAllByCategoryId() {
    var productList = new ArrayList<Product>();
    var category = new Category();
    category.setId(1L);
    category.setName("Category 1");
    for (int i = 0; i < 10; i++) {
      var product = new Product();
      product.setId(UUID.randomUUID());
      product.setName("Product " + i);
      product.setDescription("Description " + i);
      product.setPrice(BigInteger.valueOf(10));
      product.setCategory(category);
      productList.add(product);
    }
    when(productRepository.findAllByIsDeletedAndNameContainingIgnoreCaseAndCategoryId(
            any(Pageable.class), anyBoolean(), anyString(), any(Integer.class)))
        .thenReturn(new PageImpl<>(productList));

    var result = productService.findAll(0, 10, "name", "asc", false, "", 1);

    assertNotNull(result);
    verify(productRepository, times(1))
        .findAllByIsDeletedAndNameContainingIgnoreCaseAndCategoryId(
            any(Pageable.class), anyBoolean(), anyString(), any(Integer.class));
  }
}
