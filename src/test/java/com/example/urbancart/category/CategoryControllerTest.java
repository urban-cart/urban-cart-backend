package com.example.urbancart.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.urbancart.category.dto.CategoryInputDto;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private CategoryService categoryService;
  @InjectMocks private CategoryController categoryController;

  @Test
  public void findAll_ReturnsListOfCategories() throws Exception {
    List<Category> categoryList = Arrays.asList(new Category(), new Category());
    when(categoryService.findAll()).thenReturn(categoryList);
    mockMvc
        .perform(get("/categories"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void findById_ReturnsCategory() throws Exception {
    Long categoryId = Long.MIN_VALUE;
    Category category = new Category();
    category.setId(1);
    category.setName("sample");
    when(categoryService.findById(categoryId)).thenReturn(category);
    mockMvc
        .perform(get("/categories/" + categoryId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("{\"id\":1, \"name\":\"sample\"}"));
  }

  @Test
  public void save_ReturnsCategory() throws Exception {
    var category = new Category();
    category.setName("sample");
    category.setId(Long.MIN_VALUE);
    category.setDescription("sample description");
    when(categoryService.save(any(CategoryInputDto.class))).thenReturn(category);
    mockMvc
        .perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"sample\", \"description\": \"sample description\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            content()
                .json(
                    "{\"id\":-9223372036854775808,\"name\":\"sample\",\"description\":\"sample description\"}"));
  }

  @Test
  public void update_ReturnsCategory() throws Exception {
    var categoryId = 1;
    Category category = new Category();
    category.setId(categoryId);
    category.setName("oldname");
    when(categoryService.update(any(Long.class), any(CategoryInputDto.class))).thenReturn(category);
    mockMvc
        .perform(
            put("/categories/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"oldname\", \"description\": \"updated description\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(categoryId))
        .andExpect(jsonPath("$.name").value("oldname"));
  }
}
