package com.example.onlineproductstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.onlineproductstore.dto.category.CategoryResponseDto;
import com.example.onlineproductstore.exception.EntityNotFoundException;
import com.example.onlineproductstore.mapper.CategoryMapper;
import com.example.onlineproductstore.model.Category;
import com.example.onlineproductstore.repository.product.CategoryRepository;
import com.example.onlineproductstore.service.impl.CategoryServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Given
        Pageable pageable = Pageable.unpaged();
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(category));

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toResponseDto(any()))
                .thenReturn(new CategoryResponseDto(1L, "Test Category", null));

        // When
        List<CategoryResponseDto> result = categoryService.findAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getName());
    }

    @Test
    void testGetByIdExistingCategory() {
        // Given
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toResponseDto(category)).thenReturn(categoryResponseDto);

        // When
        CategoryResponseDto result = categoryService.getById(categoryId);

        // Then
        assertNotNull(result);
        assertEquals(categoryResponseDto, result);
    }

    @Test
    void testGetByIdNonExistingCategory() {
        // Given
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(categoryId));
    }

    // Additional tests for save, update, and deleteById methods can be added similarly
}
