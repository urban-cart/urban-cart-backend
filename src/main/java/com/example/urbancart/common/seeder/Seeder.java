package com.example.urbancart.common.seeder;

import com.example.urbancart.category.Category;
import com.example.urbancart.category.CategoryRepository;
import com.example.urbancart.product.Product;
import com.example.urbancart.product.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class Seeder {
  private static final String categorySeederFile = "data/categories.json";

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  @Autowired
  public Seeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  @PostConstruct
  public void seed() throws IOException {
    var categories = seedCategories();
    seedProducts(categories);
  }

  private List<Category> seedCategories() throws IOException {
    if (categoryRepository.count() == 0) {
      Resource resource = new ClassPathResource(categorySeederFile);
      String jsonString =
          new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
      ObjectMapper objectMapper = new ObjectMapper();
      var categories = objectMapper.readValue(jsonString, new TypeReference<List<Category>>() {});
      categoryRepository.saveAll(categories);
    }
    return categoryRepository.findAll();
  }

  private void seedProducts(List<Category> categories) {
    if (productRepository.count() == 0) {
      for (int i = 0; i < 100; i++) {
        var product = new Product();
        product.setName(String.format("Product %d", i + 1));
        product.setPrice(BigInteger.valueOf(1000));
        product.setQuantity(100);
        product.setDescription(String.format("This is product %d", i + 1));
        product.setCategory(categories.get(new Random().nextInt(categories.size())));
        productRepository.save(product);
      }
    }
  }
}
