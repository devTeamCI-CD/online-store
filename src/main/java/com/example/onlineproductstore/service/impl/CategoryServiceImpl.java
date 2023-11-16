package com.example.onlineproductstore.service.impl;

import com.example.onlineproductstore.dto.category.CategoryResponseDto;
import com.example.onlineproductstore.dto.category.CreateCategoryRequestDto;
import com.example.onlineproductstore.exception.EntityNotFoundException;
import com.example.onlineproductstore.mapper.CategoryMapper;
import com.example.onlineproductstore.model.Category;
import com.example.onlineproductstore.repository.product.CategoryRepository;
import com.example.onlineproductstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponseDto)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find category by id: " + id));
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto createCategoryRequestDto) {
        Category savedCategory = categoryRepository.save(
                categoryMapper.toEntity(createCategoryRequestDto));
        return categoryMapper.toResponseDto(savedCategory);
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryRequestDto createCategoryRequestDto) {
        categoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Can't find category by id: " + id));
        Category category = categoryMapper.toEntity(createCategoryRequestDto);
        category.setId(id);
        return categoryMapper.toResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
