package com.example.urbancart.product;

import com.example.urbancart.common.CustomPage;
import com.example.urbancart.product.dto.ProductInputDto;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  public final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product save(@RequestBody @Valid ProductInputDto product) {
    return this.productService.save(product);
  }

  @GetMapping
  public CustomPage<Product> findAll(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "price") String sortBy,
      @RequestParam(defaultValue = "desc") String sortDirection,
      @RequestParam(defaultValue = "false") Boolean isDeleted,
      @RequestParam(defaultValue = "") String search,
      @RequestParam(required = false) Integer categoryId) {
    return this.productService.findAll(
        page, size, sortBy, sortDirection, isDeleted, search, categoryId);
  }

  @GetMapping("/{id}")
  public Product findById(@PathVariable UUID id) {
    return this.productService.findById(id);
  }

  @PutMapping("/{id}")
  public Product update(@PathVariable UUID id, @RequestBody @Valid ProductInputDto product) {
    return this.productService.update(id, product);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable UUID id, @RequestParam(defaultValue = "false") Boolean isHardDelete) {
    this.productService.remove(id, isHardDelete);
  }
}
