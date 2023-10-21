package com.example.urbancart.common.seeder;

import com.example.urbancart.category.Category;
import com.example.urbancart.category.CategoryRepository;
import com.example.urbancart.product.Product;
import com.example.urbancart.product.ProductRepository;
import jakarta.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Seeder {
  private static final String[] CATEGORY_NAMES = {
    "Electronics",
    "Clothing",
    "Furniture",
    "Grocery",
    "Books",
    "Toys",
    "Sports",
    "Beauty",
    "Health",
    "Automotive",
    "Jewelry",
    "Movies",
    "Music",
    "Garden",
    "Tools",
    "Pet",
    "Baby",
    "Industrial",
    "Software",
    "Shoes",
  };

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  @Autowired
  public Seeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  @PostConstruct
  public void seed() {
    var categories = seedCategories();
    seedProducts(categories);
  }

  private List<Category> seedCategories() {
    if (categoryRepository.count() == 0) {

      for (String name : CATEGORY_NAMES) {
        var category = new Category();
        category.setName(name);
        category.setDescription(String.format("This is %s category", name));
        categoryRepository.save(category);
      }
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
