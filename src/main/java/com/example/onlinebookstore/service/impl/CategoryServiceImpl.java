package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.category.CategoryRequestDto;
import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CategoryMapper;
import com.example.onlinebookstore.model.Category;
import com.example.onlinebookstore.repository.book.CategoryRepository;
import com.example.onlinebookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
                        () -> new EntityNotFoundException("Can't find entity by id: " + id));
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        Category savedCategory = categoryRepository.save(categoryMapper.toEntity(categoryRequestDto));
        return categoryMapper.toResponseDto(savedCategory);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        categoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Can't find entity by id: " + id));
        Category category = categoryMapper.toEntity(categoryRequestDto);
        category.setId(id);
        return categoryMapper.toResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
