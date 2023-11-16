package com.example.onlineproductstore.service;

import com.example.onlineproductstore.dto.category.CategoryResponseDto;
import com.example.onlineproductstore.dto.category.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto createCategoryRequestDto);

    CategoryResponseDto update(Long id, CreateCategoryRequestDto createCategoryRequestDto);

    void deleteById(Long id);
}
