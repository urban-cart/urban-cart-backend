package com.example.urbancart;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.urbancart.category.CategoryController;
import com.example.urbancart.hello.HelloController;
import com.example.urbancart.product.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {
  @Autowired private HelloController helloController;
  @Autowired private ProductController productController;
  @Autowired private CategoryController categoryController;

  @Test
  void contextLoads() {
    assertThat(helloController).isNotNull();
    assertThat(productController).isNotNull();
    assertThat(categoryController).isNotNull();
  }

  @Test
  public void applicationContextTest() {
    MainApplication.main(new String[] {});
  }
}
