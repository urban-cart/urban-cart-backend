package com.example.urbancart.product;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.urbancart.auth.JwtService;
import com.example.urbancart.common.CustomPage;
import com.example.urbancart.product.dto.ProductInputDto;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private ProductService productService;
  @InjectMocks private ProductController productController;
  @MockBean private JwtService jwtService;

  @Test
  public void findProductById_ReturnsProduct() throws Exception {
    UUID productId = UUID.randomUUID();
    var product = new Product();
    when(productService.findById(eq(productId))).thenReturn(product);
    mockMvc
        .perform(get("/products/{id}", productId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void create_ReturnsCreatedProduct() throws Exception {
    var product = new Product();
    when(productService.save(Mockito.any(ProductInputDto.class))).thenReturn(product);
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\": \"sample\", \"description\": \"sample description\", \"price\": 100, \"categoryId\": 1}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void update_ReturnsUpdatedProduct() throws Exception {
    UUID productId = UUID.randomUUID();
    var product = new Product();
    when(productService.update(Mockito.any(UUID.class), Mockito.any(ProductInputDto.class)))
        .thenReturn(product);
    mockMvc
        .perform(
            put("/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\": \"sample\", \"description\": \"sample description\", \"price\": 100, \"categoryId\": 1}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void delete_ReturnsNoContent() throws Exception {
    UUID productId = UUID.randomUUID();
    mockMvc.perform(delete("/products/{id}", productId)).andExpect(status().isNoContent());
    verify(productService, times(1)).remove(productId, false);
  }

  @Test
  public void findAll_NoData() throws Exception {
    var dataList = new PageImpl<Product>(Arrays.asList());
    when(productService.findAll(
            anyInt(), anyInt(), anyString(), anyString(), anyBoolean(), anyString(), anyInt()))
        .thenReturn(new CustomPage<Product>(dataList));
    mockMvc.perform(get("/products")).andExpect(status().isOk());
  }
}
