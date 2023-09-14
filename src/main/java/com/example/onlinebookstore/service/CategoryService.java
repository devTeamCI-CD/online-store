package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.category.CategoryRequestDto;
import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);
    CategoryResponseDto getById(Long id);
    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto);
    void deleteById(Long id);
}
