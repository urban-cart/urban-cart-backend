package com.example.urbancart.category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.urbancart.category.dto.CategoryInputDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @Mock private CategoryRepository categoryRepository;

  @InjectMocks private CategoryService categoryService;

  @Mock private ModelMapper modelMapper;

  @Test
  void testSave() {
    CategoryInputDto categoryInputDto =
        new CategoryInputDto("sample", "sample description", "sample image url");
    Category category = new Category();
    when(modelMapper.map(categoryInputDto, Category.class)).thenReturn(category);
    when(categoryRepository.save(category)).thenReturn(category);

    var result = categoryService.save(categoryInputDto);

    assertNotNull(result);
    verify(categoryRepository, times(1)).save(category);
  }

  @Test
  void testSave_ifAlreadyExist() {
    CategoryInputDto categoryInputDto =
        new CategoryInputDto("sample", "sample description", "sample image url");
    when(categoryRepository.existsByName(categoryInputDto.getName())).thenReturn(true);
    assertThrows(
        ResponseStatusException.class,
        () -> categoryService.save(categoryInputDto),
        "Category with this name already exists");
  }

  @Test
  void findById_NotFound() {
    Long categoryId = Long.MIN_VALUE;
    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> categoryService.findById(categoryId));
  }

  @Test
  void testUpdate() {
    Long categoryId = Long.MIN_VALUE;
    CategoryInputDto categoryInputDto =
        new CategoryInputDto("updated", "updated description", "updated image url");
    Category category = new Category();
    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
    when(categoryRepository.save(category)).thenReturn(category);

    var result = categoryService.update(categoryId, categoryInputDto);

    assertNotNull(result);
    verify(categoryRepository, times(1)).save(category);
  }

  @Test
  void testFindAll() {
    List<Category> categoryList = new ArrayList<>();
    when(categoryRepository.findAll()).thenReturn(categoryList);

    var result = categoryService.findAll();

    assertNotNull(result);
    verify(categoryRepository, times(1)).findAll();
  }

  @Test
  void testFindById() {
    Long categoryId = Long.MIN_VALUE;
    Category category = new Category();
    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

    Category result = categoryService.findById(categoryId);

    assertNotNull(result);
    verify(categoryRepository, times(1)).findById(categoryId);
  }
}
