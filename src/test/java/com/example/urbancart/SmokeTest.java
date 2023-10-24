package com.example.urbancart;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.urbancart.auth.AuthController;
import com.example.urbancart.category.CategoryController;
import com.example.urbancart.hello.HelloController;
import com.example.urbancart.image.ImageController;
import com.example.urbancart.product.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {
  @Autowired private HelloController helloController;
  @Autowired private ProductController productController;
  @Autowired private CategoryController categoryController;
  @Autowired private ImageController imageController;
  @Autowired private AuthController authController;

  @Test
  void contextLoads() {
    assertThat(helloController).isNotNull();
    assertThat(productController).isNotNull();
    assertThat(categoryController).isNotNull();
    assertThat(imageController).isNotNull();
    assertThat(authController).isNotNull();
  }

  @Test
  public void applicationContextTest() {
    MainApplication.main(new String[] {});
  }
}
